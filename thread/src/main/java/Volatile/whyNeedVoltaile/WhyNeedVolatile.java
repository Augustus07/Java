package Volatile.whyNeedVoltaile;

/**
 * @author chenb
 * @version 1.0.0
 * @ClassName WhyNeedVolatile.java
 * @createTime 2022年04月12日 22:44:00
 * @Description :
 */
public class WhyNeedVolatile {
    private static boolean flag = false;
    public static void main(String[] args) {
        new Thread(()->{
            while (true){
                if(flag) break;
            }
            System.out.println("退出循环");
        },"t1").start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = true;
        System.out.println("修改flag");
    }
}
