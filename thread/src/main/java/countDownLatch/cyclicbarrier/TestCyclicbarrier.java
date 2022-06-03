package countDownLatch.cyclicbarrier;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: countDownLatch.cyclicbarrier
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-01  23:08
 * @Description: CyclicBarrier循环栅栏，比CountdownLatch更好
 * 主要功能， 当计数减到0时，又会重新恢复设置的初始值
 * @Version: 1.0
 */
@Slf4j(topic = "c.TestCyclicbarrier")
public class TestCyclicbarrier {

    boolean flag = false;
    public static void main(String[] args) {
//        test1();

        Thread mainthread = Thread.currentThread();
        ExecutorService service = Executors.newFixedThreadPool(3);
        CyclicBarrier barrier = new CyclicBarrier(2, () -> {
            log.debug("task1 task2 finish...");
        });


        for (int i = 0; i < 3; i++) {
//            这里每次循环都要创建一个新的CountDownLatch对象,有没有办法只创建一个类似功能对象呢？有的 cyclicbarrier
            service.submit(() -> {
                log.debug("task1 start...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            });
            service.submit(() -> {
                log.debug("task2 start...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        service.shutdown();


        log.debug("结束循环，继续执行");
    }


    private static void test1() {
        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 3; i++) {
//            这里每次循环都要创建一个新的CountDownLatch对象,有没有办法只创建一个类似功能对象呢？有的 cyclicbarrier
            CountDownLatch latch = new CountDownLatch(2);
            service.submit(() -> {
                log.debug("task1 start...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                latch.countDown();
            });
            service.submit(() -> {
                log.debug("task1 start...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                latch.countDown();
            });
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("task1 task2 finish...");
        }
        service.shutdown();
    }

}
