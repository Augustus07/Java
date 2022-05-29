package javaConcurrentUtil.cas.aba_problem;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;


/*
*  cas 中经典的ABA问题
* */
@Slf4j(topic = "c.ABAProblem")
public class ABAProblem {
    static AtomicReference<String> ref = new AtomicReference<>("A");
    public static void main(String[] args) {
//        主线程获取A
//        主线程怎么知道这个共享变量被其他线程修改过?
        String prev = ref.get();
        other();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.debug("change A->C {}", ref.compareAndSet(prev, "c"));
    }

    private static void other() {
        new Thread(() -> {
            log.debug("change A->B {}", ref.compareAndSet(ref.get(), "B"));
        }).start();

        new Thread(() -> {
            log.debug("change B->A {}", ref.compareAndSet(ref.get(), "A"));
        }).start();
    }
}
