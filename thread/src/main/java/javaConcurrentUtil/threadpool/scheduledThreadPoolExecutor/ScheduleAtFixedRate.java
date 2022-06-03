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
 * @CreateTime: 2022-05-29  23:01
 * @Description: pool.scheduleAtFixedRate 以固定速率（时延）执行任务
 * 固定速率 = Max(线程池设置的时延 ， 单次任务执行的时间)
 * @Version: 1.0
 */
@Slf4j(topic = "c.ScheduleAtFixedRate")
public class ScheduleAtFixedRate {
    public static void main(String[] args) {
//        test04();
        test05();

    }

    //    任务执行时间 = 2s, 线程池设置时延 = 1s，所以任务每2s执行一次
    private static void test05() {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        log.debug("start...");
        pool.scheduleAtFixedRate(() -> {
            log.debug("running...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    //    任务执行时间 = 0s, 线程池设置时延 = 1s，所以任务每1s执行一次
    private static void test04() {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        log.debug("start...");
        pool.scheduleAtFixedRate(() -> {
            log.debug("running...");
        }, 1, 1, TimeUnit.SECONDS);

    }

}
