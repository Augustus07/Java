package javaConcurrentUtil.cas.atomicReference;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


/*
*  当多线程使用基本类型时，可以用原子整数等。但是要保证引用类型的原子性，怎么办？比如BigDecimal
*  答案可以使用原子引用将需要保证原子性的引用类型包裹起来，如AtomicReference<BigDecimal>
*
* */
public class AtomiceReferenceTest {
    public static void main(String[] args) {
        DecimalAccount.demo(new DecimalAccountCas(new BigDecimal("10000")));
    }
}

class DecimalAccountCas implements DecimalAccount {

    private AtomicReference<BigDecimal> balance;

    public DecimalAccountCas(BigDecimal amount) {
        this.balance = new AtomicReference<>(amount);
    }

    @Override
    public BigDecimal getBalance() {
        return balance.get();
    }

    @Override
    public void withdraw(BigDecimal amount) {
        while (true) {
            BigDecimal prev = balance.get();
            BigDecimal next = prev.subtract(amount);
            if (balance.compareAndSet(prev, next)) {
                break;
            }
        }
    }

}

interface DecimalAccount{

    //    获取余额
    BigDecimal getBalance();
    //    取款
    void withdraw(BigDecimal amount);


    /*
     *方法启动1000个线程，每个线程做-10操作
     * 若初始余额10000，结果应该是0
     * */
    static void demo(DecimalAccount account){
        List<Thread> ts = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            ts.add(new Thread(()->{
                account.withdraw(BigDecimal.TEN);
            }));
        }
        long start = System.nanoTime();
        ts.forEach(Thread::start);
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long end = System.nanoTime();
        System.out.println(account.getBalance()
                + " cost: " + (end - start)/1000_000 + " ms");
    }

}


