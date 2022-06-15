package cn.itcast.nio.c1.path;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.netty.c1.path
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-04  22:06
 * @Description: 检查文件是否存在
 * @Version: 1.0
 */
public class TestFileExists {
    public static void main(String[] args) {
        Path path = Paths.get("./netty/tmp/data.txt");
//        检查文件是否存在
        System.out.println(Files.exists(path));
    }

}
