package javaConcurrentUtil.cas.aba_problem.solve;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicStampedReference;

/*
* 使用带时间戳的原子引用解决ABA问题
* */
@Slf4j(topic = "c.test3")
public class AtomicStampedReferenceTest {

    static AtomicStampedReference<String> ref = new AtomicStampedReference<>("A",0);

    public static void main(String[] args) {
        log.debug("main start");
        String prev = ref.getReference();
        int stamp = ref.getStamp();
        log.debug("{}",stamp);
        other();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int newstamp = ref.getStamp();
        log.debug("{}",newstamp);
        log.debug("change A->C {}",ref.compareAndSet(prev,"C",stamp,stamp+1));
    }

    private static void other(){
        new Thread(()->{
            int stamp = ref.getStamp();
            log.debug("{}",stamp);
            log.debug("change A->B}",ref.compareAndSet(ref.getReference(),"B",stamp,stamp+1));
        },"t1").start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(()->{
            int stamp = ref.getStamp();
            log.debug("{}",stamp);
            log.debug("change B->A}",ref.compareAndSet(ref.getReference(),"A",stamp,stamp+1));
        },"t2").start();
    }
}
