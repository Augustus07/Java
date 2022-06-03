package javaConcurrentUtil.threadpool.fixedThreadPool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: javaConcurrentUtil.threadpool
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-05-29  20:48
 * @Description:
 * @Version: 1.0
 */

/*
*  核心线程数 == 最大线程数（没有救急线程被创建），因此也无需超时时间
*  阻塞队列是无界的(Integer.MAX_VALUE)，可以放任意数量的任务
* */
@Slf4j(topic = "c.ThreadPoolExecutors")
public class FixedThreadPoolInstance {

    public void get(int a, int b) {

    }

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(2, new ThreadFactory() {
            private AtomicInteger t = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "mypool_t" + t.getAndIncrement());
            }
        });

        pool.execute(() -> {
            log.debug("1");
        });

        pool.execute(() -> {
            log.debug("2");
        });

        pool.execute(() -> {
            log.debug("3");
        });
    }

}
