package cn.itcast.nio.c1.path;

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
 * @CreateTime: 2022-06-04  22:14
 * @Description: Files.copy 与 FileChannel的零拷贝效率差不多，底层调用的是操作系统提供的接口，但是这两拷贝的实现方式不一样
 * @Version: 1.0
 */
public class TestFileCopy {
    public static void main(String[] args) {
        Path source = Paths.get("./netty/tmp/data.txt");
        Path target = Paths.get("./netty/tmp/target.txt");

        try {
//            如果文件已经存在，会抛异常 FileAlreadyExistsException
            Files.copy(source, target);

//            如果文件已经存在，并且希望不要抛异常，直接覆盖
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
