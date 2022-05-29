package reentrantLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chenb
 * @version 1.0.0
 * @ClassName Synchronize.java
 * @createTime 2022年04月12日 21:28:00
 * @Description :
 */
public class Synchronize {

    static boolean flag = false;
    public static void main(String[] args) {
        final ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        new Thread(()->{
            lock.lock();
            try{
                while (!flag){
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t1执行");
            }finally {
                lock.unlock();
            }

        },"t1").start();

        new Thread(()->{
            lock.lock();
            try{
                System.out.println("t2执行");
                flag = true;
                condition.signal();
            }finally {
                lock.unlock();
            }
        },"t2").start();


    }

}
