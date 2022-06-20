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
 * @CreateTime: 2022-06-24  22:49
 * @Description:
 * @Version: 1.0
 */
public class TestLengthFiledDecoder1 {
    public static void main(String[] args) {
        EmbeddedChannel channel = new EmbeddedChannel(
                new LengthFieldBasedFrameDecoder(
                        1024, 0, 4, 1, 4),
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
        // 假设这是版本号
        buf.writeByte(1);
        buf.writeBytes(bytes);
    }
}
