package Volatile.addVolatile;

/**
 * @author chenb
 * @version 1.0.0
 * @ClassName test2.java
 * @createTime 2022年04月12日 22:56:00
 * @Description : synchronized也可以保证变量的可变性，但是
 * synchronized需要用到monitor，volatile相比于sycnhronized在变量可见性上更加轻量级
 */
public class test2 {
    private static boolean flag = false;
    private static Object lock = new Object();

    public static void main(String[] args) {

        new Thread(()->{
                while (true){
                    synchronized (lock){
                        if(flag) break;
                    }
                }
                System.out.println("执行完毕");

        },"t1").start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (lock){
            flag = true;
            System.out.println("执行完毕");
        }
    }
}
