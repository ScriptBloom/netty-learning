package top.dzou.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author dingxiang
 * @date 19-10-24 下午4:31
 */
public class NioTest {
    //使用一个线程处理所有客户端连接 selector
    public static void main(String[] args) throws IOException {
        int[] ports = new int[]{5090,5091,5092,5093,5095};

        Selector selector = Selector.open();
        for(int i=0;i<ports.length;i++){
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            ServerSocket serverSocket = serverSocketChannel.socket();
            InetSocketAddress inetSocketAddress = new InetSocketAddress(ports[i]);
            serverSocket.bind(inetSocketAddress);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("监听"+ports[i]+"端口");
        }

        while (true){
            //阻塞事件监听 获取到channel响应就返回
            int i = selector.select();
//            System.out.println("num:"+i);
            //拿到所有事件准备就绪的通道
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
//            System.out.println("selector keys:"+selectionKeys);
            //通过迭代器获取SelectionKey
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey key = iterator.next();
                //如果通道的key处于可被服务端接受状态 执行连接
                if(key.isAcceptable()){
                    //拿到key的对应channel
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                    //执行channel的accept方法,接收channel
                    SocketChannel channel = serverSocketChannel.accept();
                    channel.configureBlocking(false);//设置为非醉
                    channel.register(selector,SelectionKey.OP_READ);//注册channel到可读事件
                    iterator.remove();//用完remove 集合遍历删除
                }else if(key.isReadable()){//如果读就绪
                    //获取key对应channel对象 并且执行读操作
                    SocketChannel channel = (SocketChannel) key.channel();
                    int read = 0;
                    while(true){
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        int byteRead = channel.read(buffer);
                        if(byteRead<=0){
                            channel.close();
                            break;
                        }
                        buffer.flip();
                        channel.write(buffer);
                        read += byteRead;
                        buffer.clear();
                    }
                    System.out.println("读取："+read+", 来自"+channel+"连接成功");
                    iterator.remove();//删除处理完的SelectionKey
                }
            }
        }
    }
}
