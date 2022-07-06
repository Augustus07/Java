package cn.itcast.chatroomdemo.server.handler;

import cn.itcast.chatroomdemo.message.LoginRequestMessage;
import cn.itcast.chatroomdemo.message.LoginResponseMessage;
import cn.itcast.chatroomdemo.server.service.UserServiceFactory;
import cn.itcast.chatroomdemo.server.session.SessionFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.chatroomdemo.server.handler
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-07-05  21:04
 * @Description: 由于此 handler 没有共享变量，也没有状态信息
 * @Version: 1.0
 */
@ChannelHandler.Sharable
public class LoginRequestMessageHandler extends SimpleChannelInboundHandler<LoginRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage msg) throws Exception {
        String username = msg.getUsername();
        String password = msg.getPassword();
        boolean login = UserServiceFactory.getUserService().login(username, password);
        LoginResponseMessage message;
        if (login) {
            // 将 channel 和 username 绑定在一起，其实主要功能就是会话管理器
            SessionFactory.getSession().bind(ctx.channel(), username);
            message = new LoginResponseMessage(true, "登陆成功");
            System.out.println("登录成功!");
        } else {
            message = new LoginResponseMessage(false, "用户名或密码不正确");
        }
        ctx.writeAndFlush(message);
    }
}
