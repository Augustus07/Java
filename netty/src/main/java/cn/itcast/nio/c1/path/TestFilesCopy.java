package cn.itcast.nio.c1.path;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.netty.c1.path
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-04  22:42
 * @Description: file只能一个一个拷贝， 要是想拷贝一个文件夹怎么办，用 Files.walk方法
 * @Version: 1.0
 */
public class TestFilesCopy {

    public static void main(String[] args) throws IOException {
        String source = "F:\\Program Files (x86)\\Snipaste-1.16.2-x64";
        String target = "./netty/Snipaste-1.16.2-x64";

        Files.walk(Paths.get(source)).forEach(path -> {
            try {
                String targetName = path.toString().replace(source, target);
                if (Files.isDirectory(path)) {
                    Files.createDirectory(Paths.get(targetName));
                } else if (Files.isRegularFile(path)) {
                    Files.copy(path, Paths.get(targetName));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
