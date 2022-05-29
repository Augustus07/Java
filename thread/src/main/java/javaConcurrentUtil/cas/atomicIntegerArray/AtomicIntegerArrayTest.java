package javaConcurrentUtil.cas.atomicIntegerArray;

import lombok.extern.slf4j.Slf4j;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;


/*
*  原子引用是可以保证多线程下 对于引用所指对象的cas操作，但是若想多线程修改数组呢？
*  在c++语言里，我们拿到数组的引用或者指针只能修改指针指向数组的第一个元素，然后移动指针修改下一个元素，但是java引用获取到的只能是数组0索引的地址，无法修改数组中的所有元素
*  因此，我们借助AtomicIntegerArray保证多线程下的cas数组修改操作
*
*  类似的，还有AtomicLongArray、AtomicReferenceArray
* */
@Slf4j(topic = "AtomicIntegerArrayTest")
public class AtomicIntegerArrayTest {

    public static void main(String[] args) {
        demo(
                () -> new int[10],
                (array) -> array.length,
                (array, index) -> array[index]++,
                array -> System.out.println(Arrays.toString(array))
        );

        demo(
                () -> new AtomicIntegerArray(10),
                (array) -> array.length(),
                (array, index) -> array.getAndIncrement(index),
                array -> System.out.println(array)
        );
    }

    /*
    *  参数1， 提供数组，可以是线程安全或者不安全数组
    *  参数2， 获取数组长度的方法
    *  参数3， 自增方法，回传 array, index
    *  参数4， 打印数组的方法
    *
    *  supplier 生产者/提供者 无中生有 ()->结果
    *  function 函数 一个参数一个结果 (参数)->结果 ， BiFunction (参数1，参数2)->结果
    *  cousumer 消费者 一个参数没结果 (参数)->void ， BiCousumer (参数1，参数2)->void
    * */
    private static <T> void demo(
            Supplier<T> arraySupplier,
            Function<T, Integer> lengthFun,
            BiConsumer<T, Integer> putCousumer,
            Consumer<T> printCousunmer) {

        List<Thread> ts = new ArrayList<>();
        T array = arraySupplier.get();
        int length = lengthFun.apply(array);
        for (int i = 0; i < length; i++) {
            ts.add(new Thread(()->{
                for (int j = 0; j < 10000; j++) {
                    putCousumer.accept(array, j%length);
                }
            }));
        }

        ts.forEach(t -> t.start());
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        printCousunmer.accept(array);
    }
}
