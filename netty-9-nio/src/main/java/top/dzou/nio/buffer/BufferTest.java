package top.dzou.nio.buffer;

import java.nio.ByteBuffer;

/**
 * @author dingxiang
 * @date 19-10-17 下午6:32
 */
public class BufferTest {
    public static void main(String[] args) {
        test1();
        test2();
    }
    public static void test1(){
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.putInt(1);
        buffer.putLong(387524875628742L);
        buffer.putChar('s');
        buffer.flip();
        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getChar());
    }
    public static void test2(){
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        for(int i=0;i<buffer.capacity();i++){
            buffer.put((byte)i);
        }
        buffer.position(10);
        buffer.limit(20);
        ByteBuffer buffer1 = buffer.slice();//buffer分片
        for(int m=0;m<buffer1.capacity();m++){
            byte b = buffer1.get();
            System.out.print(b+" ");
        }
        while(buffer1.hasRemaining()){
            System.out.print(buffer1.get());
        }
    }
}
