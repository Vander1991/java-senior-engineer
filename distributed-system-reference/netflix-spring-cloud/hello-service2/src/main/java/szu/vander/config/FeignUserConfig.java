package szu.vander.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : Vander
 * @date :   2019/8/24
 * @description : 
 */
@Configuration
public class FeignUserConfig {

    @Bean
    Map<String, String> feignUserMap() {
        Map<String, String> userMap = new HashMap<>(2);
        return userMap;
    }

}
