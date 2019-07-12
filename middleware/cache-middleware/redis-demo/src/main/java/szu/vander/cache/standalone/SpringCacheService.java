package szu.vander.cache.standalone;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import szu.vander.cache.model.User;

@Service
@Profile("single")
@Slf4j
public class SpringCacheService {

    /**
     * springcache注解版本（官方大部分资料开始往springboot方向引导，实际上不用springboot，也是差不多的方式）
     */
    // value~单独的缓存前缀
    // key缓存key 可以用springEL表达式
    @Cacheable(cacheManager = "cacheManager", value = "cache-1", key = "#userId")
    public User findUserById(String userId) {
        // 读取数据库
        User user = new User(userId, "Vander");
        log.info(String.format("从数据库中读取到数据：%s", user));
        return user;
    }

    @CacheEvict(cacheManager = "cacheManager", value = "cache-1", key = "#userId")
    public void deleteUserById(String userId) {
        log.info(String.format("用户从数据库删除成功，请检查缓存ID：%s是否已经清除！", userId));
    }

    // 如果数据库更新成功，更新redis缓存
    @CachePut(cacheManager = "cacheManager", value = "cache-1", key = "#user.userId", condition = "#result ne null")
    public User updateUser(User user) {
        // 读取数据库
        log.info("数据库进行了更新，检查缓存是否一致");
        return user; // 返回最新内容，代表更新成功
    }
}
