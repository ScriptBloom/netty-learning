package top.dzou.nio.multi_port_chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author dingxiang
 * @date 19-10-25 下午3:01
 */
public class Server {
    private static int CAPACITY = 1024;

    private static ByteBuffer readBuffer = ByteBuffer.allocate(CAPACITY);

    private static ByteBuffer writeBuffer = ByteBuffer.allocate(CAPACITY);
    private static Map<SocketChannel,String> clientMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannelWxq = ServerSocketChannel.open();
        ServerSocketChannel serverSocketChannelGzh = ServerSocketChannel.open();
        serverSocketChannelGzh.configureBlocking(false);
        serverSocketChannelWxq.configureBlocking(false);
        ServerSocket serverSocketWxq = serverSocketChannelWxq.socket();
        ServerSocket serverSocketGzh = serverSocketChannelGzh.socket();
        serverSocketWxq.bind(new InetSocketAddress(8089));
        System.out.println("监听8089：微信墙服务端口");
        serverSocketGzh.bind(new InetSocketAddress(8090));
        System.out.println("监听8090：公众号服务端口");
        Selector selector = Selector.open();
        serverSocketChannelWxq.register(selector, SelectionKey.OP_ACCEPT);
        serverSocketChannelGzh.register(selector, SelectionKey.OP_ACCEPT);
        while (true){
            int num = selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            selectionKeys.forEach(selectionKey->{
                try {
                    if(selectionKey.isAcceptable()){
                        ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                        SocketChannel client = null;
                        client = server.accept();
                        client.configureBlocking(false);
                        String key = "";
                        if(server==serverSocketChannelGzh) {//如果是公众号server，注册其客户端的可读事件
                            client.register(selector, SelectionKey.OP_READ);
                            key = "["+ UUID.randomUUID()+":gzh]";
                        }else if(server==serverSocketChannelWxq){//如果是
                            client.register(selector,SelectionKey.OP_WRITE);
                            key = "["+ UUID.randomUUID()+":wxq]";
                        }
                        System.out.println(key+":连接成功!");
                        clientMap.put(client,key);
                    }else if(selectionKey.isReadable()){
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        readBuffer.clear();
                        int read = 0;
                        while(true){
                            int byteRead = channel.read(readBuffer);
                            if(byteRead<=0){
                                break;
                            }
                            readBuffer.flip();
                            channel.write(readBuffer);
                            read += byteRead;
                            readBuffer.clear();
                        }
                        String reMsg = new String(readBuffer.array(),0,read);
                        System.out.println(clientMap.get(channel)+" send to wxq: "+reMsg);
                        //写入微信墙服务
                        for(SocketChannel ch:clientMap.keySet()){
                            if(ch!=channel) {
                                String key = clientMap.get(ch);
                                if(key.endsWith("wxq]")) {
                                    writeBuffer.clear();
                                    writeBuffer.put(("来自" + clientMap.get(channel) + ":" + reMsg).getBytes(StandardCharsets.UTF_8));
                                    writeBuffer.flip();
                                    ch.write(writeBuffer);
                                }
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
