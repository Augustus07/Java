package cn.itcast.netty.ByteBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.netty.ByteBuf
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-21  22:28
 * @Description: Unpooled 是一个工具类，提供非池化的 ByteBuf 创建、组合、复制等操作
 * @Version: 1.0
 */
public class TestUnpooled {
    public static void main(String[] args) {
        ByteBuf buf1 = ByteBufAllocator.DEFAULT.buffer(5);
        buf1.writeBytes(new byte[]{1, 2, 3, 4, 5});
        ByteBuf buf2 = ByteBufAllocator.DEFAULT.buffer(5);
        buf1.writeBytes(new byte[]{6, 7, 8, 9, 10});

        // 零拷贝 wrappedBuffer,底层使用 CompositeByteBuf
        ByteBuf buf3 = Unpooled.wrappedBuffer(buf1, buf2);
        System.out.println(ByteBufUtil.prettyHexDump(buf3));

        ByteBuf buf4 = Unpooled.wrappedBuffer(new byte[]{1, 2, 3}, new byte[]{4, 5, 6});
        System.out.println(buf4.getClass());
        System.out.println(ByteBufUtil.prettyHexDump(buf4));
    }
}
