package cn.itcast.netty.c1.path;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.netty.c1.path
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-04  22:29
 * @Description:
 * @Version: 1.0
 */
public class TestFilesWalkFileTree {
    public static void main(String[] args) throws IOException {
//        输出jdk目录下所有文件
//        test1();

//        输出jdk目录下所有的jar文件
//        test2();

    }

    private static void test2() throws IOException {
        AtomicInteger jarCount = new AtomicInteger();
//        int count = 0;
        Files.walkFileTree(Paths.get("E:\\environment\\Java\\jdk1.8.0_91"), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.toString().endsWith(".jar")) {
                    System.out.println(file);
                    jarCount.incrementAndGet();
                }
                return super.visitFile(file, attrs);
            }
        });

        System.out.println("jar count:" + jarCount);
    }

    private static void test1() throws IOException {
        AtomicInteger dirCount = new AtomicInteger();
        AtomicInteger fileCount = new AtomicInteger();
//        int count = 0;
        Files.walkFileTree(Paths.get("E:\\environment\\Java\\jdk1.8.0_91"), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                System.out.println("====>" + dir);
                dirCount.incrementAndGet();
//                注意这里为什么不把计数器定义为int? 由于匿名类访问外部的局部变量时，局部变量相当于是final的，所以不能用，用一个引用类型の累加器就可以
//                count++;
                return super.preVisitDirectory(dir, attrs);
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println(file);
                fileCount.incrementAndGet();
                return super.visitFile(file, attrs);
            }
        });

        System.out.println("dir count:" + dirCount);
        System.out.println("file count:" + fileCount);
    }

}
