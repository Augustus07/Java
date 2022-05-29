package Volatile.balking;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenb
 * @version 1.0.0
 * @ClassName TwoPhaseTermination.java
 * @createTime 2022年04月13日 19:36:00
 * @Description : 两阶段终止模式的volatile实现, 加上犹豫模式(Balking)
 * 犹豫模式： 单例模式，即开启一个心跳检测即可
 */

@Slf4j(topic = "c.TwoPhaseTermination")
public class TwoPhaseTermination {
    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTerminal t1= new TwoPhaseTerminal();
        TwoPhaseTerminal t2= new TwoPhaseTerminal();
        TwoPhaseTerminal t3= new TwoPhaseTerminal();
        t1.start();
//        t1.start();
        t2.start();
        t3.start();
        Thread.sleep(3500);
        t2.stop();
    }
}

@Slf4j(topic = "c.TwoPhaseTerminal")
class TwoPhaseTerminal{
    private static Thread monitor;
    private static volatile boolean flag = false;
    private static boolean once = false;

    public static void start(){
        synchronized (TwoPhaseTerminal.class){
            if(once){
                return;
            }
            once = true;
            monitor = new Thread(()->{
                Thread current = Thread.currentThread();
                while (true){
                    if(flag){
                        log.debug("料理后事");
                        break;
                    }
                    try {
                        log.debug("正常执行");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {

                    }
                }
            });
            monitor.start();
        }

    }

    public static synchronized void stop(){
        flag = true;
//        这里还要加一个interrupt方法是由于，如果在睡眠的时候flag置为真，就会睡够了在区进入if()语句内，会增加时间
//        如果加入interrupt,就会保证睡眠的时候立即打断,配合volatile机制立刻判断if（）
        monitor.interrupt();
    }



}

