package controlSequence;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.WaitNotify")
public class WaitNotify {
    static final Object lock = new Object();
    static boolean lockjudge = false;

    public static void main(String[] args) {
        new Thread(()->{
            synchronized (lock){
                while(!lockjudge){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        System.out.println("wait error");
                    }
                }
                log.debug("run");
            }
        },"t1").start();

        new Thread(()->{
            synchronized(lock){
                log.debug("run");
                lockjudge = true;
                lock.notify();
            }
        },"t2").start();

    }
}
