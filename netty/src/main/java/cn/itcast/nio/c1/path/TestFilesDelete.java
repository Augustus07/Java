package cn.itcast.nio.c1.path;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.netty.c1.path
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-04  22:48
 * @Description:
 * @Version: 1.0
 */
public class TestFilesDelete {
    public static void main(String[] args) throws IOException {
//        删除一个非空文件夹会报异常
//        Files.delete(Paths.get("./netty/Snipaste-1.16.2-x64"));

//        要删除的思路应该是这样的，进入非空文件夹，删除所有文件，然后删除目录
//        Files.walkFileTree(Paths.get("./netty/Snipaste-1.16.2-x64"), new SimpleFileVisitor<Path>() {
//            @Override
//            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
//                System.out.println("====> 进入" + dir);
//                return super.preVisitDirectory(dir, attrs);
//            }
//
//            @Override
//            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
//                System.out.println(file);
//                return super.visitFile(file, attrs);
//            }
//
//            @Override
//            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
//                System.out.println("<===== 退出" + dir);
//                return super.postVisitDirectory(dir, exc);
//            }
//        });

        Files.walkFileTree(Paths.get("./netty/Snipaste-1.16.2-x64"), new SimpleFileVisitor<Path>() {
//            @Override
//            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
//                System.out.println("====> 进入" + dir);
//                return super.preVisitDirectory(dir, attrs);
//            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
//                System.out.println(file);
                Files.delete(file);
                return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
//                System.out.println("<===== 退出" + dir);
                Files.delete(dir);
                return super.postVisitDirectory(dir, exc);
            }
        });

    }
}
