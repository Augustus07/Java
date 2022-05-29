package reentrantLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/*
* ReentrantLock 可打断性，synchronized没有这个特性,具体讲
* ReentrantLock的lockInterruptibly当获取不到锁处于等待状态时，可以被其他线程打断，而不会向Synchronized实现中的while(true)死等
 *
* */
@Slf4j(topic = "c.test1")
public class test1 {
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            try {
                log.debug("尝试获得锁");
//                尝试获得锁，但是该锁内的代码可以被另一个线程打断（synchronized不能实现可打断）,打断后进入catch,直接return，不会像
//                Synchronized一样用while(true)死等
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                log.debug("没有获得锁,被打断，返回");
                return;
            }
            try {
                log.debug("获取到锁");
            }finally {
                log.debug("释放锁");
                lock.unlock();
            }
        },"t1");


//        主线程先获得锁，t1不能获取锁，主线程睡眠后打断t1的等待
        lock.lock();
        t1.start();


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("sleep interrupted");
        }
        log.debug("尝试打断");
        t1.interrupt();
        lock.unlock();
    }

}
