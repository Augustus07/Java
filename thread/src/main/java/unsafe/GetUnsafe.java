package unsafe;

import lombok.Data;
import sun.misc.Unsafe;

import java.lang.reflect.Field;


/*
*   Unsafe是jdk底层 在线程方面实现 park、cas操作， 在内存读取方面实现了类似c 语言的手动分配和释放内存的方法;
*
* */
public class GetUnsafe {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe unsafe = (Unsafe) theUnsafe.get(null);

        System.out.println(unsafe);
    }
}


