package top.dzou.nio.channel;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author dingxiang
 * @date 19-10-15 上午9:36
 */

/**
 * 文件输入流
 */
public class FileInputChannelTest {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("/home/dzou/jmeter.log");
        FileChannel channel = fileInputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);;
        channel.read(byteBuffer);
        byteBuffer.flip();
        while(byteBuffer.remaining()>0){
            byte b = byteBuffer.get();
            System.out.println((char) b);
        }
        fileInputStream.close();
    }
}
