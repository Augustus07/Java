package cn.itcast.nio.c4.selector.a_TestSelectorServerAccept;

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
 * @BelongsPackage: cn.itcast.netty.c4
 * @Author: chenb
 * @Email: lcy0869@gmail.com
 * @CreateTime: 2022-06-05  20:30
 * @Description:
 * 当使用阻塞模式的时候，必须要创建一个线程用于不断接收客户端的连接，每当接受一个客户端的连接时，就要开一个新线程去处理和这个 socket 相关的数据传输任务
 * 当使用非阻塞模式的时候，很大几率会占满一个核心的 cpu
 * 当使用 selector 配合 非阻塞模式 时，将 各种channel 注册到 selector 中，并且 一个channel 对应 一个 selelectionKey　
 * selector 在单线程里不断监听 注册在其上的 channel，
 * 没有事件发生 或者 有事件发生并且处理掉该事件，就阻塞在 select() 方法； 有事件发生 或者 有事件发生但是上一轮没有处理， 就不会阻塞在 select() 方法
 * 当某些 channel 发生这个 channel 感兴趣的事件时，停止阻塞并将 发生事件的 key 加入到 selectedKey 这个 set 中 （注意 当有事件发生，selector 只负责将 key 加入 selectedKey 中，不负责删除，所以后面使用 iterator遍历 selectedKey，获得就删除）
 * 然后通过 selectedKeys 方法获取发生 事件的 channels 所对应的 selectionKey set
 * 再遍历 selectionKey 中的每一个 selectionKey，通过 selectionKey 获取 channel，然后再获取数据
 * @Version: 1.0
 */
@Slf4j
public class TestSelectorServerAccept {
    public static void main(String[] args) throws IOException {
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
                ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                SocketChannel sc = channel.accept();
                log.debug("{}", sc);
            }
        }

   }

}
