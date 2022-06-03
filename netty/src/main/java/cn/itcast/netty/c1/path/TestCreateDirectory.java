package cn.itcast.netty.c1.path;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.netty.c1.path
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-04  22:08
 * @Description:
 * @Version: 1.0
 */
public class TestCreateDirectory {
    public static void main(String[] args) {
        Path path = Paths.get("./netty/tmp/d1");
        try {
            /*
            * 1. 如果目录已存在，会抛异常 FileAlreadyExistsException
            * 2. 不能一次创建多级目录，否则会抛异常 NoSuchFileException
            * */
            Files.createDirectory(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
