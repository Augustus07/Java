package cn.itcast.netty.c4.nioBlocking;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import static cn.itcast.netty.c1.bytebuffer.ByteBufferUtil.debugRead;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.netty.c4
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-05  20:30
 * @Description: 使用 NIO 来理解阻塞模式， 单线程
 * 阻塞模式的缺点， 没连接来 或者 没有数据进来 就死等， 线程停止运行
 * @Version: 1.0
 */
@Slf4j
public class Server {
    public static void main(String[] args) throws IOException {
//        使用 NIO 来理解阻塞模式， 单线程
//        0. ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(16);
//        1. 创建了服务器
        ServerSocketChannel ssc = ServerSocketChannel.open();

//        2. 绑定监听端口
        ssc.bind(new InetSocketAddress(8080));

//        3. 连接集合
        List<SocketChannel> channels = new ArrayList<>();
        while (true) {
            log.debug("connecting...");
//            4. accept 建立与客户端连接，一个 Client 对应一个 SocketChannel，这个 SocketChannel 用来与 Client通信
            SocketChannel sc = ssc.accept(); // 阻塞方法，线程停止运行
            log.debug("connected... {}", sc);
            channels.add(sc);
            for (SocketChannel channel : channels) {
                log.debug("before read... {}", channel);
//                5. 接收客户端发送的数据
                channel.read(buffer); // 阻塞方法，线程停止运行
                buffer.flip();
                debugRead(buffer);
                buffer.clear();;
                log.debug("after read... {}", channel);
            }
        }
    }

}
