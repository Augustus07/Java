package wait_notify;

import lombok.extern.slf4j.Slf4j;

//wait是Object对象的方法，且要配合synchronized使用;sleep是Thread对象的方法，无需配合synchronized
//wait(time)会释放掉锁，sleep(time)不会释放掉锁

@Slf4j(topic = "c.waitAndsleep")
public class waitAndsleep {

    static final Object lock = new Object();

    public static void main(String[] args) {
        new Thread(()->{
            synchronized (lock){
                log.debug("获得锁");
                try {
//                    Thread.sleep(20000);
                    lock.wait(20000);
                } catch (InterruptedException e) {
                    System.out.println("sleep error");
                }
            }
        },"t1").start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("sleep error");
        }
        synchronized(lock){
            log.debug("获得锁");
        }

    }

}
