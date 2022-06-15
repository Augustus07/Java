package cn.itcast.nio.c4.selector.g_TestSelectorWriteImprove;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

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

        // 3. 接收数据
        int count = 0;
        while (true) {
            ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
            count += sc.read(buffer);
            System.out.println(count);
            buffer.clear();
        }
    }
}
