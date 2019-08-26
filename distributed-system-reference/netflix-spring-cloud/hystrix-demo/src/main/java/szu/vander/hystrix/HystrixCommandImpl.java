package szu.vander.hystrix;

import com.netflix.hystrix.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author : caiwj
 * @date :   2019/8/18
 * @description :
 */
@Slf4j
@Component
public class HystrixCommandImpl extends HystrixCommand<Object> {

    private RestTemplate restTemplate;

    public HystrixCommandImpl(RestTemplate restTemplate) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("hystrix-demo"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("HystrixHelloController"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("HystrixCommandImplThreadPool"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(100)
                        .withCircuitBreakerSleepWindowInMilliseconds(5000))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                        .withCoreSize(1)
                        .withMaxQueueSize(2)));
        this.restTemplate = restTemplate;
    }

    @Override
    protected Object run() {
        log.info("Call HystrixCommandImpl run method!!!");
        Object result = restTemplate.getForObject("http://localhost:8081/helloHystrix", String.class);
        return result;
    }

    @Override
    protected Object getFallback() {
        // 降级后调用此方法
        log.info("Call HystrixCommandImpl getFallback method!!!");
        return "HystrixCommandImpl的降级方法！！！";
    }
}
