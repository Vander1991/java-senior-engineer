package szu.vander.communicate.api.parkandunpark;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.LockSupport;

/**
 * 使用Park unPark实现生产者消费者模式
 */
public class ParkDemo {

    private static BlockingDeque<ParkDemo.Bread> queue = new LinkedBlockingDeque<>();

    public static void main(String args[]) {
        Thread consumerThread = consumeTread();
        producerTread(consumerThread);
        System.out.println("启动消费者线程！");
        consumerThread.start();
    }

    /**
     * 正常的生产者线程
     */
    public static void producerTread(Thread consumerThread) {
        System.out.println("启动生产者线程！");
        new Thread(() -> {
            ParkDemo.Bread bread = new ParkDemo.Bread("bread", 1.0f);
            System.out.println("\t生产面包：" + bread);
            queue.add(bread);
            System.out.println("生产者线程唤醒消费者线程！");
            LockSupport.unpark(consumerThread);
        }).start();
    }

    /**
     * 正常的消费者线程
     */
    public static Thread consumeTread() {
        Thread consumer = new Thread(() -> {
            // take不到东西说明面包还没生产好
            ParkDemo.Bread bread = null;
            System.out.println("消费者线程被挂起！");
            LockSupport.park();
            System.out.println("消费者线程被唤醒！");
            if ((bread = queue.poll()) != null) {
                System.out.println("\t获取到了面包：" + bread);
            } else {
                System.out.println("\t依旧获取不到面包");
            }
        });
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
