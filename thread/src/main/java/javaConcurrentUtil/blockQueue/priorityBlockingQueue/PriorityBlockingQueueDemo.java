package javaConcurrentUtil.blockQueue.priorityBlockingQueue;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: javaConcurrentUtil.blockQueue.priorityBlockingQueue
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-03  22:00
 * @Description: 不管以什么方式插入，数局组织方式按照堆存放，并且take返回值为定义的Comparator
 * @Version: 1.0
 */
public class PriorityBlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        PriorityBlockingQueue<Person> pbq = new PriorityBlockingQueue<>();

        pbq.put(new Person(3,"person3"));
        System.err.println("容器为：" + pbq);
        pbq.put(new Person(2,"person2"));
        System.err.println("容器为：" + pbq);
        pbq.put(new Person(1,"person1"));
        System.err.println("容器为：" + pbq);
        pbq.put(new Person(4,"person4"));
        System.err.println("容器为：" + pbq);
        System.err.println("分割线----------------------------------------------------------------" );


        System.err.println("获取元素 " + pbq.take().getId());
        System.err.println("容器为：" + pbq);
        System.err.println("分割线----------------------------------------------------------------" );

        System.err.println("获取元素 " + pbq.take().getId());
        System.err.println("容器为：" + pbq);
        System.err.println("分割线----------------------------------------------------------------" );

        System.err.println("获取元素 " + pbq.take().getId());
        System.err.println("容器为：" + pbq);
        System.err.println("分割线----------------------------------------------------------------" );

        System.err.println("获取元素 " + pbq.take().getId());
        System.err.println("容器为：" + pbq);
        System.err.println("分割线----------------------------------------------------------------" );
    }
}

class Person implements Comparable<Person>{
    private int id;
    private String name;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Person(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }
    public Person() {
    }
    @Override
    public String toString() {
        return this.id + ":" + this.name;
    }
    @Override
    public int compareTo(Person person) {
        return this.id > person.getId() ? 1 : ( this.id < person.getId() ? -1 :0);
    }
}
