package cn.itcast.netty.c1.bytebuffer;

import java.nio.ByteBuffer;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.netty.c1.bytebuffer
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-04  20:31
 * @Description:
 * @Version: 1.0
 */
public class TestByteBufferAllocate {
    public static void main(String[] args) {
        System.out.println(ByteBuffer.allocate(16).getClass());
        System.out.println(ByteBuffer.allocateDirect(16).getClass());
        /*
        *  class java.nio.HeapByteBuffer   - java 堆内存，读写效率低，收到 GC 的影响
        *  class java.nio.DirectByteBuffer - 直接内存，读写效率高（少一次拷贝），不会收到 GC 影响，分配的效率低
        * */
    }

}
