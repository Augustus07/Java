package deadlock;

import lombok.extern.slf4j.Slf4j;

//活锁
@Slf4j(topic = "c.TestAliveLock")
public class TestAliveLock {

    static volatile int count = 10;
    static final Object lock = new Object();

    public static void main(String[] args) {
        new Thread(()->{
            while(count > 0){
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    System.out.println("sleep error");
                }
                count--;
                log.debug("count:{}",count);
            }
        },"t1").start();

        new Thread(()->{
           while (count < 20){
               try {
                   Thread.sleep(200);
               } catch (InterruptedException e) {
                   System.out.println("sleep error");
               }
               count++;
               log.debug("count:{}",count);
           }
        },"t2").start();

    }
}
