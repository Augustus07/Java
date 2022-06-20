package cn.itcast.nettyadvanced.solve.customprotocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.nettyadvanced.solve.customprotocol
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-24  22:33
 * @Description: 使用LengthFieldBasedFrameDecoder实现自定义的网络协议，
 * 最简单的，发数据前每次发送一个int代表本次数据长度，server先接受长度，然后读取固定长度字节数
 * 还可以自定消息的格式，具体参见 LengthFieldBasedFrameDecoder 源码
 * @Version: 1.0
 */
public class TestLengthFiledDecoder {
    public static void main(String[] args) {
        EmbeddedChannel channel = new EmbeddedChannel(
                new LengthFieldBasedFrameDecoder(
                        1024, 0, 4, 0, 4),
                new LoggingHandler(LogLevel.DEBUG)
        );

        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        send(buf, "hello, world");
        send(buf, "Hi!");
        channel.writeInbound(buf);
    }

    private static  void send(ByteBuf buf, String content) {
        byte[] bytes = content.getBytes();
        int len = bytes.length;
        buf.writeInt(len);
        buf.writeBytes(bytes);
    }

}
