package cn.itcast.netty.ByteBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.netty.ByteBuf
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-21  22:15
 * @Description:
 * @Version: 1.0
 */
public class TestByteBufComposite {
    public static void main(String[] args) {
        ByteBuf buf1 = ByteBufAllocator.DEFAULT.buffer();
        buf1.writeBytes(new byte[]{1, 2, 3, 4, 5});

        ByteBuf buf2 = ByteBufAllocator.DEFAULT.buffer();
        buf2.writeBytes(new byte[]{6, 7, 8, 9, 10});

        // 这种方式可以，但是涉及到多次拷贝，大数据量会影响效率
//        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
//        buffer.writeBytes(buf1).writeBytes(buf2);
//        TestByteBuf.log(buffer);

        CompositeByteBuf buffer = ByteBufAllocator.DEFAULT.compositeBuffer();
        // 第一个参数用于确保写指针可以自增，如果没有这个参数，将合并失败
        buffer.addComponents(true, buf1, buf2);
        TestByteBuf.log(buffer);
    }
}
