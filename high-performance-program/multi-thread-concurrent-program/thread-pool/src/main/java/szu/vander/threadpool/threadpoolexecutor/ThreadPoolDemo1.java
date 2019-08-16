package szu.vander.threadpool.threadpoolexecutor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Vander
 * @Date: 2019/3/26
 * @Description: 线程池信息： 核心线程数量5，最大数量10，无界队列，超出核心线程数量的线程存活时间：5秒， 指定拒绝策略的
 */
public class ThreadPoolDemo1 {

    /**
     * 使用无界队列
     *
     * @throws Exception
     */
    public static ThreadPoolExecutor threadPoolExecutor() throws Exception {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), threadFactory());
        // 预计结果：线程池线程数量为：5,超出数量的任务，其他的进入队列中等待被执行
        return threadPoolExecutor;
    }

    public static ThreadFactory threadFactory() {
        ThreadFactory factory = r -> {
           Thread thread = new Thread(r);
           String threadName = String.format("ThreadGroup:%s-threadId:%s", thread.getThreadGroup().getName(), thread.getId());
           thread.setName(threadName);
           return thread;
        };
        return factory;
    }

}
