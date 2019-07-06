package szu.vander.demo.elect;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Vander
 * @Date: 2019/3/26
 * @Description: 线程池信息： 核心线程数量5，最大数量10，无界队列，超出核心线程数量的线程存活时间：5秒， 指定拒绝策略的
 */
public class UserDefinedThreadPool {

    private String threadPoolName;

    public UserDefinedThreadPool(String threadPoolName) {
        this.threadPoolName = threadPoolName;
    }

    /**
     * 使用无界队列
     *
     * @throws Exception
     */
    public ThreadPoolExecutor threadPoolExecutor() throws Exception {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), threadFactory());
        // 预计结果：线程池线程数量为：5,超出数量的任务，其他的进入队列中等待被执行
        return threadPoolExecutor;
    }

    public ThreadFactory threadFactory() {
        ThreadFactory factory = r -> {
           Thread thread = new Thread(r);
           String threadName = String.format("%s-ThreadGroup:%s-threadId:%s", threadPoolName, thread.getThreadGroup().getName(), thread.getId());
           thread.setName(threadName);
           thread.setDaemon(true);
           return thread;
        };
        return factory;
    }

}
