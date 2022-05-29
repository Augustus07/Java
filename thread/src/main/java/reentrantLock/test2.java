package reentrantLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

//ReentrantLock可以通过设置锁超时 tryLock方法直接判断（或等一段时间）能不能获得锁，不会像synchronized死等
@Slf4j(topic = "c.test2")
public class test2 {
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            log.debug("尝试获得锁");
            try {
//                设置等待2s如果获取到锁返回true，否则返回false
//                也可以不用等lock.tryLock()
                if(!lock.tryLock(2, TimeUnit.SECONDS)){
                    log.debug("获取不到锁");
                    return;
                }
            } catch (InterruptedException e) {
                log.debug("获取不到锁");
                return;
            }
            try{
                log.debug("获取到锁");
            }finally {
                lock.unlock();
                log.debug("释放掉锁");
            }
        },"t1");

        lock.lock();
        log.debug("获得到锁");
        t1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("sleep error");
        }
        try {
           log.debug("释放掉锁");
        }finally {
            lock.unlock();
        }


    }
}
