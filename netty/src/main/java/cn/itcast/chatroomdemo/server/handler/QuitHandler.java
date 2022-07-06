package cn.itcast.chatroomdemo.server.handler;

import cn.itcast.chatroomdemo.server.session.SessionFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.chatroomdemo.server.handler
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-07-06  18:41
 * @Description:
 * @Version: 1.0
 */
@ChannelHandler.Sharable
@Slf4j
public class QuitHandler extends ChannelInboundHandlerAdapter {
    // 当连接正常断开时， 触发 inactive 事件
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionFactory.getSession().unbind(ctx.channel());
        log.debug("{} 已经断开", ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        SessionFactory.getSession().unbind(ctx.channel());
        log.debug("{} 已经异常断开 异常是{}", ctx.channel(), cause.getMessage());
    }
}
