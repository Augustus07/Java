package cn.itcast.netty.components.channel.channelFuture.connectProblem;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.netty.c3.EventLoop.ioEvent
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-19  20:23
 * @Description: debug 模式下 右键 channel 变量 Evalulate Expression 输入
 * 1. channel.write("hello")
 * 2. channel.writeAndFlush("hello")
 * 可以观察到 write 方法将 hello 放入系统缓冲区中，并没有立即发送数据
 * 而 writeAndFlush 将 hello 放入系统缓冲区中，立即发送数据
 * @Version: 1.0
 */
@Slf4j
public class EventLoopClient {
    public static void main(String[] args) throws InterruptedException {
        // 1. 启动类
        ChannelFuture channelFuture = new Bootstrap()
                // 2. 添加 EventLoop
                .group(new NioEventLoopGroup())
                // 3. 选择客户端 channel 实现
                .channel(NioSocketChannel.class)
                // 4. 添加处理器
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override // 在连接建立后被调用
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringEncoder());
                    }
                })
                // 5. 连接到服务器 ,异步非阻塞方法
                // 异步，即将connect里面的活交给其他线程（EventLoopGroup中的某个线程）干
                // 非阻塞，即把活给其他线程管他弄没弄完，自己先执行后面代码
                .connect(new InetSocketAddress("localhost", 8080));

        // 如果没有这行代码，connect是异步非阻塞方法，即可能没有连接到服务器，但是能获取到channel，但是由于没有连接，肯定就发送不了数据
        // 所以这行代码的真正作用是，阻塞当前线程，直到nio线程完成 连接服务器 这个动作，所以必须有这行代码
//         2.1 使用 sync 方法同步处理结果, 即 发数据的活主线程自己干
//        channelFuture.sync();
        Channel channel = channelFuture.channel();
        // 无阻塞向下执行获取 channel
        log.debug("{}", channel);
        channel.writeAndFlush("hello");


    }
}
