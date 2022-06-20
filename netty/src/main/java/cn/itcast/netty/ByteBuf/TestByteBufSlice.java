package cn.itcast.netty.ByteBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.netty.ByteBuf
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-21  21:37
 * @Description: ByteBuf 的slice 操作是在原 ByteBuf 进行操作
 * @Version: 1.0
 */
public class TestByteBufSlice {
    public static void main(String[] args) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(10);
        buf.writeBytes(new byte[]{'a','b','c','d','e','f','g','h','i','j'});
        TestByteBuf.log(buf);

        // 在切片过程中，没有发生数据复制
        ByteBuf f1 = buf.slice(0,5);
        // 让 f1 的引用计数 +1，目的是防止 在使用 f1 前，buf 被 release掉，f1 将无法使用，而 f1 的引用计数 +1 后，不管 buf 有没有被释放，f1都能使用
        // 推荐这样做
        f1.retain();
        // 参数1 起始index, 参数2 长度
        ByteBuf f2 = buf.slice(5,5);
        f2.retain();
        TestByteBuf.log(f1);
        TestByteBuf.log(f2);
        // 不能在写入字节，因为会影响到f2
//        f1.writeByte('x');


        System.out.println("======================");
        f1.setByte(0,'b');

        TestByteBuf.log(f1);
        TestByteBuf.log(buf);

        // f1，f2用完了就自己释放掉, 和retain()方法是配合的
        f1.release();
        f2.release();
    }
}
