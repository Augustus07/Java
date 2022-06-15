package cn.itcast.nio.c4.selector.f_TestSelectorWrite;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.netty.c4.selector.f_TestSelectorWrite
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-15  21:49
 * @Description: 测试 服务器端的 写事件， 但该程序有个 bug，当发送大量数据给 client 时，会一直卡在 读事件发生的  while 循环里，
 * 即，数据不发完就轮不到其他的 socketchannel, 这里违背了 non - blocking - IO 的思想
 * @Version: 1.0
 */
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        Selector selector = Selector.open(); 
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress(8080));
        while (true) {
            selector.select();
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                // 将 key 从 seletionKey 中移除
                iter.remove();
                if (key.isAcceptable()) {
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);

                    // 1. 向客户端发送大量数据
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < 30000000; i++) {
                        sb.append("a");
                    }
                    ByteBuffer buffer = Charset.defaultCharset().encode(sb.toString());
                    while (buffer.hasRemaining()) {
                        // 2. 返回值代表实际写入的字节数
                        int write = sc.write(buffer);
                        System.out.println(write);
                    }
                }
            }
        }
    }

}
