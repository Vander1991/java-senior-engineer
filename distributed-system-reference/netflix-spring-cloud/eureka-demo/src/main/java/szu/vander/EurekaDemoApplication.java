package szu.vander;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author : Vander
 * @date :   2019/8/17
 * @description :
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaDemoApplication.class);
    }
}
