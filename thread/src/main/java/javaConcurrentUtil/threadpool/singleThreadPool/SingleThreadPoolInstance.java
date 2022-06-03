package javaConcurrentUtil.threadpool.singleThreadPool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: javaConcurrentUtil.threadpool.singleThreadPool
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-05-29  21:19
 * @Description:
 * @Version: 1.0
 */

/*
* 线程池里只有一个工作线程，即提交的任务一个一个执行
* 和单线程的区别是， 单线程碰到异常会抛出，不会再执行之后的任务，而SingleThreadPoolInstance会new一个新的Thread出来
 * */
@Slf4j(topic = "c.SingleThreadPoolInstance")
public class SingleThreadPoolInstance {
    public static void main(String[] args) {
        test2();
    }

    public static void test2() {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        pool.execute(() -> {
            log.debug("1");
            int i = 1 / 0;
        });

        pool.execute(() -> {
            log.debug("2");
        });

        pool.execute(() -> {
            log.debug("3");
        });
    }
}
