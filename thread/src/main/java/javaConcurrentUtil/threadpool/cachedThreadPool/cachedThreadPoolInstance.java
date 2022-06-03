package javaConcurrentUtil.threadpool.cachedThreadPool;

import lombok.extern.slf4j.Slf4j;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: javaConcurrentUtil.threadpool.cachedThreadPool
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-05-29  21:06
 * @Description:
 * @Version: 1.0
 */
/*
*  cachedThreadPool底层是SynchronousQueue
*
*  SynchronousQueue的作用机制是一手交钱一手交货
* */
@Slf4j(topic = "c.cachedThreadPool")
public class cachedThreadPoolInstance{
    public static void main(String[] args) {
        java.util.concurrent.SynchronousQueue<Integer> integers = new java.util.concurrent.SynchronousQueue<>();
        new Thread(() -> {
            try {
                log.debug("putting {}", 1);
                integers.put(1);
                log.debug("{} putted...", 1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "t1").start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        new Thread(() -> {
            try {
                log.debug("taking {}", 1);
                integers.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "t2").start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        new Thread(() -> {
            try {
                log.debug("taking {}", 2);
                integers.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "t3").start();

    }

}
