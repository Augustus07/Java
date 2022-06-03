package countDownLatch;

import exercise.ExerciseTansfer;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: countDownLatch
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-01  22:53
 * @Description:
 * @Version: 1.0
 */
@Slf4j(topic = "c.TestCountDownLatch")
public class TestCountDownLatch {

    public static void main(String[] args) {
//        test1();

        CountDownLatch latch = new CountDownLatch(3);
        ExecutorService service = Executors.newFixedThreadPool(4);
        service.submit(() -> {
            log.debug("begin...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            latch.countDown();
            log.debug("end...");
        });
        service.submit(() -> {
            log.debug("begin...");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            latch.countDown();
            log.debug("end...");
        });
        service.submit(() -> {
            log.debug("begin...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            latch.countDown();
            log.debug("end...");
        });

        service.submit(() -> {
            try {
                log.debug("waiting...");
                latch.await();
                log.debug("wait end...");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });



    }

    private static void test1() {
        CountDownLatch latch = new CountDownLatch(3);

        new Thread(() -> {
            log.debug("begin...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            latch.countDown();
            log.debug("end...");
        }).start();
        new Thread(() -> {
            log.debug("begin...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            latch.countDown();
            log.debug("end...");
        }).start();
        new Thread(() -> {
            log.debug("begin...");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            latch.countDown();
            log.debug("end...");
        }).start();

        log.debug("waiting...");
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.debug("waiting end, continue...");
    }

}
