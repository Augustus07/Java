package javaConcurrentUtil.threadpool.scheduledThreadPoolExecutor;

import javaConcurrentUtil.threadpool.threadpoolSubmitTask.TestInvokeAll;
import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: javaConcurrentUtil.threadpool.scheduledThreadPoolExecutor
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-05-29  22:28
 * @Description:
 * @Version: 1.0
 */

@Slf4j(topic = "c.TestTimer")
public class TestTimer {

    public static void main(String[] args) {
//        test01();
//        test02();
//        test03();
    }

//    可以看到，当task1抛异常的时候，task2也不会执行了
    private static void test03() {
        Timer timer = new Timer();
        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                log.debug("task1");
                int i = 1 / 0;
            }
        };

        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                log.debug("task2");
            }
        };

        log.debug("start...");
//        注意这里的delay都是从timer对象的创建时间开始计算的
        timer.schedule(task1, 1000);
        timer.schedule(task2, 1000);
    }

    //    可以看到由于task1执行会花费2s，将task2执行时间延后了2s
    private static void test02() {
        Timer timer = new Timer();
        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                log.debug("task1");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                log.debug("task2");
            }
        };

        log.debug("start...");
//        注意这里的delay都是从timer对象的创建时间开始计算的
        timer.schedule(task1, 1000);
        timer.schedule(task2, 1000);
    }


    private static void test01() {
        Timer timer = new Timer();
        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                log.debug("task1");
            }
        };

        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                log.debug("task2");
            }
        };

        log.debug("start...");
//        注意这里的delay都是从timer对象的创建时间开始计算的
        timer.schedule(task1, 1000);
        timer.schedule(task2, 1000);
    }

}
