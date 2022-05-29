package deadlock;

import lombok.extern.slf4j.Slf4j;
import sun.util.locale.provider.LocaleServiceProviderPool;

/*
* deadlock classic case 死锁
* */
@Slf4j(topic = "c.TestDeadLock")
public class TestDeadLock {

    public static void main(String[] args) {
        final Object a = new Object();
        final Object b = new Object();

        new Thread(()->{
            synchronized (a){
                System.out.println("获得锁a");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    System.out.println("睡眠打断");
                }
                synchronized (b){
                    System.out.println("获得锁b");
                }
            }
        }, "t1").start();

        new Thread(()->{
            synchronized (b){
                System.out.println("获得锁b");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("睡眠打断");
                }
                synchronized (a){
                    System.out.println("获得锁a");
                }
            }
        }, "t2").start();
    }
}
