package cn.itcast.chatroomdemo.server.session;

import io.netty.channel.Channel;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class GroupSessionMemoryImpl implements GroupSession {
    private final Map<String, Group> groupMap = new ConcurrentHashMap<>();

    @Override
    public Group createGroup(String name, Set<String> members) {
        Group group = new Group(name, members);
        return groupMap.putIfAbsent(name, group);
    }

    @Override
    public Group joinMember(String name, String member) {
        return groupMap.computeIfPresent(name, (key, value) -> {
            value.getMembers().add(member);
            return value;
        });
    }

    @Override
    public Group removeMember(String name, String member) {
        return groupMap.computeIfPresent(name, (key, value) -> {
            value.getMembers().remove(member);
            return value;
        });
    }

    @Override
    public Group removeGroup(String name) {
        return groupMap.remove(name);
    }

    @Override
    public Set<String> getMembers(String name) {
        return groupMap.getOrDefault(name, Group.EMPTY_GROUP).getMembers();
    }

    // 通过name获取session中的channel,当channel不空时加入 list,即返回群聊中在线的成员
    @Override
    public List<Channel> getMembersChannel(String name) {
        return getMembers(name).stream()
                .map(member -> SessionFactory.getSession().getChannel(member))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
