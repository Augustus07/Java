package countDownLatch;

import javaConcurrentUtil.reentrantreadwritelock.TestReentrantreadwritelock;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: countDownLatch
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-01  23:01
 * @Description:
 * @Version: 1.0
 */
public class WangZheLoad {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);
        Random r = new Random();
        String[] all = new String[10];
        for (int i = 0; i < 10; i++) {
            int k = i;
            service.submit(() -> {
                for (int j = 0; j <= 100; j++) {
                    try {
                        Thread.sleep(r.nextInt(100));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    all[k] = j + "%";
                    System.out.print("\r" + Arrays.toString(all));
                }
            });
        }
        service.shutdown();
    }

}
