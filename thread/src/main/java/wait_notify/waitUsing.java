package wait_notify;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.waitUsing")
public class waitUsing {

    static final Object lock = new Object();
    static boolean hasCigarette = false;
    static boolean hasTakeout = false;

    public static void main(String[] args) {

        new Thread(()->{
            synchronized (lock){
                log.debug("有烟没？[{}]",hasCigarette);
                while(!hasCigarette){
                    log.debug("没烟，先歇会");
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        System.out.println("wait error");
                    }
                }
            }
            log.debug("有烟没？[{}]",hasCigarette);
            if (hasCigarette){
                log.debug("可以开始干活了");
            }else{
                log.debug("没干成活");
            }

        },"小南").start();

        new Thread(()->{
            synchronized (lock){
                log.debug("有外卖没？[{}]",hasTakeout);
                while(!hasTakeout){
                    log.debug("没外卖，先歇会");
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        System.out.println("wait error");
                    }
                }
            }
            log.debug("有外卖没？[{}]",hasTakeout);
            if (hasTakeout){
                log.debug("可以开始干活了");
            }else{
                log.debug("没干成活");
            }
        },"小女").start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("sleep error");
        }
        new Thread(()->{
            synchronized (lock){
                log.debug("外卖到了");

                lock.notifyAll();
                hasTakeout = true;
            }
        },"外卖员").start();

    }

}
