package unsafe;

import lombok.Data;
import sun.misc.Unsafe;

import java.lang.reflect.Field;


/*
*   本例使用unsafe的cas实现了juc中 atomicIntegerFiledUpdater
*
* */
public class Implement_atomicIntegerFiledUpdater {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe unsafe = (Unsafe) theUnsafe.get(null);

        System.out.println(unsafe);

//        1. 获取偏移地址
        long idOffset = unsafe.objectFieldOffset(Teacher.class.getDeclaredField("id"));
        long nameOffset = unsafe.objectFieldOffset(Teacher.class.getDeclaredField("name"));

        Teacher t = new Teacher();

        System.out.println(t);

//        2. 执行cas操作
        unsafe.compareAndSwapInt(t, idOffset, 0, 1);

        unsafe.compareAndSwapObject(t, nameOffset, null, "张三");

        System.out.println(t);

    }
}

@Data
class Teacher {
    volatile int id;
    volatile String name;

}