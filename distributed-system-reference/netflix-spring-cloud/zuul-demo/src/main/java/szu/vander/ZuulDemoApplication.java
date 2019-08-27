package szu.vander;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author : caiwj
 * @date :   2019/8/17
 * @description :
 */
@EnableZuulProxy
@SpringBootApplication
public class ZuulDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZuulDemoApplication.class);
    }
}
