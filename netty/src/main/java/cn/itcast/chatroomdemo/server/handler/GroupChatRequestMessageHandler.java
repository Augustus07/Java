package cn.itcast.chatroomdemo.server.handler;

import cn.itcast.chatroomdemo.message.GroupChatRequestMessage;
import cn.itcast.chatroomdemo.message.GroupChatResponseMessage;
import cn.itcast.chatroomdemo.server.session.GroupSessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.chatroomdemo.server.handler
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-07-05  22:10
 * @Description:
 * @Version: 1.0
 */
@ChannelHandler.Sharable
public class GroupChatRequestMessageHandler extends SimpleChannelInboundHandler<GroupChatRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupChatRequestMessage msg) throws Exception {
        // 获取群聊中在线成员的 channel list
        List<Channel> channels = GroupSessionFactory.getGroupSession().getMembersChannel(msg.getGroupName());
        // 每一个channel发送消息
        for (Channel channel : channels) {
            channel.writeAndFlush(new GroupChatResponseMessage(msg.getFrom(), msg.getContent()));
        }
    }
}
