package wait_notify;

import lombok.extern.slf4j.Slf4j;

/*
*
* notify 与 notifyall的区别：
* synchronized有两个池，锁池和等待池
* notify通知等待池中的某一个进入锁池， notifyall通知等待池中的所有进入锁池.
* 只有锁池中的线程竞争锁
* 等待池中的线程不具有竞争锁的资格，只有等notify或者notifyall才有机会从等待池进入锁池
*
* */
@Slf4j(topic = "c.test1")
public class test1 {

    static final Object lock = new Object();

    public static void main(String[] args) {

        new Thread(()->{
            synchronized(lock){
                log.debug("执行...");
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    System.out.println("wait error");
                }
                log.debug("其他代码...");
            }

        },"t1").start();

        new Thread(()->{
            synchronized(lock){
                log.debug("执行...");
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    System.out.println("wait error");
                }
                log.debug("其他代码...");
            }

        },"t2").start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("sleep error");
        }
        log.debug("唤醒 obj 上其他线程");
        synchronized (lock) {
            lock.notify();
//            lock.notifyAll();
        }

    }

}
