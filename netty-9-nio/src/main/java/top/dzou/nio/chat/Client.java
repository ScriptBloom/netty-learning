package top.dzou.nio.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author dingxiang
 * @date 19-10-24 下午8:04
 */
public class Client {
    public static void main(String[] args) throws IOException {
        //打开客户端channel
        SocketChannel socketChannel = SocketChannel.open();
        //设置为非阻塞模式，可以配合selector使用
        socketChannel.configureBlocking(false);
        //打开selector
        Selector selector = Selector.open();
        //注册客户端channel到多路复用器，监听连接事件
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        //连接到指定地址
        socketChannel.connect(new InetSocketAddress("localhost",8089));
        while (true){
            try{
                    //执行selector方法，阻塞获取channel事件的触发
                    int num = selector.select();
                    //获取注册到多路复用器上的SelectionKey
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    //通过迭代器遍历SelectionKey
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();
                        //如果SelectionKey触发的事件为连接就绪
                        if(selectionKey.isConnectable()){
                            //拿到SelectionKey的客户端channel
                            SocketChannel client = (SocketChannel) selectionKey.channel();
                            if(client.isConnectionPending()){
                                //完成连接
                                client.finishConnect();
                                //新建一个写buffer
                                ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                                //写入客户端连接成功消息
                                writeBuffer.put((client.toString()+":连接成功!").getBytes());
                                //翻转读写操作 执行写操作
                                writeBuffer.flip();
                                //写入buffer数据刅客户端
                                client.write(writeBuffer);
                                //开辟一个线程写，因为标准输入是阻塞的，当前线程不能阻塞写
                                ExecutorService executorService = Executors.newSingleThreadExecutor();
                                executorService.submit(()->{
                                    while (true){
                                        writeBuffer.clear();
                                        InputStreamReader reader = new InputStreamReader(System.in);
                                        BufferedReader br = new BufferedReader(reader);
                                        String msg = br.readLine();
                                        //每次读入一行，写入数据到buffer并且写入客户端channel
                                        writeBuffer.put(msg.getBytes());
                                        writeBuffer.flip();
                                        client.write(writeBuffer);
                                    }
                                });
                            }
                            //注册客户端可读事件到多路复用器
                            client.register(selector,SelectionKey.OP_READ);
                            //如果多路复用器上的SelectionKey处于读就绪状态
                        }else if(selectionKey.isReadable()){
                            //拿到SelectionKey触发相应事件对应的客户端channel，执行读操作
                            SocketChannel client = (SocketChannel) selectionKey.channel();
                            //创建一个新的读buffer，
                            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                            //从准备好读操作的channel中读取数据
                            int count = client.read(readBuffer);
                            if (count>0){
                                //转码并数据使用String保存且打印
                                String reMsg = new String(readBuffer.array(),0,count);
                                System.out.println(reMsg);
                            }else if(count==-1) client.close();//关闭客户端
                        }
                    }
                    selectionKeys.clear();//每次处理完一个SelectionKey的事件，把该SelectionKey删除
                }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
