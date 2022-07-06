package cn.itcast.chatroomdemo.server.handler;

import cn.itcast.chatroomdemo.message.GroupMembersRequestMessage;
import cn.itcast.chatroomdemo.message.GroupMembersResponseMessage;
import cn.itcast.chatroomdemo.server.session.GroupSessionFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Set;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.chatroomdemo.server.handler
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-07-05  22:13
 * @Description:
 * @Version: 1.0
 */
@ChannelHandler.Sharable
public class GroupMembersRequestMessageHandler extends SimpleChannelInboundHandler<GroupMembersRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMembersRequestMessage msg) throws Exception {
        Set<String> members = GroupSessionFactory.getGroupSession()
                .getMembers(msg.getGroupName());
        ctx.writeAndFlush(new GroupMembersResponseMessage(members));
    }
}
