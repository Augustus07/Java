package cn.itcast.netty.components.EventLoop.ordinaryEventAndScheduleEvent;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.netty.c3
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-18  22:41
 * @Description: netty 中的基本组件之一， EventLoop使用, 使用 EventLoop 执行 普通任务 和 定时任务
 * @Version: 1.0
 */

@Slf4j
public class TestEventLoop {
    public static void main(String[] args) {
        // 1. 创建事件循环组
        EventLoopGroup group = new NioEventLoopGroup(2); // 这个对象可以执行 IO事件， 普通任务， 定时任务
//        EventLoopGroup group = new DefaultEventLoop(); // 这个对象可以执行 普通任务， 定时任务
        // 2. 获取下一个事件循环对象
        System.out.println(group.next());
        System.out.println(group.next());
        System.out.println(group.next());
        System.out.println(group.next());
        // 3. 执行普通任务
        group.next().submit(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("ok");
        });
        group.next().execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("ok2");
        });
        // 4. 执行定时任务
//        group.next().scheduleAtFixedRate(() -> {
//            log.debug("ok");
//        }, 0, 1, TimeUnit.SECONDS);
        log.debug("main");
    }
}
