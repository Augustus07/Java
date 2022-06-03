package cn.itcast.netty.c1.bytebuffer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import static cn.itcast.netty.c1.bytebuffer.ByteBufferUtil.debugAll;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.netty.c1.bytebuffer
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-04  21:14
 * @Description:
 * @Version: 1.0
 */
public class TestScatteringReads {
    public static void main(String[] args) {
        try (FileChannel channel = new RandomAccessFile("./netty/tmp/words.txt", "r").getChannel()) {
            ByteBuffer b1 = ByteBuffer.allocate(3);
            ByteBuffer b2 = ByteBuffer.allocate(3);
            ByteBuffer b3 = ByteBuffer.allocate(5);
            channel.read(new ByteBuffer[]{b1,b2,b3});
            b1.flip();
            b2.flip();
            b3.flip();
            debugAll(b1);
            debugAll(b2);
            debugAll(b3);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
