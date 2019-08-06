package szu.vander.consumer;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import szu.vander.web.AnnotationAction;

/**
 * @author : Vander
 * @date :   2019/8/3
 * @description :
 */
@Configuration
@EnableDubbo(scanBasePackages = "szu.vander.web.AnnotationAction")
@PropertySource("classpath:dubbo-consumer.properties")
@ComponentScan(value = { "szu.vander" })
public class AnnotationConsumerConfiguration {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                AnnotationConsumerConfiguration.class);
        context.start();
        final AnnotationAction annotationAction = context.getBean(AnnotationAction.class);
        System.out.println(annotationAction);
        String hello = annotationAction.doSayHello("world");
        System.out.println(hello);
        context.close();
    }
}
