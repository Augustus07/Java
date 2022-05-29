package javaConcurrentUtil.cas.atomicInteger;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

@Slf4j(topic = "c.AtomicIntegerTest")
public class AtomicIntegerTest {
    public static void main(String[] args) {

        AtomicInteger i = new AtomicInteger(5);

//        ++i
//        System.out.println(i.incrementAndGet());
//        i++
//        System.out.println(i.getAndIncrement());

//        System.out.println(i.getAndAdd(5));
//        System.out.println(i.addAndGet(5));

//        i.updateAndGet( value -> value * 10);
//        i.getAndUpdate( value -> value * 10);

        updateAndGet(i, value -> value * 10);

        log.debug(String.valueOf(i));

    }

//    自己实现一个updateAndeGet的代码
    public static int updateAndGet(AtomicInteger i, IntUnaryOperator operator) {
        while (true) {
            int prev = i.get();
            int next = operator.applyAsInt(prev);
            if (i.compareAndSet(prev,next)) {
                return next;
            }
        }
    }

}
