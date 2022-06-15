package cn.itcast.nio.c4.threadoptimization.a;

import cn.itcast.nio.c1.bytebuffer.ByteBufferUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.netty.c4.threadoptimization
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-16  21:03
 * @Description: 思路如下，server 开启 n 个线程，其中 一个线程 用于接受 客户 连接， 其他线程用于处理 读写事件
 * 本例中， 主线程 接受 客户端 连接，一个 Worker 对象代表一个 线程，用于处理 读事件
 * 但是，有个问题，即， 先开启 worker线程，worker线程会卡在读事件的 select()处，主线程中 再接受 client连接时 执行 注册 代码
 * 如果 先卡在 select 后执行 注册，就会注册不上，因为 selector此时是 阻塞 的，
 * 如何处理这个问题， 见 b 包 和 c 包 通过两种方式解决
 * @Version: 1.0
 */
@Slf4j
public class MultiThreadServer {
    public static void main(String[] args) throws IOException {
        Thread.currentThread().setName("boss");
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        Selector boss = Selector.open();
        SelectionKey bossKey = ssc.register(boss, 0, null);
        bossKey.interestOps(SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress(8080));
        // 1. 创建固定数量的 worker 并初始化
        Worker worker = new Worker("worker-0"); // 初始化 selector,启动 worker-0
        worker.register();
        while (true) {
            boss.select();
            Iterator<SelectionKey> iter = boss.selectedKeys().iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                iter.remove();
                if (key.isAcceptable()) {
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    log.debug("connected...{}", sc.getRemoteAddress());
                    log.debug("before register...{}", sc.getRemoteAddress());
                    sc.register(worker.selector, SelectionKey.OP_READ, null);
                    log.debug("after register...{}", sc.getRemoteAddress());
                }
            }
        }
    }

    static class Worker implements Runnable {
        private Thread thread;
        private Selector selector;
        private String name;
        private volatile boolean start = false; // 还未初始化

        public Worker(String name) {
            this.name = name;
        }

        // 初始化线程， 和 selector
        public void register() throws IOException {
            if (!start) {
                selector = Selector.open();
                thread = new Thread(this, name);
                thread.start();
                start = true;
            }
        }

        @Override
        public void run() {
            while (true) {
                try {
                    selector.select();
                    Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                    while (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        iter.remove();
                        if (key.isReadable()) {
                            ByteBuffer buffer = ByteBuffer.allocate(16);
                            SocketChannel channel = (SocketChannel) key.channel();
                            log.debug("connected...{}", channel.getRemoteAddress());
                            channel.read(buffer);
                            buffer.flip();
                            ByteBufferUtil.debugAll(buffer);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
