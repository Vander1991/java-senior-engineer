package szu.vander.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author : Vander
 * @date :   2019/7/21
 * @description :
 */
@Configuration
public class ProfileTestConfig {

    @Profile("dev")
    @Bean
    public String profileTest1() {
        System.out.println("当前使用的是dev环境");
        return "dev profile";
    }

    @Bean
    @Profile("local")
    public String profileTest2() {
        System.out.println("当前使用的是local环境");
        return "local profile";
    }

}
