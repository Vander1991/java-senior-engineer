package szu.vander.communicate.api.suspendandresume;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class SuspendDeadLockDemo1 {
    private static BlockingDeque<SuspendDemo.Bread> queue = new LinkedBlockingDeque<SuspendDemo.Bread>();

    public static void main(String args[]) throws Exception {
        Thread consumeTread = consumerThreadDeadLock();
        producerThreadDeadLock(consumeTread);
    }

    /**
     * 死锁的suspend/resume。 suspend并不会像wait一样释放锁，故此容易写出死锁代码
     */
    public static Thread consumerThreadDeadLock() throws Exception {
        // 启动线程
        Thread consumerThread = new Thread(() -> {
            System.out.println("消费者线程被挂起！");
            // 当前线程拿到锁，然后挂起
            synchronized (queue) {
                Thread.currentThread().suspend();
            }
            SuspendDemo.Bread bread = null;
            if ((bread = queue.poll()) != null) {
                System.out.println("\t获取到了面包：" + bread);
            } else {
                System.out.println("\t依旧获取不到面包");
            }
        });
        consumerThread.start();
        return consumerThread;
    }

    /**
     * 生产者线程死锁
     *
     * @param consumerThread
     * @throws Exception
     */
    public static void producerThreadDeadLock(Thread consumerThread) throws Exception {
        System.out.println("启动生产者线程：");
        new Thread(() -> {
            SuspendDemo.Bread bread = new SuspendDemo.Bread("bread", 1.0f);
            System.out.println("\t生产面包：" + bread);
            queue.add(bread);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("恢复消费者线程：");
            synchronized (queue) {
                consumerThread.resume();
            }
        }).start();
    }

}
