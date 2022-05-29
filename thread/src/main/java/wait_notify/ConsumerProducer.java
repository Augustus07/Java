package wait_notify;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

/*
* 经典生产者、消费者模型。生产者消费者不需要一一对应
* */
@Slf4j(topic = "c.ConsumerProducer")
public class ConsumerProducer {
    public static void main(String[] args) {

        MessageQueue queue = new MessageQueue(2);

        for (int i = 0; i < 3; i++) {
            int id = i;
            new Thread(()->{
                queue.put(new Message(id,"值"+id));
            },"生产者"+i).start();
        }

        new Thread(()->{
            while(true){
                try {
                    Thread.sleep(1000);
                    Message message = queue.take();
                } catch (InterruptedException e) {
                    System.out.println("sleep error");
                }
            }
        },"消费者").start();
    }
}

@Slf4j(topic = "c.MessageQueue")
class MessageQueue{

    private LinkedList<Message> list = new LinkedList<>();
    private int capcity;

    public MessageQueue(int capcity) {
        this.capcity = capcity;
    }

    public Message take(){
        synchronized (list){
            while(list.isEmpty()){
                try {
                    log.debug("队列为空，消费者线程等待");
                    list.wait();
                } catch (InterruptedException e) {
                    System.out.println("wait error");
                }
            }
            Message message = list.removeFirst();
            log.debug("已消费消息{}",message);
            list.notifyAll();
            return message;
        }
    }

    public void put(Message message){
        synchronized (list){
            while(list.size() == capcity){
                try {
                    log.debug("队列已满，生产者线程等待");
                    list.wait();
                } catch (InterruptedException e) {
                    System.out.println("wait error");
                }
            }
            list.addLast(message);
            log.debug("已生产消息{}",message);
            list.notifyAll();
        }
    }

}

class Message{
    private int id;
    private Object value;

    public Message(int id, Object value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}