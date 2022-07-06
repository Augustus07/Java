package cn.itcast.chatroomdemo.protocol;

import cn.itcast.chatroomdemo.config.Config;
import cn.itcast.chatroomdemo.message.LoginRequestMessage;
import cn.itcast.chatroomdemo.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.chatroomdemo.protocol
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-29  20:26
 * @Description: 测试 MessageCodec类
 * @Version: 1.0
 */
public class TestMessageCodec {
    public static void main(String[] args) throws Exception {
    }

    // 测试 多种序列化的代码
    private static void m2() {
        MessageCodecSharable CODEC = new MessageCodecSharable();
        LoggingHandler LOGGING = new LoggingHandler();
        EmbeddedChannel channel = new EmbeddedChannel(LOGGING, CODEC, LOGGING);

        LoginRequestMessage message = new LoginRequestMessage("zhangsan", "123");

        // 测试出栈，逆序执行
//        channel.writeOutbound(message);

        // 测试入栈，顺序执行
        ByteBuf buf = messageToByteBuf(message);
        channel.writeInbound(buf);
    }

    private static ByteBuf messageToByteBuf(Message msg) {
        int algorithm = Config.getSerializerAlgorithm().ordinal();
        ByteBuf out = ByteBufAllocator.DEFAULT.buffer();
        out.writeBytes(new byte[] {1, 2, 3, 4});
        out.writeByte(1);
        out.writeByte(algorithm);
        out.writeByte(msg.getMessageType());
        out.writeInt(msg.getSequenceId());
        out.writeByte(0xff);
        byte[] bytes = Serializer.Algorithm.values()[algorithm].serialize(msg);
        out.writeInt(bytes.length);
        out.writeBytes(bytes);
        return out;
    }


    private static void m1() throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(
                // 可以只创建一个，多个线程共享， 源码有@Sharable
                new LoggingHandler(),
                // 必须使用解码器解决粘包和半包问题，多个线程不能共享
                new LengthFieldBasedFrameDecoder(
                        1024, 12, 4, 0, 0),
                new MessageCodec());
        // encode
        LoginRequestMessage message = new LoginRequestMessage("zhangsan", "123", "张三");
//        channel.writeOutbound(message);
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        new MessageCodec().encode(null, message, buf);

        ByteBuf s1 = buf.slice(0, 100);
        ByteBuf s2 = buf.slice(100, buf.readableBytes() - 100);
        s1.retain(); // 引用计数 2
        channel.writeInbound(s1); // release 1
        channel.writeInbound(s2);
    }
}
