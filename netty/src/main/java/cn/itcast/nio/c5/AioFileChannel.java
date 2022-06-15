package cn.itcast.nio.c5;

import cn.itcast.nio.c1.bytebuffer.ByteBufferUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.netty.c5
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-17  22:20
 * @Description: asychronous 异步文件读
 * @Version: 1.0
 */
@Slf4j
public class AioFileChannel {
    public static void main(String[] args) {
        try (AsynchronousFileChannel channel = AsynchronousFileChannel.open(Paths.get("./netty/tmp/data.txt"), StandardOpenOption.READ)) {
            ByteBuffer buffer = ByteBuffer.allocate(16);
            log.debug("read begin...");
            // 参数1 ByteBuffer
            // 参数2 读取的起始位置
            // 参数3 附件
            // 参数4 回调对象
            channel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override // read 成功，回调方法
                public void completed(Integer result, ByteBuffer attachment) {
                    log.debug("read completed...{}", result);
                    // 写 -> 读
                    attachment.flip();
                    ByteBufferUtil.debugAll(attachment);
                }

                @Override // read 失败
                public void failed(Throwable exc, ByteBuffer attachment) {
                    exc.printStackTrace();
                }
            });
            log.debug("read end...");
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
