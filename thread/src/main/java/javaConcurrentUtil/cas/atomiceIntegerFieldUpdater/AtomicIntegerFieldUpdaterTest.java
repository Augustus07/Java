package javaConcurrentUtil.cas.atomiceIntegerFieldUpdater;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/*
*  保证类中的某个域的多线程cas性, 域即类中的成员变量
*  AtomicIntegerFieldUpdater
*  AtomicLongFieldUpdater
*  AtomicReferenceFieldUpdater
*
* */
public class AtomicIntegerFieldUpdaterTest {
    public static void main(String[] args) {
        Student stu = new Student();

//        多线程使用类的Filed对象的cas性，其中类的filed必须是volatile类型
        AtomicReferenceFieldUpdater updater =
                AtomicReferenceFieldUpdater.newUpdater(Student.class, String.class, "name");

        System.out.println(updater.compareAndSet(stu, null, "张三"));
        System.out.println(stu);
    }
}

class Student {

    volatile String name;

    @Override
    public String toString() {
        return "student{" +
                "name='" + name + '\'' +
                '}';
    }
}
