package top.dzou.nio.multi_port_chat;

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

/**
 * @author dingxiang
 * @date 19-10-25 下午3:12
 */
public class GzhClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        socketChannel.connect(new InetSocketAddress("localhost",8090));
        while (true){
            try{
                int num = selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if(selectionKey.isConnectable()){
                        SocketChannel client = (SocketChannel) selectionKey.channel();
                        if(client.isConnectionPending()){
                            client.finishConnect();
                        }
                        client.register(selector,SelectionKey.OP_WRITE);
                    }/*else if(selectionKey.isReadable()){
                        SocketChannel client = (SocketChannel) selectionKey.channel();
                        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                        int count = client.read(readBuffer);
                        if (count>0){
                            String reMsg = new String(readBuffer.array(),0,count);
                            System.out.println(reMsg);
                        }else if(count==-1) client.close();//关闭客户端
                    }*/else if(selectionKey.isWritable()){
                        SocketChannel client = (SocketChannel) selectionKey.channel();
                        ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                            writeBuffer.clear();
                            InputStreamReader reader = new InputStreamReader(System.in);
                            BufferedReader br = new BufferedReader(reader);
                            String msg = br.readLine();
                            //每次读入一行，写入数据到buffer并且写入客户端channel
                            writeBuffer.put(msg.getBytes());
                            writeBuffer.flip();
                            client.write(writeBuffer);
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
