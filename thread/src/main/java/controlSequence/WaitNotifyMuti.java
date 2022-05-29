package controlSequence;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.WaitNotifyMuti")
public class WaitNotifyMuti {
    public static void main(String[] args) {

        Wait wait = new Wait(1,5);
        new Thread(()->{
            wait.print("a",1,2);
        },"t1").start();

        new Thread(()->{
            wait.print("b",2,3);
        },"t2").start();

        new Thread(()->{
            wait.print("c",3,1);
        },"t3").start();
    }
}

@Slf4j(topic = "c.Wait")
class Wait{

    public void print(String str, int waitFlag, int nextFlag){
        for (int i = 0; i < 5; i++) {
            synchronized (this){
                while(flag!=waitFlag){
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug(str);
                flag = nextFlag;
                this.notifyAll();
            }

        }
    }

    private int flag;
    private int loopNumber;

    public Wait(int flag, int loopNumber) {
        this.flag = flag;
        this.loopNumber = loopNumber;
    }
}
