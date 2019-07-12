package szu.vander.threadpool.threadpoolexecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: Vander
 * @Date: 2019/3/26
 * @Description:
 */
public class Main {

    public static void main(String args[]) throws Exception {
        ThreadPoolExecutor threadPoolExecutor = ThreadPoolDemo1.threadPoolExecutor();
        testCommon(threadPoolExecutor);
    }

    /**
     * 测试： 提交15个执行时间需要3秒的任务,看线程池的状况
     *
     * @param threadPoolExecutor 传入不同的线程池，看不同的结果
     * @throws Exception
     */
    public static void testCommon(ThreadPoolExecutor threadPoolExecutor) throws Exception {
        // 测试： 提交15个执行时间需要3秒的任务，看超过大小的2个，对应的处理情况
        for (int i = 0; i < 15; i++) {
            int n = i;
            threadPoolExecutor.submit(() -> {
                try {
                    System.out.println("开始执行：" + n);
                    Thread.sleep(3000L);
                    System.err.println("执行结束:" + n);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            System.out.println("任务提交成功 :" + i);
        }
        // 查看线程数量，查看队列等待数量
        Thread.sleep(500L);
        System.out.println("当前线程池线程数量为：" + threadPoolExecutor.getPoolSize());
        System.out.println("当前线程池等待的数量为：" + threadPoolExecutor.getQueue().size());
        // 等待15秒，查看线程数量和队列数量（理论上，超出核心线程数量的线程会被自动销毁）
        Thread.sleep(15000L);
        System.out.println("当前线程池线程数量为：" + threadPoolExecutor.getPoolSize());
        System.out.println("当前线程池等待的数量为：" + threadPoolExecutor.getQueue().size());
    }

}
