package Volatile.interruptRemove;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenb
 * @version 1.0.0
 * @ClassName TwoPhaseTermination.java
 * @createTime 2022年04月13日 19:36:00
 * @Description : 两阶段终止模式的volatile实现
 */

@Slf4j(topic = "c.TwoPhaseTermination")
public class TwoPhaseTermination {
    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTerminal twoPhaseTerminal = new TwoPhaseTerminal();
        twoPhaseTerminal.start();
        Thread.sleep(3500);
        twoPhaseTerminal.stop();
    }
}

@Slf4j(topic = "c.TwoPhaseTerminal")
class TwoPhaseTerminal{
    private Thread monitor;
    private volatile boolean flag = false;

    public void start(){
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

    public void stop(){
        flag = true;
//        这里还要加一个interrupt方法是由于，如果在睡眠的时候flag置为真，就会睡够了在区进入if()语句内，会增加时间
//        如果加入interrupt,就会保证睡眠的时候立即打断,配合volatile机制立刻判断if（）
        monitor.interrupt();
    }



}

