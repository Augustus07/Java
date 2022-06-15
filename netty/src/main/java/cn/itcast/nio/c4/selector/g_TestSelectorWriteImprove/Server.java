package cn.itcast.nio.c4.selector.g_TestSelectorWriteImprove;

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
 * 针对这个问题进行改进
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
                    SelectionKey sckey = sc.register(selector, 0, null);
                    sckey.interestOps(SelectionKey.OP_READ);

                    // 1. 向客户端发送大量数据
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < 30000000; i++) {
                        sb.append("a");
                    }
                    ByteBuffer buffer = Charset.defaultCharset().encode(sb.toString());
                    // 2. 返回值代表实际写入的字节数
                    int write = sc.write(buffer);
                    System.out.println(write);

                    // 3. 判断是否有剩余内容
                    if (buffer.hasRemaining()) {
                        // 4. 如果有剩余内容，就关注可写事件
                        sckey.interestOps(sckey.interestOps() + SelectionKey.OP_WRITE);
//                        sckey.interestOps(sckey.interestOps() | SelectionKey.OP_WRITE);
                        // 5. 把未写完的数据挂到 sckey 上
                        sckey.attach(buffer);
                    }
                } else if (key.isWritable()) {
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    SocketChannel sc = (SocketChannel) key.channel();
                    int write = sc.write(buffer);
                    System.out.println(write);
                    // 6. 清理操作, 当buffer中的内容全部放到 sc 中去
                    if (!buffer.hasRemaining()) {
                        key.attach(null); // 需要清除 buffer
                        key.interestOps(key.interestOps() - SelectionKey.OP_WRITE); // 数据传输完就不用关注可写事件了
                    }
                }
            }
        }
    }

}
