package szu.vander.threadpool.scheduledthreadpoolexector;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Vander
 * @Date: 2019/3/26
 * @Description: 定时执行线程池信息：3秒后执行，一次性任务，到点就执行
 */
public class ScheduledThreadPoolDemo {
    /**
     * 核心线程数量5，最大数量Integer.MAX_VALUE，DelayedWorkQueue延时队列，超出核心线程数量的线程存活时间：0秒
     *
     * @throws Exception
     */
    public static ThreadPoolExecutor threadPoolExecutor() throws Exception {
        // 和Executors.newScheduledThreadPool()一样的
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ScheduledThreadPoolExecutor threadPoolExecutor = new ScheduledThreadPoolExecutor(5);
        threadPoolExecutor.schedule(() -> {
                    System.out.println("任务被执行，现在时间：" + df.format(new Date()));
                }, 3000, TimeUnit.MILLISECONDS
        );
        System.out.println(
                "定时任务，提交成功，时间是：" + df.format(new Date()) + ", 当前线程池中线程数量：" + threadPoolExecutor.getPoolSize());
        // 预计结果：任务在3秒后被执行一次
        return threadPoolExecutor;
    }
}
