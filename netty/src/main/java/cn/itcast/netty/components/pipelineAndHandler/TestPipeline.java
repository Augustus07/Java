package cn.itcast.netty.components.pipelineAndHandler;

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
 * @BelongsPackage: cn.itcast.netty.c3.pipelineAndHandler
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-20  22:00
 * @Description:
 * ChannelInboundHandlerAdapter 当有数据进来的时候从 head 向 tail 顺序执行
 * ChannelOutboundHandlerAdapter 当有数据发送出去的时候从 tail 向 head 顺序执行
 * @Version: 1.0
 */
@Slf4j
public class TestPipeline {
    public static void main(String[] args) {
        new ServerBootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        // 1. 通过 channel 拿到 pipeline
                        ChannelPipeline pipeline = ch.pipeline();
                        // 2. pipeline中添加处理器 head -> h2 -> h2 -> h3 -> h4 -> h5 -> h6 -> tail
                        pipeline.addLast("h1", new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.debug("1");
                                ByteBuf buf = (ByteBuf) msg;
                                String name = buf.toString(Charset.defaultCharset());
                                // 下面两行都是将 数据name 发送给下一个 handler,选其一即可, 如果没有这行代码，那么pipline会从这里断开
                                super.channelRead(ctx, name);
//                                ctx.fireChannelRead(name);
                            }
                        });
                        pipeline.addLast("h2", new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object name) throws Exception {
                                log.debug("2");
                                Student student = new Student(name.toString());

                                // 下面两行都是将 数据student 发送给下一个 handler,选其一即可
                                super.channelRead(ctx, student);
//                                ctx.fireChannelRead(student);
                            }
                        });
                        pipeline.addLast("h3", new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.debug("3, 结果{}, class:{}", msg, msg.getClass());
                                // 这里由于是 ChannelInboundHandlerAdapter 的末端，因此没有必要将数据在发给后面的了
//                                super.channelRead(ctx, msg);
//                                发送数据才会触发 ChannelOutboundHandlerAdapter
                                /*
                                * 这里注意 ctx.writeAndFlush 和 ch.writeAndFlush 的区别
                                * 调用发送数据会触发 ChannelOutboundHandlerAdapter，但是
                                * ctx 是从当前位置开始的，即有数据发过来，执行 1 -> 2 -> 3，当前是3，再从 3 开始 往前找，前面没有ChannelOutboundHandlerAdapter
                                * ch 使用 tail 位置开始的，即有数据发过来，执行 1 -> 2 -> 3，发送数据后从 tail 开始往前找ChannelOutboundHandlerAdapter,有 6 -> 5 -> 4
                                * */
                                ctx.writeAndFlush(ctx.alloc().buffer().writeBytes("server...".getBytes()));
                                ch.writeAndFlush(ctx.alloc().buffer().writeBytes("server...".getBytes()));
                            }
                        });
                        pipeline.addLast("h4", new ChannelOutboundHandlerAdapter(){
                            @Override
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                log.debug("4");
                                super.write(ctx, msg, promise);
                            }
                        });
                        pipeline.addLast("h5", new ChannelOutboundHandlerAdapter(){
                            @Override
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                log.debug("5");
                                super.write(ctx, msg, promise);
                            }
                        });
                        pipeline.addLast("h6", new ChannelOutboundHandlerAdapter(){
                            @Override
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                log.debug("6");
                                super.write(ctx, msg, promise);
                            }
                        });
                    }
                })
                .bind(8080);
    }

    static class Student {
        String name;

        public Student(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

}
