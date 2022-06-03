package javaConcurrentUtil.threadpool.scheduledThreadPoolExecutor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: javaConcurrentUtil.threadpool.scheduledThreadPoolExecutor
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-05-29  22:44
 * @Description:ScheduledThreadPool 相比于Timer就不会产生异常不执行，和延时执行的情况
 * @Version: 1.0
 */
@Slf4j(topic = "c.ScheduledThreadPool")
public class ScheduledThreadPool {
    public static void main(String[] args) {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);

        pool.schedule(() -> {
            log.debug("task1");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, 1, TimeUnit.SECONDS);

        pool.schedule(() -> {
            log.debug("task2");
        }, 1, TimeUnit.SECONDS);

    }

}
