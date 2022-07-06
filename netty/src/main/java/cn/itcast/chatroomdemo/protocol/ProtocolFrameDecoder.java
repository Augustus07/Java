package cn.itcast.chatroomdemo.protocol;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.chatroomdemo.protocol
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-30  12:27
 * @Description:
 * @Version: 1.0
 */
public class ProtocolFrameDecoder extends LengthFieldBasedFrameDecoder {

    public ProtocolFrameDecoder() {
        this(1024, 12, 4, 0, 0);
    }

    public ProtocolFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }
}
