package szu.vander.communicate.api.waitandnotify;


import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class WaitDeadLockDemo {
    private static BlockingDeque<WaitDemo.Bread> queue = new LinkedBlockingDeque<>();

    public static void main(String args[]) throws Exception {
        consumerThreadDeadLock();
        producerThreadDeadLock();
    }

    /**
     * 生产者线程死锁
     *
     * @throws Exception
     */
    public static void producerThreadDeadLock() throws Exception {
        System.out.println("生产者线程启动：");
        new Thread(() -> {
            synchronized (queue) {
                WaitDemo.Bread bread = new WaitDemo.Bread("bread", 1.0f);
                System.out.println("\t生产面包：" + bread);
                queue.add(bread);
                System.out.println("恢复消费者线程：");
                queue.notifyAll();
            }
        }).start();
    }

    /**
     * 消费者线程死锁
     *
     * @return
     * @throws Exception
     */
    public static Thread consumerThreadDeadLock() throws Exception {
        // 启动线程
        Thread consumerThread = new Thread(() -> {
            System.out.println("消费者线程启动！");
            // 当前线程拿到锁，然后挂起
            WaitDemo.Bread bread = null;
            while ((bread = queue.poll()) == null) {
                try {
                    Thread.sleep(1000);
                    synchronized (queue) {
                        queue.wait();
                        System.out.println("\t获取到了面包：" + bread);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        consumerThread.start();
        return consumerThread;
    }

}
