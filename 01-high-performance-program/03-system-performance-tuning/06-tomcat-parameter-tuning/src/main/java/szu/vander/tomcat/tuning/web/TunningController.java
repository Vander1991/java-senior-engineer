package szu.vander.tomcat.tuning.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.Callable;

/**
 * @Auther: Vander
 * @Date: 2019/4/11
 * @Description:
 */
@RestController
public class TunningController {

    // 这个方法固定延时3秒，用于测试线程/连接数量控制
    @RequestMapping("/testCount")
    public String testCount() throws InterruptedException {
        Thread.sleep(3000);// connections  acceptCount
        return "Success";
    }

    @RequestMapping("/test")
    public String benchmark() throws InterruptedException {
        System.out.println("访问test：" + Thread.currentThread().getName());

        // 这段代码，一直运算。
        for (int i = 0; i < 200000; i++) {
            new Random().nextInt();
        }
        // 50毫秒的数据库等待，线程不干活
        Thread.sleep(50L);
        return "Success";
    }

    // 异步支持
    @RequestMapping("/testAsync")
    public Callable<String> benchmarkAsync() throws InterruptedException {
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("访问testAsync：" + Thread.currentThread().getName());
                // 这段代码，一直运算。
                for (int i = 0; i < 200000; i++) {
                    new Random().nextInt();
                }
                // 50毫秒的数据库等待，线程不干活
                Thread.sleep(50L);
                return "Success";
            }
        };
    }

}
