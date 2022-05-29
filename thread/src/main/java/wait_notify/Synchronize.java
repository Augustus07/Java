package wait_notify;

/**
 * @author chenb
 * @version 1.0.0
 * @ClassName Synchronize.java
 * @createTime 2022年04月12日 21:19:00
 * @Description : 使用synchronized中的 wait-notify实现进程同步控制
 */
public class Synchronize {

    static boolean flag = false;

    public static void main(String[] args) {

        final Object lock = new Object();

        new Thread(()->{
            synchronized (lock){
                while (!flag){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t1执行");
            }

        },"t1").start();

        new Thread(()->{
            synchronized (lock){
                System.out.println("t2执行");
                flag = true;
                lock.notify();
            }
        },"t2").start();


    }
}
