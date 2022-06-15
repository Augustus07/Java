package cn.itcast.netty.components.EventLoop.ioEventDivisonOfWorkImprove;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
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
 * @Description: 使用 EventLoop执行 IO 任务, 细分2
 * @Version: 1.0
 */
@Slf4j
public class EventLoopServer {
    public static void main(String[] args) {
        // 细分2： 创建一个独立的 EventLoopGroup 处理耗时任务
        // 具体指，用于read 和 write 的那个 Eventloop 只让他收发数据，收到数据后将 耗时操作 交给 自定义 EventLoop 执行
        EventLoopGroup group = new DefaultEventLoop();
        new ServerBootstrap()
                // boss 和 worker
                // boss 只负责 accept 事件 worker 只负责 sockerChannel 上的读写
                .group(new NioEventLoopGroup(), new NioEventLoopGroup(2))
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast("handler1",new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buf = (ByteBuf) msg;
                                log.debug(buf.toString(Charset.defaultCharset()));
                                ctx.fireChannelRead(msg); // 将消息传递给 pipeline 中下一个 handler
                            }
                        }).addLast(group,"handler2", new ChannelInboundHandlerAdapter(){
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
