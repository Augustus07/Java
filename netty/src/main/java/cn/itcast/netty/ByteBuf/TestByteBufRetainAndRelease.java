package cn.itcast.netty.ByteBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.netty.ByteBuf
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-21  22:21
 * @Description:
 * @Version: 1.0
 */
public class TestByteBufRetainAndRelease {
    public static void main(String[] args) {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.heapBuffer(10);
        buffer.writeBytes(new byte[]{1, 2, 3, 4});
        // 让 buffer 的引用计数+1
        buffer.retain();
        // 让 buffer 的引用计数-1 ,当 buffer的引用计数为0时，进行内存回收
        buffer.release();
    }
}
