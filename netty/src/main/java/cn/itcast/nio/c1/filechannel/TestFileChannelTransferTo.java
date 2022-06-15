package cn.itcast.nio.c1.filechannel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.netty.c1.filechannel
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-04  21:40
 * @Description:
 * @Version: 1.0
 */
public class TestFileChannelTransferTo {
    public static void main(String[] args) {
        try ( FileChannel from = new FileInputStream("./netty/tmp/data.txt").getChannel();
              FileChannel to = new FileOutputStream("./netty/tmp/to.txt").getChannel();
        ) {
//            效率高，底层会利用操作系统的零拷贝进行优化
            from.transferTo(0, from.size(), to);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
