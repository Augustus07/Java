package cn.itcast.nio.c1.bytebuffer;

import java.nio.ByteBuffer;

import static cn.itcast.nio.c1.bytebuffer.ByteBufferUtil.debugAll;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.netty.c1.bytebuffer
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-04  21:23
 * @Description: 粘包，半包解决
 * @Version: 1.0
 */
public class TestByteStickPacks {
    public static void main(String[] args) {
        /*
        *  网络上有多条数据发送给服务端，数据之间使用 \n 进行分隔
        *  但由于某种原因这些数据在接收时，被进行了重新组合，例如原始数据有 3 条为
        *       Hello,world\n
        *       I'm zhangsan\n
        *       How are you?\n
        *  变成了下面两个 byteBuffer (粘包，半包)
        *       Hello,world\nI'm zhangsan\nHo
        *       w are you?\n
        *  现在要求你编写程序，将错乱的数据恢复成原始的按照 \n 分隔的数据
        * */
        ByteBuffer source = ByteBuffer.allocate(32);
        source.put("Hello,world\nI'm zhangsan\nHo".getBytes());
        split(source);
        source.put(("w are you?\n").getBytes());
        split(source);

    }

    private static void split(ByteBuffer source) {
//        写 -> 读
        source.flip();
        for (int i = 0; i < source.limit(); i++) {
//            找到一条完整消息
            if (source.get(i) == '\n') {
                int length = i + 1 - source.position();
//                把这条完整消息存入新的 ByteBuffer
                ByteBuffer target = ByteBuffer.allocate(length);
//                从 source 读，向 target 写
                for (int j = 0; j < length; j++) {
                    target.put(source.get());
                }
                debugAll(target);
            }
        }
//        读 -> 写，并且保留未读的数据元素
        source.compact();

    }

}
