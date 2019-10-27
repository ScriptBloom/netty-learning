package top.dzou.nio.channel;

/**
 * @author dingxiang
 * @date 19-10-24 下午3:54
 */

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * 实现数据分散和汇集
 * Scatter 数据写入多个buffer
 * Gather 多个buffer写入channel
 */
public class ScatterAndGatherTest {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel =  ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(8899);
        serverSocketChannel.socket().bind(inetSocketAddress);
        int messageLength = 3 + 4 + 5;
        ByteBuffer[] byteBuffer = new ByteBuffer[3];
        byteBuffer[0] = ByteBuffer.allocate(3);
        byteBuffer[1] = ByteBuffer.allocate(4);
        byteBuffer[2] = ByteBuffer.allocate(5);
        SocketChannel socketChannel = serverSocketChannel.accept();
        while (true){
            int byteRead = 0;
            while (byteRead<messageLength){
                long r = socketChannel.read(byteBuffer);
                byteRead += r;
                System.out.println("byteread:"+byteRead);
                Arrays.stream(byteBuffer).map(o->"position:"+o.position()+",limit:"+o.limit()).forEach(System.out::println);
            }

            Arrays.stream(byteBuffer).forEach(Buffer::flip);

            int byteWrite = 0;
            while(byteWrite<messageLength){
                long r = socketChannel.write(byteBuffer);
                byteWrite += r;
                System.out.println("bytewrite:"+byteWrite);
                Arrays.stream(byteBuffer).map(o->"position:"+o.position()+",limit:"+o.limit()).forEach(System.out::println);
            }

            Arrays.stream(byteBuffer).forEach(Buffer::clear);
        }
    }
}
