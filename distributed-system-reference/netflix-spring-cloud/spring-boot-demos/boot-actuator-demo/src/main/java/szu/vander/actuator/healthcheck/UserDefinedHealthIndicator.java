package szu.vander.actuator.healthcheck;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author : Vander
 * @date :   2019/8/12
 * @description : 自定义检查项
 */
@Component
public class UserDefinedHealthIndicator extends AbstractHealthIndicator {
    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        // 测试，具体怎么检查，看你的业务细节
        int i = new Random().nextInt();
        if (i % 2 == 0) {
            builder.withDetail("细节", "1").up();
        } else {
            builder.withDetail("细节", "2").down();
        }
    }
}