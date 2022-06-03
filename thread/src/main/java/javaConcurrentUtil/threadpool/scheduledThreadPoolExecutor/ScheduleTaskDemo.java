package javaConcurrentUtil.threadpool.scheduledThreadPoolExecutor;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: javaConcurrentUtil.threadpool.scheduledThreadPoolExecutor
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-05-30  15:36
 * @Description:
 * @Version: 1.0
 */
public class ScheduleTaskDemo {

    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);

        //只会获取本周四时间，意味着，如果今天周一，会获取到未来的周四，如果今天周五，会获取昨天的周四
        LocalDateTime time = now.withHour(18).withSecond(0).withNano(0).with(DayOfWeek.THURSDAY);

        // 处理获取过去周四的情况
        if (now.compareTo(time) > 0) {
            time = time.plusWeeks(1);
        }

        System.out.println(time);

        // initialDelay 代表当前时间和周四的时间差
        // period 代表一周的间隔时间
        long initailDelay = Duration.between(now, time).toMillis();
        long period = 1000 * 60 * 60 * 24 * 7;
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        pool.scheduleAtFixedRate(() -> {
            System.out.println("running");
        }, initailDelay, period, TimeUnit.MILLISECONDS);

    }

}
