package szu.vander.test.standalone;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import szu.vander.cache.model.User;
import szu.vander.cache.standalone.SpringCacheService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@ActiveProfiles("single")
@Slf4j
public class SpringCacheTests {

    private final static String USER_ID = "2010130110";

    @Autowired
    SpringCacheService springCacheService;

    // ---------------spring cache注解演示
    // get
    @Test
    public void springCacheTest() {
        User user = springCacheService.findUserById(USER_ID);
        log.info(String.format("userId为%s的用户：%s", USER_ID, user.toString()));
    }

    // update
    @Test
    public void springCacheTest2() {
        springCacheService.updateUser(new User(USER_ID, "Panda"));
        User user = springCacheService.findUserById(USER_ID);
        log.info(String.format("userId为%s的用户：%s", USER_ID, user.toString()));
    }

    // delete
    @Test
    public void springCacheTest3() {
        springCacheService.deleteUserById(USER_ID);
    }
}
