package javaConcurrentUtil.blockQueue.linkedBlockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: javaConcurrentUtil.blockQueue.linkedBlockingQueue
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-03  21:58
 * @Description:
 * @Version: 1.0
 */
public class LinkedBlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> queue = new LinkedBlockingQueue<>(3);
        new Thread(() -> {
            try {
                queue.put("a");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(() -> {
            try {
                queue.put("b");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(() -> {
            try {
                queue.put("c");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(() -> {
            try {
                queue.put("d");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        Thread.sleep(1000);
        System.out.println(queue.take());
        Thread.sleep(1000);
        System.out.println(queue.take());
        Thread.sleep(1000);
        System.out.println(queue.take());
        Thread.sleep(1000);
        System.out.println(queue.take());
    }

}
