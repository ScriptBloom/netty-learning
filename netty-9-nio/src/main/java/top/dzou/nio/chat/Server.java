package top.dzou.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @author dingxiang
 * @date 19-10-24 下午6:23
 */

/**
 * 可以直接使用 linux nc命令当做客户端
 * nc localhost 端口
 */
public class Server {
    private static Map<SocketChannel,String> clientMap = new HashMap<>();
    public static void main(String[] args) throws IOException {
        //打开服务器channel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //设置非阻塞 即将使用selector
        serverSocketChannel.configureBlocking(false);
        //获取服务器的socket
        ServerSocket serverSocket = serverSocketChannel.socket();
        //绑定端口
        serverSocket.bind(new InetSocketAddress(8089));
        //打开一个多路复用器，使用一条线程处理客户端channel
        Selector selector = Selector.open();
        //注册服务器channel到
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true){
            //阻塞获取channel事件
            //一旦调用了 select() 方法，并且返回值表明有一个或更多个通道就绪了
            int num = selector.select();
            /**
             * 获取到后 拿到多路复用器的SelectionKey 核心方法channel获取注册在起上的channel
             * SelectionKey 每次注册一个channel都会创建一个SelectionKey 其中常量定义channel状态
            **/
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //对其中每一个SelectionKey进行操作
            selectionKeys.forEach(selectionKey->{
                    try {
                        //如果该服务器SelectionKey被接收
                        if(selectionKey.isAcceptable()){
                            //拿到服务器channel
                            ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                            SocketChannel client = null;
                            //拿到本次连接上服务器的客户端
                            client = server.accept();
                            client.configureBlocking(false);
                            //把客户端注册到多路复用器，监听客户端的可读事件
                            client.register(selector,SelectionKey.OP_READ);
                            //为每个客户端分配id
                            String key = "["+ UUID.randomUUID()+"]";
                            clientMap.put(client,key);
                            //如果SelectionKey读就绪，执行读操作
                        }else if(selectionKey.isReadable()){
                            //拿到channel
                            SocketChannel channel = (SocketChannel) selectionKey.channel();
                            //创建读buffer
                            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                            //读取channel中数据到读buffer
                            int read = channel.read(readBuffer);
                            String reMsg = "";
                            //如果有数据
                            if(read>0){
                                //翻转进行写操作
                                readBuffer.flip();
                                //制定解码集utf-8，对读buffer解码打印
                                Charset charset = Charset.forName("utf-8");
                                reMsg = String.valueOf(charset.decode(readBuffer).array());
                                System.out.println(clientMap.get(channel)+" receive: "+reMsg);
                            }else if(read==-1) channel.close();//如果客户端关闭就关闭客户端channel
                            //群发：发送数据到其他客户端channel
                            for(SocketChannel ch:clientMap.keySet()){
                                if(ch!=channel) {
                                    String key = clientMap.get(ch);
                                    ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                                    writeBuffer.put(("来自"+key + ":" + reMsg).getBytes());
                                    writeBuffer.flip();
                                    ch.write(writeBuffer);
                                }
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();

                }
            });
            selectionKeys.clear();//每次处理完一个SelectionKey的事件，把该SelectionKey删除
        }
    }
}
