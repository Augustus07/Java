package javaConcurrentUtil.threadpool.threadpoolSubmitTask;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: javaConcurrentUtil.threadpool.threadpoolSubmitTask
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-05-29  21:41
 * @Description: poll.invokeall(), 提交tasks中的所有任务，可以配合future和callable获取返回结果
 * @Version: 1.0
 */

@Slf4j(topic = "c.TestInvokeAll")
public class TestInvokeAll {
    public static void main(String[] args) throws InterruptedException {
//        test03();
        test04();
    }

    private static void test04() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);

//        用lambda表达式简化
        List<Future<String>> futures = pool.invokeAll(Arrays.asList(
                () -> {
                        log.debug("begin");
                        Thread.sleep(1000);
                        return "1";
                },
                () -> {
                        log.debug("begin");
                        Thread.sleep(500);
                        return "2";
                },
                () -> {
                        log.debug("begin");
                        Thread.sleep(2000);
                        return "3";
                }
        ));

//        futures等待线程池中提交的所有任务结束后执行
        futures.forEach( f -> {
            try {
                log.debug("{}", f.get());
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static void test03() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);

//        可以用lambda表达式简化, 注意到这里将多个callable<T>任务封装成一个list并使用invokeAll方法提交
        List<Future<String>> futures = pool.invokeAll(Arrays.asList(
                new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        log.debug("begin");
                        Thread.sleep(1000);
                        return "1";
                    }
                },
                new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        log.debug("begin");
                        Thread.sleep(500);
                        return "2";
                    }
                },
                new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        log.debug("begin");
                        Thread.sleep(2000);
                        return "3";
                    }
                }
        ));

//        futures等待线程池中提交的所有任务结束后执行
        futures.forEach( f -> {
            try {
                log.debug("{}", f.get());
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }


}
