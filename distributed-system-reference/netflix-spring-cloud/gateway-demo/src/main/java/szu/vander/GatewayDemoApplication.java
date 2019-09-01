package szu.vander;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import szu.vander.filter.JwtCheckFilterFactory;
import szu.vander.filter.JwtCheckGatewayFilterFactory;

/**
 * @author : Vander
 * @date :   2019/8/27
 * @description :
 */
@SpringBootApplication
public class GatewayDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayDemoApplication.class);
    }

//    @Bean
//    JwtCheckFilterFactory jwtCheckFilterFactory(){
//        return new JwtCheckFilterFactory();
//    }

    @Bean
    JwtCheckGatewayFilterFactory jwtCheckGatewayFilterFactory() {
        return new JwtCheckGatewayFilterFactory();
    }

}
