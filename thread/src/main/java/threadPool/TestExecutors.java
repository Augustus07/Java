package threadPool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j(topic = "c.TestExecutors")
public class TestExecutors {
    public static void main(String[] args) {
        test2();
    }

    private static void test2() {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        pool.execute(()->{
            log.debug("1");
            int i = 1 / 0;
        });

        pool.execute(()->{
            log.debug("2");
        });

        pool.execute(()->{
            log.debug("3");
        });
    }
}
