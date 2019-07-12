package szu.vander.communicate.api.suspendandresume;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class SuspendDeadLockDemo2 {
    private static BlockingDeque<SuspendDemo.Bread> queue = new LinkedBlockingDeque<SuspendDemo.Bread>();

    public static void main(String args[]) throws Exception {
        Thread consumeTread = consumerThreadDeadLock2();
        producerThreadDeadLock2(consumeTread);
    }

    /**
     * 生产者线程死锁
     *
     * @param consumerThread
     * @throws Exception
     */
    public static void producerThreadDeadLock2(Thread consumerThread) throws Exception {
        System.out.println("生产者线程启动：");
        new Thread(() -> {
            synchronized (queue) {
                SuspendDemo.Bread bread = new SuspendDemo.Bread("bread", 1.0f);
                System.out.println("\t生产面包：" + bread);
                queue.add(bread);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("恢复消费者线程：");
                consumerThread.resume();
            }
        }).start();
    }

    /**
     * 消费者线程死锁
     *
     * @return
     * @throws Exception
     */
    public static Thread consumerThreadDeadLock2() throws Exception {
        // 启动线程
        Thread consumerThread = new Thread(() -> {
            System.out.println("消费者线程启动！");
            // 当前线程拿到锁，然后挂起
            SuspendDemo.Bread bread = null;
            while ((bread = queue.poll()) == null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (queue) {
                    Thread.currentThread().suspend();
                    System.out.println("\t获取到了面包：" + bread);
                }
            }
        });
        consumerThread.start();
        return consumerThread;
    }

}
