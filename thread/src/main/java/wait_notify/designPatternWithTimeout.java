package wait_notify;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.designPatternWithTimeout")
public class designPatternWithTimeout {
    public static void main(String[] args) {
        GuardedObject1 guardedObject = new GuardedObject1();

        new Thread(()->{
            log.debug("begin");
            Object response = guardedObject.get(2000);
            log.debug("结果是:{}",response);
        },"t1").start();

        new Thread(()->{
            log.debug("begin");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("sleep error");
            }
            guardedObject.complete(null);
        },"t2").start();
    }

}
class GuardedObject1{

    //    结果
    private Object response;

    //    获取结果
    public Object get(long timeout){
        synchronized (this){
            long begin = System.currentTimeMillis();
            long passedTime = 0;
//            没有结果
            while(response == null){
                long waitTime = timeout - passedTime;
                if (waitTime <= 0){
                    break;
                }
                try {
                    this.wait(waitTime);//防止虚假唤醒
                } catch (InterruptedException e) {
                    System.out.println("wait error");
                }
                passedTime = System.currentTimeMillis() - begin;
            }
        }
        return response;
    }

    //    产生结果
    public void complete(Object response){
        synchronized (this){
//            给结果成员变量赋值
            this.response = response;
            this.notifyAll();
        }
    }
}


