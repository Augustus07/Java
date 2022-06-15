package cn.itcast.netty.nettyPractice.a;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.netty.nettyPractice.a
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-17  23:05
 * @Description:
 * @Version: 1.0
 */
public class HelloServer {
    public static void main(String[] args) {
        // 1. 启动器， 负责组装 netty 组件，启动服务器
        new ServerBootstrap()
                // 2. BossEventLoop, WorkerEventLoop(selector, thread), group 组
                .group(new NioEventLoopGroup())
                // 3. 选择 服务器的 ServerSockerChannel 实现
                .channel(NioServerSocketChannel.class)
                // 4. boss 负责处理连接 worker(child) 负责处理读写，决定了 worker(child) 能执行哪些操作 (handler)
                .childHandler(
                        // 5. channel 代表和客户端进行数据读写的通道 Initializer 初始化，负责添加别的 handler
                        new ChannelInitializer<NioSocketChannel>() {
                            @Override
                            protected void initChannel(NioSocketChannel ch) throws Exception {
                                // 6. 添加具体的 handler
                                ch.pipeline().addLast(new StringDecoder());
                                ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                    @Override // 读事件
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        // 打印上一步转换好的字符串
                                        System.out.println(msg);
                                    }
                                });
                            }
                })
                // 7. 绑定监听端口
                .bind(8080);
    }

}
