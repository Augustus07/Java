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
 * @CreateTime: 2022-06-04  22:26
 * @Description:
 * @Version: 1.0
 */
public class TestDirectoryDelete {
    public static void main(String[] args) {
        Path target = Paths.get("./netty/tmp/d1");
        try {
            //如果目录还有内容，会抛异常 DirectoryNotEmptyException
            Files.delete(target);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
