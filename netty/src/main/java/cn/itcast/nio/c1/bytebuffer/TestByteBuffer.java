package cn.itcast.nio.c1.bytebuffer;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.netty.c1
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-03  22:18
 * @Description: ByteBuffer position、limit、capacity
 * @Version: 1.0
 */
@Slf4j
public class TestByteBuffer {
    public static void main(String[] args) {
//        FileChannel
//        可以通过两种方式获得FileChannel 1. 输入输出流 2. RandomAccessFile
        try (FileChannel channel = new FileInputStream("./netty/tmp/data.txt").getChannel()) {
//            准备缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(10);
            while (true) {
//                从 channel 中读取数据，向 buffer 写入
                int len = channel.read(buffer);
                log.debug("读取到的字节数 {}", len);
//                没有内容时，channel.read()方法返回-1，否则返回读取的字节数
                if (len == -1) {
                    break;
                }
            }

//            从写模式切换到读模式
            buffer.flip();
//            是否还有剩余未读数据
            while (buffer.hasRemaining()) {
                byte b = buffer.get();
                log.debug("实际字节 {}",(char) b);
            }
//            从读模式切换到写模式
            buffer.clear();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
