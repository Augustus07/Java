package cn.itcast.netty.components.futureAndPromise;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.netty.c3.futureAndPromise
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-20  19:22
 * @Description:
 * @Version: 1.0
 */
@Slf4j
public class TestJdkFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(2);
        Future<Integer> future = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.debug("执行计算");
                Thread.sleep(1000);
                return 50;
            }
        });
        // 3. 主线程通过 future 来获取结果
        log.debug("等待结果");
        log.debug("结果是 {}", future.get());
        log.debug("主线程干其他的事情去啦");
    }

}
