package top.dzou.nio.channel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author dingxiang
 * @date 19-10-17 下午6:23
 */
public class FileChannelTest {
    public static void main(String[] args) throws IOException {
        FileInputStream inputStream = new FileInputStream("/home/dzou/java/netty/netty-9-nio/1.txt");
        FileChannel in = inputStream.getChannel();
        FileOutputStream outputStream = new FileOutputStream("/home/dzou/java/netty/netty-9-nio/2.txt");
        FileChannel out = outputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        long start = System.currentTimeMillis();
        while(true){
            byteBuffer.clear();//没有的话会一直读取
            int read = in.read(byteBuffer);
            System.out.println("read:"+read);
            if(read==-1){
                break;
            }
            byteBuffer.flip();
            out.write(byteBuffer);
        }
        long end = System.currentTimeMillis();
        System.out.println(end-start);
        inputStream.close();
        outputStream.close();
    }
}
