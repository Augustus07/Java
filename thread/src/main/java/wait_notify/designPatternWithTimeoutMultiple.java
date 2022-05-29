package wait_notify;

import lombok.extern.slf4j.Slf4j;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
/*
* 一一对应的 wait notify线程设计
* */
@Slf4j(topic = "c.designPatternWithTimeoutMultiple")
public class designPatternWithTimeoutMultiple {

    public static void main(String[] args) {

        for (int i = 0; i < 3; i++) {
            new People().start();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("sleep error");
        }
        for (Integer id : Mailboxes.getIds()){
            new Postman(id,"内容" + id).start();
        }
    }
}

@Slf4j(topic = "c.People")
class People extends Thread{
    @Override
    public void run() {
        GuardedObjectv3 guardedObject = Mailboxes.createGuardedObject();
        log.debug("开始收信 id:{}",guardedObject.getId());
        Object mail = guardedObject.get(5000);
        log.debug("收到信id:{},内容:{}",guardedObject.getId(),mail);

    }
}

@Slf4j(topic = "c.Postman")
class Postman extends Thread{
    private int id;
    private String mail;

    public Postman(int id, String mail){
        this.id = id;
        this.mail = mail;
    }

    @Override
    public void run() {
        GuardedObjectv3 guardedObject = Mailboxes.getGuardedObject(id);
        log.debug("送信 id:{},内容:{}",id,mail);
        guardedObject.complete(mail);
    }
}

/*
* 以下内容的类是通用的，即Mailboxes配合GuarderedObject实现生产者和消费者的一一对应
*
* RPC框架中这种写法很常见
* */
class Mailboxes{
//    Hashtable线程安全
    private static Map<Integer,GuardedObjectv3> boxes = new Hashtable<>();

    private static int id = 1;

    private static synchronized int generateId(){
        return id++;
    }

    //map的remove方法返回搜寻值且移除搜寻值
    public static GuardedObjectv3 getGuardedObject(int id){
        return boxes.remove(id);
    }

    public static GuardedObjectv3 createGuardedObject(){
        GuardedObjectv3 go = new GuardedObjectv3(generateId());
        boxes.put(go.getId(),go);
        return go;
    }

    public static Set<Integer> getIds(){
        return boxes.keySet();
    }

}

class GuardedObjectv3{

    private int id;

    public GuardedObjectv3(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

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
                    this.wait(waitTime);
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