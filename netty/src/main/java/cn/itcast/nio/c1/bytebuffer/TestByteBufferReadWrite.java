package cn.itcast.nio.c1.bytebuffer;

import java.nio.ByteBuffer;

import static cn.itcast.nio.c1.bytebuffer.ByteBufferUtil.debugAll;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.netty.c1.bytebuffer
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-04  20:25
 * @Description:
 * @Version: 1.0
 */
public class TestByteBufferReadWrite {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put((byte) 0x61);
        debugAll(buffer);
        buffer.put(new byte[] {0x62, 0x63, 0x64});
        debugAll(buffer);
//        注意到这里获取到的位置是position的位置
//        System.out.println(buffer.get());
        buffer.flip();
        System.out.println(buffer.get());
        debugAll(buffer);
        buffer.compact();
        debugAll(buffer);
        buffer.put(new byte[] {0x65, 0x6f});
        debugAll(buffer);
    }

}
