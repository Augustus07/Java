package controlSequence;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

@Slf4j(topic = "c.ParkInterrupt")
public class ParkInterrupt {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            LockSupport.park();
//            调用interrupt方法后，打断标记置为真，需要重新设置打断标记为false
            Thread.interrupted();
            log.debug("run");
            LockSupport.park();
            Thread.interrupted();
            log.debug("run");
        }, "t1");

        t1.start();

        new Thread(()->{
            log.debug("run");
            t1.interrupt();
        },"t2").start();

        new Thread(()->{
            log.debug("run");
            t1.interrupt();
        },"t3").start();


    }
}
