package MultiLock;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.TestMultiLock")
public class TestMultiLock {

    public static void main(String[] args) {
        BigRoom bigRoom = new BigRoom();

        new Thread(()->{
            bigRoom.sleep();
        },"小南").start();

        new Thread(()->{
            bigRoom.study();
        },"小女").start();
    }
}

@Slf4j(topic = "c.BigRoom")
class BigRoom{

    private final Object studyRoom = new Object();

    private final Object bedRoom = new Object();

    public void sleep(){
        synchronized(bedRoom){
            log.debug("sleeping 2 hours");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("sleep error");
            }
            log.debug("sleeping end");
        }
    }

    public void study(){
        synchronized (studyRoom){
            log.debug("study 1 hours");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("sleep error");
            }
            log.debug("study end");
        }
    }

}