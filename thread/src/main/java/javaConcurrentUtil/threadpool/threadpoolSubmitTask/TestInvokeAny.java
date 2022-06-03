package javaConcurrentUtil.threadpool.threadpoolSubmitTask;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: javaConcurrentUtil.threadpool.threadpoolSubmitTask
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-05-29  21:57
 * @Description: pool.invokeAny()方法，会返回线程池中提交的tasks集合中最先返回的结果，在此期间调用线程池的线程阻塞
 * @Version: 1.0
 */
@Slf4j(topic = "c.TestInvokeAny")
public class TestInvokeAny {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        test05();
//        test06();
        test07();
    }

    private static void test07() throws InterruptedException, ExecutionException {
//        注意这里相比于06和07方法，将线程池的大小改为1，配合invokeAny方法就实现谁先执行，谁的结果就返回
        ExecutorService pool = Executors.newFixedThreadPool(1);

        String res = pool.invokeAny(Arrays.asList(
                () -> {
                    log.debug("begin 1");
                    Thread.sleep(1000);
                    log.debug("end 1");
                    return "1";
                },
                () -> {
                    log.debug("begin 2");
                    Thread.sleep(500);
                    log.debug("end 2");
                    return "2";
                },
                () -> {
                    log.debug("begin 3");
                    Thread.sleep(2000);
                    log.debug("end 3");
                    return "3";
                }
        ));
        System.out.println(res);
    }

    private static void test06() throws InterruptedException, ExecutionException {
        ExecutorService pool = Executors.newFixedThreadPool(3);

        String res = pool.invokeAny(Arrays.asList(
                () -> {
                        log.debug("begin 1");
                        Thread.sleep(1000);
                        log.debug("end 1");
                        return "1";
                },
                () -> {
                        log.debug("begin 2");
                        Thread.sleep(500);
                        log.debug("end 2");
                        return "2";
                },
                () -> {
                        log.debug("begin 3");
                        Thread.sleep(2000);
                        log.debug("end 3");
                        return "3";
                }
        ));
        System.out.println(res);
    }

    private static void test05() throws InterruptedException, ExecutionException {
        ExecutorService pool = Executors.newFixedThreadPool(3);

        String res = pool.invokeAny(Arrays.asList(
                new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        log.debug("begin 1");
                        Thread.sleep(1000);
                        log.debug("end 1");
                        return "1";
                    }
                },
                new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        log.debug("begin 2");
                        Thread.sleep(500);
                        log.debug("end 2");
                        return "2";
                    }
                },
                new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        log.debug("begin 3");
                        Thread.sleep(2000);
                        log.debug("end 3");
                        return "3";
                    }
                }
        ));

        System.out.println(res);
    }

}
