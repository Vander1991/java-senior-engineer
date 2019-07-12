package szu.vander.test.standalone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import szu.vander.cache.model.User;
import szu.vander.cache.standalone.SingleExampleService;

import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@ActiveProfiles("single")
public class SingleTests {

    @Autowired
    SingleExampleService exampleService;

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void testSet() {
        exampleService.setByCache("Vander", "A Junior Java Engineer!");
        exampleService.setByCache("Panda", "A Senior Java Engineer!");
    }

    @Test
    public void testGet() throws Exception {
        User user = exampleService.findUser("2010130110");
        System.out.println(user);
    }

    @Test
    public void testGetZSet() {
        Set<String> strings = redisTemplate.opsForZSet().range("testSet", 0, 3);
        System.out.println(strings);
    }

}