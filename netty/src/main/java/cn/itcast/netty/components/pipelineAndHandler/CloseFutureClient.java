package cn.itcast.netty.components.pipelineAndHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.netty.c3.channel.channelFuture.closeProblem
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-19  22:36
 * @Description: 该代码能够通过两种方式 主线程 同步和异步 等待 channel 关闭，但是可以看到最终结果该程序并未结束
 * 原因是， NioEventLoopGroup 中的线程没有关闭
 * @Version: 1.0
 */
@Slf4j
public class CloseFutureClient {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        // 1. 启动类
        ChannelFuture channelFuture = new Bootstrap()
                // 2. 添加 EventLoop
                .group(group)
                // 3. 选择客户端 channel 实现
                .channel(NioSocketChannel.class)
                // 4. 添加处理器
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override // 在连接建立后被调用
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        // 给pipeline()里增加 netty 自带的打印 Log的 handler
                        ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                        ch.pipeline().addLast(new StringEncoder());
                    }
                })
                // 5. 连接到服务器 ,异步非阻塞方法
                // 异步，即将connect里面的活交给其他线程（EventLoopGroup中的某个线程）干
                // 非阻塞，即把活给其他线程管他弄没弄完，自己先执行后面代码
                .connect(new InetSocketAddress("localhost", 8080));

        Channel channel = channelFuture.sync().channel();
        log.debug("{}", channel);
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String line = scanner.nextLine();
                if ("q".equals(line)) {
                    // 不能在这里关，因为channel的执行实在 nio 线程里，在这关是错的
                    channel.close(); // close 操作异步 1s 之后
                    break;
                }
                channel.writeAndFlush(line);
            }
        }, "input").start();

        // 1.方法一 主线程 里等待 channel 关闭
//        ChannelFuture closeFuture = channel.closeFuture();
//        System.out.println("waiting close...");
//        closeFuture.sync();
//        log.debug("处理关闭之后的操作");

        // 2. 方法二 使用回调方法
        ChannelFuture closeFuture = channel.closeFuture();
        closeFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                log.debug("处理关闭之后的操作");

//                优雅关掉 NioEventLoopGroup 中的线程
                group.shutdownGracefully();
            }
        });
        log.debug("主线程处理其他事情去");
    }

}
