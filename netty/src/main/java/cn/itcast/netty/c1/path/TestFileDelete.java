package cn.itcast.netty.c1.path;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.netty.c1.path
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-04  22:24
 * @Description:
 * @Version: 1.0
 */
public class TestFileDelete {
    public static void main(String[] args) {
        Path target = Paths.get("./netty/tmp/target.txt");
        try {
//            如果文件不存在，会抛异常NoSuchFileException
            Files.delete(target);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
