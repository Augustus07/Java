package cn.itcast.netty.c1.bytebuffer;

import java.nio.ByteBuffer;

import static cn.itcast.netty.c1.bytebuffer.ByteBufferUtil.debugAll;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.netty.c1.bytebuffer
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-04  20:36
 * @Description:
 * @Version: 1.0
 */
public class TestByteBufferRead {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[] {'a', 'b', 'c', 'd'});
        buffer.flip();

//        rewind 从头开始读
        buffer.get(new byte[4]);
        debugAll(buffer);
//        在读模式下，rewind将position指针放在0的位置，且不改变读的状态
        buffer.rewind();
        System.out.println((char) buffer.get());
        System.out.println();

        /*
        *  mark & reset
        *  mark 做一个标记，记录 position 位置，reset 是将 position 重置到 mark 的位置
        * */
//        position再次置0，读模式
        buffer.rewind();
//        输出0、1位置元素，position指向 2
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());
//        此时标记 2 位置
        buffer.mark();
//        输出 2、3 位置元素，position指向 4
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());
//        重置position到 mark 位置，即 2 位置
        buffer.reset();
//        输出 2、3 位置元素，position指向 4
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());
        System.out.println();

        /*
        *  get(i) 不会改变 position 的位置
        * */
        System.out.println((char) buffer.get(3));
        debugAll(buffer);

    }

}
