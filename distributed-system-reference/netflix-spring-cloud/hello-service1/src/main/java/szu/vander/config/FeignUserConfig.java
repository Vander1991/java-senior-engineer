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
        userMap.put("Vander", "Vander is a earthman!!!");
        userMap.put("Winnie", "Winnie is a Martian!!!");
        return userMap;
    }

}
