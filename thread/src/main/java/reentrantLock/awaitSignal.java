package reentrantLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/*
*  ReentrantLock中的waitset可以定义多个，即休息室可以有多个。Synchronized中不管什么条件只有一个waitset休息室
*  ReentrantLock中的Condition.await()、Condition.signal、Condition.signalAll对应Synchronized中的wait()、notify()、notifyAll();
*
* */
@Slf4j(topic = "c.awaitSignal")
public class awaitSignal {

    static boolean hasCigarette = false;
    static boolean hasTakeout = false;
    static ReentrantLock reentrantLock = new ReentrantLock();
    //同一锁设置两个不同功能的休息室(waitset),也称作条件，即等烟的进入等烟的休息室，等外卖的进入等外卖的休息室
    // synchronized只有一个entrylist和waitset, reentrantLock有一个entrylist和多个waitset
    static Condition conditionCigarette = reentrantLock.newCondition();
    static Condition conditionTakeout = reentrantLock.newCondition();


    public static void main(String[] args) {

        new Thread(()->{
            reentrantLock.lock();
            try{
                log.debug("有烟没？[{}]",hasCigarette);
                while(!hasCigarette){
                    log.debug("没烟，先歇会");
                    try {
                        conditionCigarette.await();
                    } catch (InterruptedException e) {
                        System.out.println("await error");
                    }
                }
                log.debug("可以开始干活了");

            }finally {
                reentrantLock.unlock();
            }


        },"小南").start();

        new Thread(()->{
            reentrantLock.lock();
            try {
                log.debug("有外卖没？[{}]",hasTakeout);
                while(!hasTakeout){
                    log.debug("没外卖，先歇会");
                    try {
                        conditionTakeout.await();
                    } catch (InterruptedException e) {
                        System.out.println("wait error");
                    }
                }
                log.debug("可以开始干活了");
            }finally {
                reentrantLock.unlock();
            }
        },"小女").start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("sleep error");
        }
        new Thread(()->{
            reentrantLock.lock();
            try{
                log.debug("外卖到了");
                //唤醒reentrantLock中Takeoutwaitset的线程
                conditionTakeout.signal();
                hasTakeout = true;
            }finally {
               reentrantLock.unlock();
            }

        },"外卖员").start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("sleep error");
        }
        new Thread(()->{
            reentrantLock.lock();
            try{
                log.debug("烟到了");
                //唤醒reentrantLock中Cigarettrwaitset的线程
                conditionCigarette.signal();
                hasCigarette = true;
            }finally {
                reentrantLock.unlock();
            }
        },"送烟的").start();

    }
}
