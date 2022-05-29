package threadPool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.SynchronousQueue;

/*
* 1.核心线程数是0，最大线程数是 Integer.MAX_VALUE,救急线程的空闲生存时间是60s，意味着①全部是救急线程②救济线程可以无限创建
*
* 2.队列采用了SynchronousQueue实现特点是，它没有容量，没有线程来取就放不进去
* */
@Slf4j(topic = "c.TestSynchronousQueue")
public class TestSynchronousQueue {
    public static void main(String[] args) {
        SynchronousQueue<Integer> integers = new SynchronousQueue<>();

        new Thread(() -> {
            try {
                log.debug("putting {}", 1);
                integers.put(1);
                log.debug("{} putted", 1);

                log.debug("putting {}", 2);
                integers.put(2);
                log.debug("{} putted", 2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            try {
                log.debug("taking {}", 1);
                integers.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2").start();

        new Thread(() -> {
            try {
                log.debug("taking {}", 2);
                integers.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t3").start();

    }
}
