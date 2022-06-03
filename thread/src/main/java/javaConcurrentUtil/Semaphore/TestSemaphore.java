package javaConcurrentUtil.Semaphore;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: javaConcurrentUtil.Semaphore
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-01  22:34
 * @Description:
 * @Version: 1.0
 */
@Slf4j(topic = "c.TestSemaphore")
public class TestSemaphore {

    public static void main(String[] args) {

//        test1();

        test2();

    }

//    使用信号量
    private static void test2() {
        //        创建信号量
        Semaphore semaphore = new Semaphore(3);
//        创建10个线程，使用信号量
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    log.debug("running...");
                    Thread.sleep(1000);
                    log.debug("end...");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    semaphore.release();
                }
            }).start();
        }
    }

//    不使用信号量
    private static void test1() {
        //        创建10个线程
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                log.debug("running...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.debug("end...");
            }).start();
        }
    }

}
