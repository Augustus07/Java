package cn.itcast.netty.c1.path;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.netty.c1.path
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-04  21:58
 * @Description:
 * @Version: 1.0
 */
public class TestPath {
    public static void main(String[] args) {
//        相对路径 使用 user.dir 环境变量来定位 data.txt
        Path source1 = Paths.get("./netty/tmp/data.txt");

//        绝对路径
        Path source2 = Paths.get("E:\\Mycode\\java\\java_practice\\netty\\tmp\\data.txt");

//        绝对路径
        Path source3 = Paths.get("E:/Mycode/java/java_practice/netty/tmp/data.txt");

//        代表了一个project
        Path projects = Paths.get("E:/Mycode/java/java_practice/netty/", "projects");

//        注意这里netty目录下，note 和 tmp 是同级目录
        Path path = Paths.get("E:/Mycode/java/java_practice/netty/note/../tmp");
        System.out.println(path);
//        正常化目录
        System.out.println(path.normalize());

    }

}
