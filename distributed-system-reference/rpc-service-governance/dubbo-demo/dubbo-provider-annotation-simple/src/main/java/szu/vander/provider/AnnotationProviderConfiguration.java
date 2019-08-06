package szu.vander.provider;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;

/**
 * @author : Vander
 * @date :   2019/8/3
 * @description :
 */
@Configuration
@EnableDubbo(scanBasePackages = "szu.vander")
@PropertySource("classpath:dubbo-provider.properties")
public class AnnotationProviderConfiguration {

    public static void main(String[] args){
        AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext(AnnotationProviderConfiguration.class);
        annotationConfigApplicationContext.start();
        try {
            System.in.read();
        } catch (IOException e) {
        	e.printStackTrace();
        }
        annotationConfigApplicationContext.close();
    }

}
