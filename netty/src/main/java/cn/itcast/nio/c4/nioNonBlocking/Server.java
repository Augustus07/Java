package cn.itcast.nio.c4.nioNonBlocking;

import cn.itcast.nio.c1.bytebuffer.ByteBufferUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.netty.c4
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-05  20:30
 * @Description: 使用 NIO 来理解非阻塞模式， 单线程
 * 非阻塞模式的缺点： 在一个线程里一直循环监听，有 连接 或者 有数据 就输出数据，不然就输出 null，整个线程一直在运行，如果是单核 CPU，那么cpu的利用率应该会占满
 * @Version: 1.0
 */
@Slf4j
public class Server {
    public static void main(String[] args) throws IOException {
//        test01();

//        使用 NIO 来理解非阻塞模式， 单线程
//        0. ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(16);
//        1. 创建了服务器
        ServerSocketChannel ssc = ServerSocketChannel.open();
//        设置 ServerSocketChannel 为非阻塞模式
        ssc.configureBlocking(false);

//        2. 绑定监听端口
        ssc.bind(new InetSocketAddress(8080));

//        3. 连接集合
        List<SocketChannel> channels = new ArrayList<>();
        while (true) {
//            log.debug("connecting...");
//            4. accept 建立与客户端连接，一个 Client 对应一个 SocketChannel，这个 SocketChannel 用来与 Client通信
            SocketChannel sc = ssc.accept(); // 阻塞方法，线程停止运行
            if (sc != null) {
                log.debug("connected... {}", sc);
                sc.configureBlocking(false);
                channels.add(sc);
            }
            for (SocketChannel channel : channels) {
                log.debug("before read... {}", channel);
//                5. 接收客户端发送的数据
                int read = channel.read(buffer); // 阻塞方法，线程停止运行
                if (read > 0) {
                    buffer.flip();
                    ByteBufferUtil.debugRead(buffer);
                    buffer.clear();;
                    log.debug("after read... {}", channel);
                }
            }
        }
    }

    private static void test01() throws IOException {
        //        使用 NIO 来理解阻塞模式， 单线程
//        0. ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(16);
//        1. 创建了服务器
        ServerSocketChannel ssc = ServerSocketChannel.open();
//        设置 ServerSocketChannel 为非阻塞模式
        ssc.configureBlocking(false);

//        2. 绑定监听端口
        ssc.bind(new InetSocketAddress(8080));

//        3. 连接集合
        List<SocketChannel> channels = new ArrayList<>();
        while (true) {
            log.debug("connecting...");
//            4. accept 建立与客户端连接，一个 Client 对应一个 SocketChannel，这个 SocketChannel 用来与 Client通信
            SocketChannel sc = ssc.accept(); // 阻塞方法，线程停止运行
            log.debug("connected... {}", sc);
//            channels.add(sc);
//            for (SocketChannel channel : channels) {
//                log.debug("before read... {}", channel);
////                5. 接收客户端发送的数据
//                channel.read(buffer); // 阻塞方法，线程停止运行
//                buffer.flip();
//                debugRead(buffer);
//                buffer.clear();;
//                log.debug("after read... {}", channel);
//            }
        }
    }

}
