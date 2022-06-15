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
 * @CreateTime: 2022-06-04  21:46
 * @Description: for transferto api, once transfer 2G data, if file size greater than 2
 * @Version: 1.0
 */
public class TestFileChannelTransferToBigFile {
    public static void main(String[] args) {
        try (FileChannel from = new FileInputStream("./netty/tmp/3DMGAME-NFS9.v1.3.CHT.Green.rar").getChannel();
             FileChannel to = new FileOutputStream("./netty/tmp/NFS9_copy.rar").getChannel();
        ) {
            long size = from.size();
            for (long left = size; left > 0; ) {
                System.out.println("position:" + (size - left) + " left:" + left);
//            效率高，底层会利用操作系统的零拷贝进行优化，一次transfer上限是2G
                left -= from.transferTo((size - left), left, to);
            }
            from.transferTo(0, from.size(), to);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
