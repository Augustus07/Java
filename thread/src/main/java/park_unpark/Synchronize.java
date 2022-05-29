package park_unpark;

import java.util.concurrent.locks.LockSupport;

/**
 * @author chenb
 * @version 1.0.0
 * @ClassName Synchronize.java
 * @createTime 2022年04月12日 21:26:00
 * @Description : 通过park unpark方法实现多线程同步
 */
public class Synchronize {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            LockSupport.park();
            System.out.println("t1执行");
        }, "t1");
        t1.start();

        new Thread(()->{
            System.out.println("t2执行");
            LockSupport.unpark(t1);
        }, "t2").start();
    }
}
