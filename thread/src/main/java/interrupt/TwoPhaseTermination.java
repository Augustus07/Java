package interrupt;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenb
 * @version 1.0.0
 * @ClassName TwoPhaseTermination.java
 * @createTime 2022年04月13日 19:36:00
 * @Description :两阶段终止模式的volatile实现
 */

@Slf4j(topic = "c.TwoPhaseTermination")
public class TwoPhaseTermination {
    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTerminal twoPhaseTerminal = new TwoPhaseTerminal();
        twoPhaseTerminal.start();
        Thread.sleep(3000);
        twoPhaseTerminal.stop();
    }
}

@Slf4j(topic = "c.TwoPhaseTerminal")
class TwoPhaseTerminal{
    private Thread monitor;

    public void start(){
        monitor = new Thread(()->{
            Thread current = Thread.currentThread();
            while (true){
                if(current.isInterrupted()){
                    log.debug("料理后事");
                    break;
                }
                try {
                    /*
                    * 非sleep状态下（运行状态）被打断，打断标志变真
                    * sleep状态下被打断，打断标记变真，sleep默认在有真置假
                    * */
                    log.debug("正常执行");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    //所以这里再次赋值为真,执行if
                    current.interrupt();
                }
            }
        });
        monitor.start();
    }

    public void stop(){
        monitor.interrupt();
    }



}

