package szu.vander.test.geo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import szu.vander.cache.geo.GeoExampleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@ActiveProfiles("geo") // 设置profile
@Slf4j
public class GeoTests {

    @Autowired
    GeoExampleService geoExampleService;

    @Test
    public void test1() throws InterruptedException {
        // 模拟三个人位置上报
        geoExampleService.add(new Point(116.405285, 39.904989), "allen");
        geoExampleService.add(new Point(116.405265, 39.904969), "mike");
        geoExampleService.add(new Point(116.405315, 39.904999), "tony");

        // tony查找附近的人
        GeoResults<RedisGeoCommands.GeoLocation> geoResults = geoExampleService.near(new Point(116.405315, 39.904999));
        for (GeoResult<RedisGeoCommands.GeoLocation> geoResult : geoResults) {
            RedisGeoCommands.GeoLocation content = geoResult.getContent();
            log.info(String.format("经度116.405315，纬度39.904999附近有%s，距离此地有%s！", content.getName(), geoResult.getDistance().getValue()));
        }
    }
}
