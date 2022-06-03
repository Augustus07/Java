package cn.itcast.netty.c4.selector;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @BelongsProject: java_practice
 * @BelongsPackage: cn.itcast.netty.c4.selector
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-05  22:07
 * @Description:
 * @Version: 1.0
 */
@Slf4j
public class TestSelectorCancel {
    public static void main(String[] args) throws IOException {
//        test01();

        /*
        * 针对 test01() 发生的问题，使用 key.cancel()方法 停止无限循环
        * */
//        1. 创建 selector， 管理多个 channel
        Selector selector = Selector.open();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

//        2. 建立 selector 和 channel 的联系， 即将各种类型的 channel 注册到 selector 上去
//        SelectionKey 作用：
//        当前时间点在 selector 上注册的所有 channel 没事件发生时，就一直阻塞在 selector.select()处
//        当未来某个时间点某个时间点发生事件之后，selector.select() 不在阻塞，通过 selector.selectedKeys() 方法返回发生事件的 socketchannel 对应的 selectionKey set
//        通过这个 selectionKey set，我们一个一个遍历出单个 selectionKey ，通过 selectionKey 获取到 channel, 然后处理事件
        SelectionKey sscKey = ssc.register(selector, 0, null);
//        设置这个 selectionKey 感兴趣的事件
        /*
         * 事件类型有：
         * accpt - 服务器端，有 client 连接 serversocketChannel 时
         * connect - 客户端， 与 server 连接上时触发
         * read - 可读事件
         * write - 可写事件
         * */
        sscKey.interestOps(SelectionKey.OP_ACCEPT);
        log.debug("register key: {}", sscKey);
        ssc.bind(new InetSocketAddress(8080));

        while (true) {
//            3. select 方法，没有事件发生，线程阻塞，有事件发生，就继续运行
            selector.select();
//            4. 当有事件发生时，select() 方法不在阻塞，通过 selectedKeys 获取发生事件的 selctionKey set
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                log.debug("key: {}", key);
//                ServerSocketChannel channel = (ServerSocketChannel) key.channel();
//                SocketChannel sc = channel.accept();
//                log.debug("{}", sc);

//                将这个 selectionKey 对应的 channel 事件取消
                key.cancel();
            }
        }
    }

//    可以看到，当有事件发生时，会在 select() 处停止阻塞，如果不对该事件进行处理，seletor 就不会阻塞在 select() 处，不处理就无限循环
    private static void test01() throws IOException {
        //        1. 创建 selector， 管理多个 channel
        Selector selector = Selector.open();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

//        2. 建立 selector 和 channel 的联系， 即将各种类型的 channel 注册到 selector 上去
//        SelectionKey 作用：
//        当前时间点在 selector 上注册的所有 channel 没事件发生时，就一直阻塞在 selector.select()处
//        当未来某个时间点某个时间点发生事件之后，selector.select() 不在阻塞，通过 selector.selectedKeys() 方法返回发生事件的 socketchannel 对应的 selectionKey set
//        通过这个 selectionKey set，我们一个一个遍历出单个 selectionKey ，通过 selectionKey 获取到 channel, 然后处理事件
        SelectionKey sscKey = ssc.register(selector, 0, null);
//        设置这个 selectionKey 感兴趣的事件
        /*
         * 事件类型有：
         * accpt - 服务器端，有 client 连接 serversocketChannel 时
         * connect - 客户端， 与 server 连接上时触发
         * read - 可读事件
         * write - 可写事件
         * */
        sscKey.interestOps(SelectionKey.OP_ACCEPT);
        log.debug("register key: {}", sscKey);
        ssc.bind(new InetSocketAddress(8080));

        while (true) {
//            3. select 方法，没有事件发生，线程阻塞，有事件发生，就继续运行
            selector.select();
//            4. 当有事件发生时，select() 方法不在阻塞，通过 selectedKeys 获取发生事件的 selctionKey set
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                log.debug("key: {}", key);
//                ServerSocketChannel channel = (ServerSocketChannel) key.channel();
//                SocketChannel sc = channel.accept();
//                log.debug("{}", sc);
            }
        }
    }

}
