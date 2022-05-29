package reentrantLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/*
 * 线程互斥： 保证变量的原子性(读写操作之间由同一个线程完成，比如i++操作，线程1获取到在寄存器中加一后写入变量内存，在这个读写过程中，不会有其他线程发生读写操作)
 * 线程同步，保证线程之间的顺序执行
 *
 * */

//ReentrantLock和 synchronized都支持可重入, ReentrantLock相比Synchronized要好，具体表现在可打断、可超时、可以有多个waitset
//ReentrantLock是java级别的monitor实现, synchronized是c++级别的monitor实现
/*
* 基本使用
*  lock.lock();
        try {

        }finally {
            lock.unlock();
        }
* */
@Slf4j(topic = "c.TestReentrantLock")
public class TestReentrantLock {

    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        lock.lock();
        try {
           log.debug("enter main");
           m1();
        }finally {
            lock.unlock();
        }
    }

    public static void m1(){
        lock.lock();
        try {
            log.debug("enter m1");
            m2();
        }finally {
            lock.unlock();
        }
    }

    public static void m2(){
        lock.lock();
        try{
           log.debug("enter m2");
        }finally {
            lock.unlock();
        }
    }
}
