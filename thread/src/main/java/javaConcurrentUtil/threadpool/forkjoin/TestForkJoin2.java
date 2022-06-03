package javaConcurrentUtil.threadpool.forkjoin;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: javaConcurrentUtil.threadpool.forkjoin
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-05-30  15:50
 * @Description:
 * @Version: 1.0
 */
public class TestForkJoin2 {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool(4);
        System.out.println(pool.invoke(new MyTask(5)));
    }

}

@Slf4j(topic = "c.MyTask")
class MyTask extends RecursiveTask<Integer> {

    private int n;

    public MyTask(int n) {
        this.n = n;
    }

    @Override
    public String toString() {
        return "{" + n + '}';
    }

    @Override
    protected Integer compute() {
        if (n == 1) {
            log.debug("join() {}", n);
            return n;
        }

        MyTask t1 = new MyTask(n - 1);
//        让一个线程执行此任务
        t1.fork();
        log.debug("fork() {} + {}", n, t1);

//        获取任务结果并合并
        int result = n + t1.join();
        log.debug("join() {} + {} = {}", n, t1, result);
        return result;
    }
}
