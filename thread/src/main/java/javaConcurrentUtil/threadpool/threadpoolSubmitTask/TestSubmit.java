package javaConcurrentUtil.threadpool.threadpoolSubmitTask;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: javaConcurrentUtil.threadpool.fixedThreadPool
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-05-29  21:25
 * @Description: pool.submit()可以提交单个任务, 并使用callable<T>和future等待线程池执行完任务后获得返回结果
 * @Version: 1.0
 */
@Slf4j(topic = "c.TestSubmit")
public class TestSubmit {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        test01可以用 lambda表达式简化
//        test01();
//        test02();
    }

    private static void test02() throws InterruptedException, ExecutionException {

        ExecutorService pool = Executors.newFixedThreadPool(2);

        Future<String> future = pool.submit(() -> {
            log.debug("running");
            Thread.sleep(1000);
            return "ok";
        });

        log.debug("{}", future.get());
    }

    private static void test01() throws InterruptedException, ExecutionException {
        ExecutorService pool = Executors.newFixedThreadPool(2);

//        Future<T> 配合 Callable<T> 将任务提交给线程池执行并获取执行结果， 其中T是返回的结果类型
        Future<String> future = pool.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                log.debug("running");
                Thread.sleep(1000);
                return "ok";
            }
        });

//        使用future.get()方法打印输出结果，这是一个阻塞方法，意味着Future<T>没有返回结果就卡在这，等待线程池结束
        log.debug("{}", future.get());

    }


}
