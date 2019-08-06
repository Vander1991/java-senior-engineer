package szu.vander;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

/**
 * @author : Vander
 * @date :   2019/8/3
 * @description :
 */
@EnableDubbo(scanBasePackages = "szu.vander")
@SpringBootApplication
public class DubboProviderApplication {
    public static void main(String[] args){
        SpringApplication.run(DubboProviderApplication.class);
        try {
            System.in.read();
        } catch (IOException e) {
        	e.printStackTrace();
        }
    }
}
