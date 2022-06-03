package cn.itcast.netty.c1.path;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.netty.c1.path
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-04  22:21
 * @Description: 移动文件
 * @Version: 1.0
 */
public class TestFileMove {
    public static void main(String[] args) {
        Path source = Paths.get("./netty/tmp/data.txt");
        Path target = Paths.get("./netty/tmp/data.txt");

        try {
//            StandardCopyOption.ATOMIC_MOVE 保证文件移动的原子性
            Files.move(source, target, StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
