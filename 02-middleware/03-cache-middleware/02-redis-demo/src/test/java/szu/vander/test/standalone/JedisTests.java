package szu.vander.test.standalone;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@ActiveProfiles("single") // 设置profile
@Slf4j
public class JedisTests {

    @Autowired
    private Jedis jedis;

    // ------------------------ jedis 工具直连演示
    // jedis和redis命令名称匹配度最高，最为简洁，学习难度最低

    // 列表~ 集合数据存储~ java.util.List，java.util.Stack
    // 生产者消费者（简单MQ）
    @Test
    public void list() {

        // 插入数据1 --- 2 --- 3
        jedis.rpush("queue_1", "1");
        jedis.rpush("queue_1", "2", "3");

        List<String> strings = jedis.lrange("queue_1", 0, -1);
        for (String string : strings) {
            log.info(String.format("往队列queue_1中写入：%s", string));
        }

        // 消费者线程简例
        while (true) {
            String item = jedis.lpop("queue_1");
            if (item == null) break;
            log.info(String.format("从队列queue_1中取出：%s", item));
        }

        jedis.close();
    }

    // 类似：在redis里面存储一个hashmap
    // 推荐的方式，无特殊需求是，一般的缓存都用这个
    @Test
    public void hashTest() {
        String key = "2010130110";
        jedis.hset(key, "name", "Vander");
        jedis.hset(key, "age", "18");

        jedis.hget(key, "name");
        log.info(String.format("获取Key=%s的所有相关属性：%s", key, jedis.hgetAll(key).toString()));
        jedis.close();
    }

    // 用set实现（交集 并集）
    // 交集示例： 共同关注的好友
    // 并集示例：
    @Test
    public void setTest() {
        // 取出两个人共同关注的好友
        // 每个人维护一个set
        jedis.sadd("userA", "userC", "userD", "userE");
        jedis.sadd("userB", "userC", "userE", "userF");
        // 取出共同关注
        Set<String> intersection = jedis.sinter("userA", "userB");
        log.info(String.format("获取userA{%s}和userB{%s}的交集：%s", jedis.smembers("userA"), jedis.smembers("userB"), intersection));

        // 取出共同人群
        Set<String> unionSet = jedis.sunion("userA", "userB");
        log.info(String.format("获取userA{%s}和userB{%s}的并集：%s", jedis.smembers("userA"), jedis.smembers("userB"), unionSet));

        jedis.close();
    }

    // 游戏排行榜
    @Test
    public void zsetTest() {
        String ranksKeyName = "exam_rank";
        jedis.zadd(ranksKeyName, 100.0, "stu1");
        jedis.zadd(ranksKeyName, 82.0, "stu2");
        jedis.zadd(ranksKeyName, 90, "stu3");
        jedis.zadd(ranksKeyName, 96, "stu4");
        jedis.zadd(ranksKeyName, 89, "stu5");
        jedis.zadd(ranksKeyName, 29, "stu6");

        Set<String> stringSet = jedis.zrevrange(ranksKeyName, 0, 2);
        System.out.println("返回前三名:");
        for (String s : stringSet) {
            System.out.println(s);
        }

        long zcount = jedis.zcount(ranksKeyName, 85, 100);
        System.out.println("超过85分的数量 " + zcount);

        jedis.close();
    }
}

