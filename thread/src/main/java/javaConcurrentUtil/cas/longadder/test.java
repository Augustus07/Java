package javaConcurrentUtil.cas.longadder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.function.Supplier;

/*
*  多线程对long进行cas累加操作，分别使用 AtomicLong 和 LongAdder
*  可以看到LongAdder效率更高， 为啥， 应为用到了cell数组
*
* */
public class test {

    public static void main(String[] args) {

        for (int i = 0; i < 5; i++) {
            demo(
                    () -> new AtomicLong(0),
                    (adder) -> adder.getAndIncrement()
            );
        }

        System.out.println();

        for (int i = 0; i < 5; i++) {
            demo(
                    () -> new LongAdder(),
                    adder -> adder.increment()
            );
        }

    }

    /*
    *  () -> 结果  提供adder对象
    *  (参数) ->  执行累加操作
    * */
    private static <T> void demo(Supplier<T> adderSupplier, Consumer<T> action) {
        T adder = adderSupplier.get();
        List<Thread> ts = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            ts.add(new Thread(() -> {
                for (int j = 0; j < 500000; j++) {
                    action.accept(adder);
                }
            }));
        }

        long start = System.nanoTime();
        ts.forEach(t -> t.start());
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        long end = System.nanoTime();
        System.out.println(adder + " cost: " + (end - start) / 1000_000);

    }

}
