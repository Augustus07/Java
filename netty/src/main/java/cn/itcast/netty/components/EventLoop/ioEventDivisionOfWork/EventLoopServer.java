package cn.itcast.netty.components.EventLoop.ioEventDivisionOfWork;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.netty.c3.EventLoop.ioEvent
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-19  20:19
 * @Description: 使用 EventLoop执行 IO 任务 ,细分1
 * @Version: 1.0
 */
@Slf4j
public class EventLoopServer {
    public static void main(String[] args) {
        new ServerBootstrap()
                // boss 和 worker
                // 细分1 boss 只负责 accept 事件 worker 只负责 sockerChannel 上的读写
                .group(new NioEventLoopGroup(), new NioEventLoopGroup(2))
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buf = (ByteBuf) msg;
                                log.debug(buf.toString(Charset.defaultCharset()));
                            }
                        });
                    }
                })
                .bind(8080);
    }
}
