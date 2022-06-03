package javaConcurrentUtil.reentrantreadwritelock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: javaConcurrentUtil.threadpool.aqs
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-01  22:05
 * @Description: Reentrantreadwritelock
 * 读读同步， 读写互斥， 写写互斥
 * 读锁不支持条件变量， 写锁支持条件变量
 * 有读锁的情况下获取写锁，会导致写锁永久等待
 * 有写锁的情况下获取写锁，会导致读锁永久等待
 * @Version: 1.0
 *
 */

@Slf4j(topic = "c.TestReentrantreadwritelock")
public class TestReentrantreadwritelock {
    public static void main(String[] args) throws InterruptedException {
        DataContainer dataContainer = new DataContainer();
        new Thread(() -> {
            dataContainer.read();
        }, "t1").start();

        Thread.sleep(100);

        new Thread(() -> {
            dataContainer.write();
        }, "t2").start();

    }

}

@Slf4j(topic = "c.DataContainer")
class DataContainer {
    private Object data;
    private ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock r = rw.readLock();
    private ReentrantReadWriteLock.WriteLock w = rw.writeLock();

    public Object read() {
        log.debug("获取读锁");
        r.lock();
        try {
            log.debug("读取");
            Thread.sleep(1000);
            return data;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            log.debug("释放读锁");
            r.unlock();
        }

    }

    public void write() {
        log.debug("获取写锁");
        w.lock();
        try {
            log.debug("写入");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            log.debug("释放写锁");
            w.unlock();
        }
    }

}

