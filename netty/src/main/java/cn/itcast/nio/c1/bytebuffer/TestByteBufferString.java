package cn.itcast.nio.c1.bytebuffer;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static cn.itcast.nio.c1.bytebuffer.ByteBufferUtil.debugAll;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.netty.c1.bytebuffer
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-04  20:49
 * @Description: 字符串与 ByteBuffer的转换
 * @Version: 1.0
 */
public class TestByteBufferString {
    public static void main(String[] args) {

        /*
         * 三种方式 String 转 ByteBuffer
         * */
        // 1. 字符串转为 ByteBuffer，put完还是写模式
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.put("hello".getBytes());
        debugAll(buffer);

        // 2. Charset 直接给 ByteBuffer 赋值后自动变为读模式
        ByteBuffer buffer1 = StandardCharsets.UTF_8.encode("hello");
        debugAll(buffer1);

        // 3. wrap 赋值后自动变为读模式
        ByteBuffer buffer2 = ByteBuffer.wrap("hello".getBytes());
        debugAll(buffer2);

        /*
        * ByteBuffer 中的内容转 String
        * */
        buffer.flip();
        String str = StandardCharsets.UTF_8.decode(buffer).toString();
        System.out.println(str);

        String str1 = StandardCharsets.UTF_8.decode(buffer1).toString();
        System.out.println(str1);

        String str2 = StandardCharsets.UTF_8.decode(buffer2).toString();
        System.out.println(str2);


    }
}
