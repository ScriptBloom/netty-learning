package top.dzou.nio.buffer;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

/**
 * @author dingxiang
 * @date 19-10-17 下午6:55
 */
public class DirectBufferTest {
    public static void main(String[] args) throws IOException {
        RandomAccessFile file = new RandomAccessFile("/home/dzou/java/netty/netty-9-nio/1.txt", "rw");
        FileChannel channel = file.getChannel();
        MappedByteBuffer mappedByteBuffer = channel.map(MapMode.READ_WRITE, 0, channel.size());
        mappedByteBuffer.put(1,(byte)'a');
        file.close();
    }
}
