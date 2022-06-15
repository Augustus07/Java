package cn.itcast.nio.c4.threadoptimization.b;

import cn.itcast.nio.c1.bytebuffer.ByteBufferUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.netty.c4.threadoptimization
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-16  21:03
 * @Description: 解决 a 包中的方法一
 * 将 regiter 这个过程抽象到 worker 的方法中去 由 boss线程 (主线程) 调用，然后添加 注册代码 到线程安全的 队列<Runnable>类型中
 * 在由 worker 线程 运行的合适位置从 队列中取出 Runnable ,执行该代码
 * 同时，为了解决第一次 select 卡住导致不能注册的问题， 在 boss 线程添加 任务 到队列中后，执行一次 selector 的 wakeup 方法
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
                    worker.register(sc);
                    log.debug("after register...{}", sc.getRemoteAddress());
                }
            }
        }
    }

    static class Worker implements Runnable {
        private Thread thread;
        private Selector selector;
        private String name;
        // 非阻塞（CAS）实现的线程安全的队列
        private ConcurrentLinkedQueue<Runnable> queue = new ConcurrentLinkedQueue<>();
        private volatile boolean start = false; // 还未初始化

        public Worker(String name) {
            this.name = name;
        }

        // 初始化线程， 和 selector
        public void register(SocketChannel sc) throws IOException {
            if (!start) {
                selector = Selector.open();
                thread = new Thread(this, name);
                thread.start();
                start = true;
            }
//            我们的目的是进行线程间的通信，使用线程安全的阻塞队列
//            在 boss 线程中将 runnable 放入队列， 在 worker 线程需要执行的地方 从队列中取出来 运行
            queue.add(() -> {
                try {
                    sc.register(selector, SelectionKey.OP_READ, null);
                } catch (ClosedChannelException e) {
                    throw new RuntimeException(e);
                }
            });
//            第一次的时候，大概率是先卡在 select() 方法中，然后在执行注册，
//            所以队列中添加完元素后，wakeup 一下selector
            selector.wakeup();
        }

        @Override
        public void run() {
            while (true) {
                try {
                    selector.select();
                    // 从队列中取出来
                    Runnable task = queue.poll();
                    if (task != null) {
                        task.run();
                    }
                    Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                    while (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        iter.remove();
                        if (key.isReadable()) {
                            ByteBuffer buffer = ByteBuffer.allocate(16);
                            SocketChannel channel = (SocketChannel) key.channel();
                            log.debug("read...{}", channel.getRemoteAddress());
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
