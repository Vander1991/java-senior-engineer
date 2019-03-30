package szu.vander.threadlocal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Auther: Vander
 * @Date: 2019/3/20
 * @Description:
 */
public class ThreadLocalDemo {

    /**
     * threadLocal变量，每个线程都有一个副本，互不干扰
     */
    public static ThreadLocal<Bread> value = new ThreadLocal<>();

    public static void main(String[] args) throws Exception {
        producer1Thread();
        producer2Thread();
    }

    /**
     * threadlocal测试
     *
     * @throws Exception
     */
    public static Thread producer1Thread() throws Exception {
        Thread producer1Thread = new Thread(() -> {
            synchronized (value){
                System.out.println("producer1Thread: " + value.get());
                value.set(new Bread("producer1Bread", 10f));
                System.out.println("producer1Thread: " + value.get());
            }
        });
        producer1Thread.start();
        return producer1Thread;
    }

    /**
     * threadlocal测试
     *
     * @throws Exception
     */
    public static Thread producer2Thread() throws Exception {
        Thread producer2Thread = new Thread(() -> {
            synchronized (value){
                System.out.println("producer2Thread: " + value.get());
                value.set(new Bread("producer2Bread", 10f));
                System.out.println("producer2Thread: " + value.get());
            }
        });
        producer2Thread.start();
        return producer2Thread;
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
