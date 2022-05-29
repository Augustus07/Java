package cn.itcast.jvm.t4.avo;

public class Demo4_3 {

    static int x;

    public static void main(String[] args) {


        Thread t2 = new Thread(()->{
            while(true) {
                if(Thread.currentThread().isInterrupted()) {
                    System.out.println(x);
                    break;
                }
            }
        }, "cn/itcast/jvm/t2");
        t2.start();

        new Thread(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            x = 10;
            t2.interrupt();
        }, "cn/itcast/jvm/t1").start();

        while(!t2.isInterrupted()) {
            Thread.yield();
        }
        System.out.println(x);

    }
}
