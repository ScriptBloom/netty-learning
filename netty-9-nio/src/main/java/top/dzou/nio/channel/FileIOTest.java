package top.dzou.nio.channel;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * @author dingxiang
 * @date 19-10-25 上午9:50
 */
public class FileIOTest {
    public static void main(String[] args) throws IOException {
        RandomAccessFile in = new RandomAccessFile("netty-9-nio/1.txt", "rw");
        RandomAccessFile out = new RandomAccessFile("netty-9-nio/2.txt", "rw");
        FileChannel inChannel = in.getChannel();
        FileChannel outChannel = out.getChannel();
        MappedByteBuffer inputData = inChannel.map(FileChannel.MapMode.READ_ONLY,0,new File("netty-9-nio/1.txt").length());

        Charset charset = Charset.forName("iso-8859-1");
        CharsetDecoder decoder = charset.newDecoder();
        CharsetEncoder encoder = charset.newEncoder();
        CharBuffer charBuffer = decoder.decode(inputData);
        ByteBuffer buffer = encoder.encode(charBuffer);
        outChannel.write(buffer);
        in.close();out.close();
    }
}
