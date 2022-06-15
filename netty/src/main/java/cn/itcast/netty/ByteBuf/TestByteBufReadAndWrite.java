package cn.itcast.netty.ByteBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.netty.ByteBuf
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-21  00:49
 * @Description:
 * @Version: 1.0
 */
public class TestByteBufReadAndWrite {
    public static void main(String[] args) {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.heapBuffer(10);
        buffer.writeBytes(new byte[]{1, 2, 3, 4});
        TestByteBuf.log(buffer);
        // 初始 capacity = 10,已经写入4个字节
        buffer.writeInt(5);
        TestByteBuf.log(buffer);
        // 已经写入 1+1+1+1+4 = 8 个字节，再写一个 int 4 个字节会发生扩容
        // 扩容规则： 写入后数据小于等于 512，选择下一个16的整数倍；如果写入后数据大于 512，扩容后的大小是 2^n ，扩容的极限 maxcapacity 是 Integer.MAX_VALUE
        buffer.writeInt(6);
        TestByteBuf.log(buffer);

        System.out.println(buffer.readByte());
        System.out.println(buffer.readByte());
        System.out.println(buffer.readByte());
        System.out.println(buffer.readByte());
        TestByteBuf.log(buffer);
        buffer.markReaderIndex();
        System.out.println(buffer.readInt());
        TestByteBuf.log(buffer);
        buffer.resetReaderIndex();
        TestByteBuf.log(buffer);
    }
}
