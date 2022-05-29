package controlSequence;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

@Slf4j(topic = "ParkUnparkMuti")
public class ParkUnparkMuti {

    static Thread t1;
    static Thread t2;
    static Thread t3;

    public static void main(String[] args) {
        Park park = new Park(5);

        t1 = new Thread(()->{
            park.print("a",t2);
        },"t1");
        t2 = new Thread(()->{
            park.print("b",t3);
        },"t2");
        t3 = new Thread(()->{
            park.print("c",t1);
        },"t3");

        t1.start();
        t2.start();
        t3.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LockSupport.unpark(t1);

    }
}

@Slf4j(topic = "c.Park")
class Park{

    public void print(String str, Thread next){
        for (int i = 0; i < loopNumber; i++) {
            LockSupport.park();
            log.debug(str);
            LockSupport.unpark(next);
        }
    }

    private int loopNumber;

    public Park(int loopNumber) {
        this.loopNumber = loopNumber;
    }
}
