package javaConcurrentUtil.blockQueue.arrayBlockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: javaConcurrentUtil.blockQueue.arrayBlockingQueue
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-03  21:51
 * @Description:
 * @Version: 1.0
 */
public class ArrayBlockingQueueDemo {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
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
