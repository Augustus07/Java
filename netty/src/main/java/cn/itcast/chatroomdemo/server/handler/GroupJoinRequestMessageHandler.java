package cn.itcast.chatroomdemo.server.handler;

import cn.itcast.chatroomdemo.message.GroupJoinRequestMessage;
import cn.itcast.chatroomdemo.message.GroupJoinResponseMessage;
import cn.itcast.chatroomdemo.server.session.Group;
import cn.itcast.chatroomdemo.server.session.GroupSessionFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.chatroomdemo.server.handler
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-07-05  22:12
 * @Description:
 * @Version: 1.0
 */
@ChannelHandler.Sharable
public class GroupJoinRequestMessageHandler extends SimpleChannelInboundHandler<GroupJoinRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupJoinRequestMessage msg) throws Exception {
        Group group = GroupSessionFactory.getGroupSession().joinMember(msg.getGroupName(), msg.getUsername());
        if (group != null) {
            ctx.writeAndFlush(new GroupJoinResponseMessage(true, msg.getGroupName() + "群加入成功"));
        } else {
            ctx.writeAndFlush(new GroupJoinResponseMessage(true, msg.getGroupName() + "群不存在"));
        }
    }
}
