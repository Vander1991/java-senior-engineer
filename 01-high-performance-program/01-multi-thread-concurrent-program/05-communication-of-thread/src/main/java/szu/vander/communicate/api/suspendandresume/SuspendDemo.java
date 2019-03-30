package szu.vander.communicate.api.suspendandresume;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class SuspendDemo {

    private static BlockingDeque<Bread> queue = new LinkedBlockingDeque<Bread>();

    public static void main(String args[]) {
        try {
            Thread consumeTread = consumeTread();
            Thread.sleep(1000);
            producerTread(consumeTread);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 正常的生产者线程
     */
    public static void producerTread(Thread consumerThread) {
        System.out.println("启动生产者线程：");
        new Thread(() -> {
            Bread bread = new Bread("bread", 1.0f);
            System.out.println("\t生产面包：" + bread);
            queue.add(bread);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("恢复消费者线程：");
            consumerThread.resume();
        }).start();
    }

    /**
     * 正常的消费者线程
     */
    public static Thread consumeTread() {
        Thread consumer = new Thread(() -> {
            // take不到东西说明面包还没生产好
            Bread bread = null;
            System.out.println("消费者线程被挂起！");
            Thread.currentThread().suspend();
            if ((bread = queue.poll()) != null) {
                System.out.println("\t获取到了面包：" + bread);
            } else {
                System.out.println("\t依旧获取不到面包");
            }
        });
        System.out.println("启动消费者线程：");
        consumer.start();
        return consumer;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @ToString
    protected  static class Bread {
        private String name;
        private float price;
    }

}
