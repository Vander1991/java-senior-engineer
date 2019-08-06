package szu.vander.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author : Vander
 * @date :   2019/8/3
 * @description :
 */
public class Provider {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:provider.xml");
        context.start();
        // 按任意键退出
        System.in.read();
    }
}