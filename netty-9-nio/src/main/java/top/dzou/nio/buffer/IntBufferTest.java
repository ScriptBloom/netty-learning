package top.dzou.nio.buffer;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * @author dingxiang
 * @date 19-10-15 上午9:22
 */

/**
 * intbuffer写入并读取
 */
public class IntBufferTest {
    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(10);
        for(int i=0;i<buffer.capacity();i++){
            int num = new SecureRandom().nextInt(20);
            buffer.put(num);
        }
        buffer.flip();
        while(buffer.hasRemaining()){
            System.out.println(buffer.get());
        }
    }
}
