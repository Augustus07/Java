package javaConcurrentUtil.stampedLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: javaConcurrentUtil.threadpool.aqs
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-01  22:05
 * @Description: Reentrantreadwritelock
 * 相比于 Reentrantreadwritelock 在加读锁前加了时间戳，若果时间戳有效，直接读，如果时间戳失效了，再加读锁
 * @Version: 1.0
 *
 */

@Slf4j(topic = "c.TestStampedlock")
public class TestStampedlock {
    public static void main(String[] args) throws InterruptedException {
//        testReadRead();
        teatReadWriteRead();

    }

    private static void teatReadWriteRead() throws InterruptedException {
        DataContainer dataContainer = new DataContainer(1);
        new Thread(() -> {
            dataContainer.read(1);
        }, "t1").start();

        Thread.sleep(500);

        new Thread(() -> {
            dataContainer.write(1000);
        }, "t2").start();

        Thread.sleep(500);

        new Thread(() -> {
            dataContainer.read(1);
        }, "t3").start();
    }

    private static void testReadRead() throws InterruptedException {
        DataContainer dataContainer = new DataContainer(1);
        new Thread(() -> {
            dataContainer.read(1);
        }, "t1").start();

        Thread.sleep(500);

        new Thread(() -> {
            dataContainer.read(0);
        }, "t2").start();
    }

}

@Slf4j(topic = "c.DataContainer")
class DataContainer {
    private int data;
    private final StampedLock lock = new StampedLock();

    public DataContainer(int data) {
        this.data = data;
    }

    public int read(int readTime) {
        long stamp = lock.tryOptimisticRead();
        log.debug("optimistic read locking...{}", stamp);
        try {
            Thread.sleep(readTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (lock.validate(stamp)) {
            log.debug("read finish...{}", stamp);
            return data;
        }
        log.debug("updating to read lock... {}", stamp);
        try {
            stamp = lock.readLock();
            log.debug("read lock {}", stamp);
            Thread.sleep(readTime);
            log.debug("read finish... {}", stamp);
            return data;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            log.debug("read unlock {}", stamp);
            lock.unlockRead(stamp);
        }

    }

    public void write(int newData) {
        long stamp = lock.writeLock();
        log.debug("write lock {}", stamp);
        try {
            Thread.sleep(2000);
            this.data = newData;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            log.debug("write unlock {}", stamp);
            lock.unlockWrite(stamp);
        }
    }

}

