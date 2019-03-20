package szu.vander.communicate.api.waitandnotify;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class WaitDemo {

    private static BlockingDeque<Bread> queue = new LinkedBlockingDeque<Bread>();

    public static void main(String args[]) {
        try {
            consumeTread();
            Thread.sleep(1000);
            producerTread();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 正常的生产者线程
     */
    public static void producerTread() {
        System.out.println("启动生产者线程：");
        new Thread(() -> {
            synchronized (queue) {
                Bread bread = new Bread("bread", 1.0f);
                System.out.println("\t生产面包：" + bread);
                queue.add(bread);
                System.out.println("恢复消费者线程：");
                queue.notifyAll();
            }
        }).start();
    }

    /**
     * 正常的消费者线程
     */
    public static Thread consumeTread() {
        Thread consumer = new Thread(() -> {
            synchronized (queue) {
                // take不到东西说明面包还没生产好
                Bread bread = null;
                System.out.println("消费者线程被挂起！");
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if ((bread = queue.poll()) != null) {
                    System.out.println("\t获取到了面包：" + bread);
                } else {
                    System.out.println("\t依旧获取不到面包");
                }
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
    protected static class Bread {
        private String name;
        private float price;
    }

}
