package connectionPool.handWritingThreadPool;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j(topic = "c.TestPool")
public class TestPool {
    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(1,
                1000,TimeUnit.MILLISECONDS,1, (queue, task) -> {
//            1) 死等
            queue.put(task);
//            2) 带超时等待
//            queue.offer(task,500,TimeUnit.MILLISECONDS);
//            3) 让调用者放弃任务执行
//            log.debug("放弃{}",task);
//            4) 让调用者抛出异常
//            throw new RuntimeException("任务执行失败" + task);
//            5) 让调用者自己执行任务
//            task.run();
        });
        for (int i = 0; i < 3; i++) {
            int j = i;
            threadPool.exexute(()->{
                try {
                    Thread.sleep(1000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("{}",j);
            });
        }
    }
}

@FunctionalInterface
interface RejectPolicy<T>{
    void reject(BlockingQueue<T> queue, T task);
}

@Slf4j(topic = "c.ThreadPool")
class ThreadPool{

//    任务线程数
    private BlockingQueue<Runnable> taskQueue;

//    线程集合
    private HashSet<Worker> workers = new HashSet<>();

//    核心线程数
    private int coreSize;

    private long timeout;

    private TimeUnit timeUnit;

    private RejectPolicy<Runnable> rejectPolicy;

    public void exexute(Runnable task){
//        当任务数没有超过 coresize时，直接交给worker对象执行
//               如果任务数超过coreSize时，加入任务队列暂存
        synchronized (workers){
            if(workers.size() < coreSize){
                Worker worker = new Worker(task);
                log.debug("新增 worker{},{}",worker,task);
                workers.add(worker);
                worker.start();
            }
            else {
//                log.debug("加入任务队列{}",task);
//                taskQueue.put(task);
//                1) 死等
//                2) 带超时等待
//                3) 放弃任务执行
//                4) 抛出异常
//                5) 让调用者自己执行任务
                taskQueue.tryPut(rejectPolicy, task);
            }
        }

    }

//    执行任务
    public ThreadPool(int coreSize, long timeout, TimeUnit timeUnit, int queueCapcity, RejectPolicy<Runnable> rejectPolicy) {
        this.coreSize = coreSize;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.taskQueue = new BlockingQueue<>(queueCapcity);
        this.rejectPolicy = rejectPolicy;
    }

    class Worker extends Thread{
        private Runnable task;

        public Worker(Runnable task){
            this.task = task;
        }

        @Override
        public void run() {
            while (task != null || (task = taskQueue.poll(timeout,timeUnit)) != null){
                try {
                    task.run();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    task = null;
                }
            }
            synchronized (workers){
                log.debug("worker 被移除了{}",this);
                workers.remove(this);
            }
        }
    }
}

@Slf4j(topic = "BlockingQueue")
class BlockingQueue<T>{

//    1.任务队列
    private Deque<T> queue = new ArrayDeque<>();

//    2.锁
    private ReentrantLock lock = new ReentrantLock();

//    3.生产者条件变量
    private Condition fullWaitSet = lock.newCondition();

//    4.消费者条件变量
    private Condition emptyWaitSet = lock.newCondition();

//    5.容量
    private int capacity;

    public BlockingQueue(int capacity) {
        this.capacity = capacity;
    }

//    带超时阻塞获取
    public T poll(long timeout, TimeUnit unit){
        lock.lock();
        try{
            long nanos = unit.toNanos(timeout);
            while(queue.isEmpty()){
                try {
                    if (nanos <= 0){
                        return null;
                    }
                    nanos = emptyWaitSet.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = queue.removeFirst();
            fullWaitSet.signal();
            return t;
        }finally {
            lock.unlock();
        }
    }

//    阻塞获取
    public T take(){
        try{
            while(queue.isEmpty()) {
                try {
                    emptyWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = queue.removeFirst();
            return t;
        }finally {
            lock.unlock();
        }
    }

//    带超时时间的阻塞添加
    public boolean offer(T task, long timeout, TimeUnit timeUnit){
        lock.lock();
        try{
            long nanos = timeUnit.toNanos(timeout);
            while(queue.size() == capacity){
                try {
                    log.debug("等待加入任务队列 {} ...",task);
                    if (nanos <= 0){
                        return false;
                    }
                    nanos = fullWaitSet.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug("加入任务队列 {} ...",task);
            queue.addLast(task);
            emptyWaitSet.signal();
            return true;
        }finally {
            lock.unlock();
        }
    }

//    阻塞添加
    public void put(T element){
        lock.lock();
        try {
            while(queue.size() == capacity) {
                try {
                    log.debug("等待加入任务队列...",element);
                    fullWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug("加入任务队列{}",element);
            queue.addLast(element);
            emptyWaitSet.signal();
        }finally {
            lock.unlock();
        }
    }

//    获取大小
    public int size(){
        lock.lock();
        try{
            return queue.size();
        }finally {
            lock.unlock();
        }
    }

    public void tryPut(RejectPolicy<T> rejectPolicy, T task) {
        lock.lock();
        try{
            if(queue.size() == capacity){
                rejectPolicy.reject(this,task);
            }else {
                log.debug("加入任务队列 {}", task);
                queue.addLast(task);
                emptyWaitSet.signal();
            }
        }finally {
            lock.unlock();
        }
    }
}
