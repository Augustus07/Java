package cn.itcast.nio.c5;

import cn.itcast.nio.c1.bytebuffer.ByteBufferUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.netty.c4
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-05  20:34
 * @Description: 客户端程序
 * @Version: 1.0
 */
public class Client {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", 8080));
        sc.write(Charset.defaultCharset().encode("0123456789\n"));
        ByteBuffer buffer = ByteBuffer.allocate(20);
        sc.read(buffer);
        buffer.flip();
        ByteBufferUtil.debugAll(buffer);
        System.in.read();
    }
}
