package Volatile.addVolatile;

import lombok.extern.slf4j.Slf4j;

/*
* volatile可以保证变量值可见性， 通过加上读写屏障保证代码不会指令重拍，保证有序性
*
* 它可以用来修饰成员变量和静态成员变量，他可以避免线程从自己的工作缓存中查找变量的值，必须到主存中获取它的值，
* 线程操作 volatile 变量都是直接操作主存, 使用于一写多读的情况
* */
@Slf4j(topic = "c.test1")
public class test1 {

    volatile private static boolean flag = false;

    public static void main(String[] args) {

        new Thread(()->{
            while (true){
                if(flag) break;
            }
            log.debug("执行完毕");
        },"t1").start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = true;
        log.debug("执行完毕");
    }
}
