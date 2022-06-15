package cn.itcast.netty.ByteBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.netty.c4.ByteBuf
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-20  23:32
 * @Description:
 * @Version: 1.0
 */
public class TestByteBufDirectAndHeap {
    public static void main(String[] args) {
        // 默认是 池化的直接内存
        // jvm 参数加上 -Dio.netty.allocator.type=unpooled 变成非池化的直接内存
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        System.out.println(buf.getClass());
        TestByteBuf.log(buf);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 300; i++) {
            sb.append("a");
        }
        buf.writeBytes(sb.toString().getBytes());
        TestByteBuf.log(buf);
    }
}
