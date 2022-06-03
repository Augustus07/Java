package javaConcurrentUtil.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: javaConcurrentUtil.threadpool.aqs
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-05-30  16:10
 * @Description:
 * @Version: 1.0
 */

@Slf4j(topic = "c.TestNonReentrantLock")
public class TestNonReentrantLock {
    public static void main(String[] args) {
        MyLock lock = new MyLock();
        new Thread(() -> {
            lock.lock();
            log.debug("locking...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                log.debug("unlocking...");
                lock.unlock();
            }
        }, "t1").start();

        new Thread(() -> {
            lock.lock();
            try {
                log.debug("locking...");
            } finally {
                log.debug("unlocing...");
                lock.unlock();
            }
        }, "t2").start();

    }


}

//自定义锁， 不可重入锁
class MyLock implements Lock {

    protected MySync sync = new MySync();

    @Override  // 加锁 (不成功会进入等待队列)
    public void lock() {
        sync.acquire(1);
    }

    @Override // 加锁， 可打断
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override // 尝试加锁一次
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override // 解锁
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }

    // 独占锁 同步器类
    class MySync extends AbstractQueuedSynchronizer {
        @Override
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0, 1)) {
                // 加上了锁，并设置 Owner 为当前线程
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        protected Condition newCondition() {
            return new ConditionObject();
        }

    }

}
