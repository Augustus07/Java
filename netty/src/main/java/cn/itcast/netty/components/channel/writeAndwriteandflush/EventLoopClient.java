package cn.itcast.netty.components.channel.writeAndwriteandflush;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

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
public class EventLoopClient {
    public static void main(String[] args) throws InterruptedException {
        // 1. 启动类
        Channel channel = new Bootstrap()
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
                // 5. 连接到服务器
                .connect(new InetSocketAddress("localhost", 8080))
                .sync()
                .channel();
        System.out.println(channel);
        System.out.println("");
    }
}
