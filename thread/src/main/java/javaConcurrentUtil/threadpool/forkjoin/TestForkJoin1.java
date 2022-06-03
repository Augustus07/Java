package javaConcurrentUtil.threadpool.forkjoin;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: javaConcurrentUtil.threadpool.forkjoin
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-05-30  16:02
 * @Description:
 * @Version: 1.0
 */
public class TestForkJoin1 {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool(4);
        System.out.println(pool.invoke(new MyTaskImprove(1, 10)));
    }

}

@Slf4j(topic = "c.RecursiveTask")
class MyTaskImprove extends RecursiveTask<Integer> {
    int begin;
    int end;

    public MyTaskImprove(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    public String toString() {
        return "{" + begin + "," + end + '}';
    }

    @Override
    protected Integer compute() {
// 5, 5
        if (begin == end) {
            log.debug("join() {}", begin);
            return begin;
        }
// 4, 5
        if (end - begin == 1) {
            log.debug("join() {} + {} = {}", begin, end, end + begin);
            return end + begin;
        }
// 1 5
        int mid = (end + begin) / 2; // 3
        MyTaskImprove t1 = new MyTaskImprove(begin, mid); // 1,3
        t1.fork();
        MyTaskImprove t2 = new MyTaskImprove(mid + 1, end); // 4,5
        t2.fork();
        log.debug("fork() {} + {} = ?", t1, t2);
        int result = t1.join() + t2.join();
        log.debug("join() {} + {} = {}", t1, t2, result);
        return result;
    }

}
