package cn.itcast.netty.components.futureAndPromise;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;


/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.netty.c3.futureAndPromise
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-20  19:35
 * @Description:
 * @Version: 1.0
 */
@Slf4j
public class TestNettyFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        EventLoop eventLoop = group.next();
        Future<Integer> future = eventLoop.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.debug("执行计算");
                Thread.sleep(1000);
                return 70;
            }
        });
//        主线程同步等待future结果
//        log.debug("等待结果");
//        log.debug("结果是 {}", future.get());
//        log.debug("主线程干其他事去啦");

//        主线程使用回调对象中的回调函数异步等待future结果
        future.addListener(new GenericFutureListener<Future<? super Integer>>() {
            @Override
            public void operationComplete(Future<? super Integer> future) throws Exception {
                log.debug("接受结果：{}", future.getNow());
            }
        });
        log.debug("主线程干其他事去啦");
    }

}
