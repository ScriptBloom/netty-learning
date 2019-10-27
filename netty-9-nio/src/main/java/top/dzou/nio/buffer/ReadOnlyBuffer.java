package top.dzou.nio.buffer;

import java.nio.ByteBuffer;

/**
 * @author dingxiang
 * @date 19-10-17 下午6:48
 */

/**
 * 只读buffer 每个buffer都可以随时转换为只读buffer
 */
public class ReadOnlyBuffer {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        for(int i=0;i<buffer.capacity();i++){
            buffer.put((byte)i);
        }
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        System.out.println(readOnlyBuffer.getClass());
    }
}
