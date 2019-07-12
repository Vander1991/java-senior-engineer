package szu.vander.demo.lock;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author : Vander
 * @date :   2019/7/7
 * @description :
 */
public class ThreadPoolConfig {

    /**
     * 创建线程池
     * @return
     */
    public static ExecutorService createThreadPool() {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("zk-distribute-lock-pool-%d").build();
        ExecutorService threadPoolExecutor = new ThreadPoolExecutor(50, 50,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024), namedThreadFactory);
        return  threadPoolExecutor;
    }

}
