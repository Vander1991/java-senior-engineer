package szu.vander;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author : Vander
 * @date :   2019/8/17
 * @description :
 */
@EnableEurekaClient
@SpringBootApplication
public class HelloService2Application {
    public static void main(String[] args) {
        SpringApplication.run(HelloService2Application.class);
    }
}
