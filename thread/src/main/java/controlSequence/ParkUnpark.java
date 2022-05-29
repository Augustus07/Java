package controlSequence;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

@Slf4j(topic = "c.ParkUnpark")
public class ParkUnpark {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            LockSupport.park();
            log.debug("run");
        }, "t1");

        t1.start();
        new Thread(()->{
            LockSupport.unpark(t1);
            log.debug("run");
        },"t2").start();
    }
}
