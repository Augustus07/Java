package park_unpark;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/*
* 注意,unpark在park前发生，也是可以打断这个park的
* 而且，unpark最多补充一份干粮，如下代码所示，
* t1线程启动后在2s,4s处park
* main线程启动后在1s处两次unpark,
* 结果是，虽然两个unpark但是只补充一份干粮，t1线程会停在4s的park处
* */
@Slf4j(topic = "c.TestParkUnpark")
public class TestParkUnpark {
    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            log.debug("start");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("sleep error");
            }
            log.debug("park");
            LockSupport.park();
            log.debug("resume");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("sleep error");
            }
            log.debug("park");
            LockSupport.park();
            log.debug("resume");
        },"t1");
        t1.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("sleep error");
        }
        log.debug("unpark");
        LockSupport.unpark(t1);
        LockSupport.unpark(t1);
    }
}
