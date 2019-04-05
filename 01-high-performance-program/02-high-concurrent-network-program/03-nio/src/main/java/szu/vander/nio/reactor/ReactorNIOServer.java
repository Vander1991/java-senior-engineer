package szu.vander.nio.reactor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Auther: Vander
 * @Date: 2019/4/4
 * @Description:
 *
 *          Reator:通过分配给特定的Handler来响应IO事件
 *          Handler：执行非阻塞任务
 */
public class ReactorNIOServer {

    private static ExecutorService mainRector;

    private static ExecutorService subRector;

    private final static int MAIN_REACTOR_THREAD_NUM = 1;

    private final static int SUB_REACTOR_THREAD_NUM = 1;

    static {
        mainRector = Executors.newFixedThreadPool(1);
        subRector = Executors.newFixedThreadPool(5);
    }



}
