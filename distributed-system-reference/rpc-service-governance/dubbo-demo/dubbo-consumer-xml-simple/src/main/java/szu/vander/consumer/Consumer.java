package szu.vander.consumer;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import szu.vander.service.DemoService;

/**
 * @author : Vander
 * @date :   2019/8/3
 * @description :
 */
public class Consumer {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:consumer.xml");
        context.start();
        // 获取远程服务代理
        DemoService demoService = (DemoService)context.getBean("demoService");
        // 显示代理对象
        System.out.println(demoService);
        // 执行远程方法
        String hello = demoService.sayHello("world");
        // 显示调用结果
        System.out.println( hello );
    }
}