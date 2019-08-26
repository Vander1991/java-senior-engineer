package szu.vander.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : Vander
 * @date :   2019/7/21
 * @description :
 */
@Configuration
public class ReadWriteSplitRedisTemplateConfiguration {

    @Bean
    @ConditionalOnClass(name = "org.springframework.data.redis.core.RedisTemplate")
    public ReadWriteSplitRedisTemplate readWriteSplitRedisTemplate() {
        return new ReadWriteSplitRedisTemplate();
    }

    /**
     * 带有负载均衡功能的RedisTemplate
     */
    public static class ReadWriteSplitRedisTemplate {
        public ReadWriteSplitRedisTemplate() {
            System.out.println("注入一个带有读写分离功能的RedisTemplate！");
        }
    }

}
