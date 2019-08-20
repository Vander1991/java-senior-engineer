package szu.vander.web;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import szu.vander.hystrix.HystrixCommandImpl;

/**
 * @author : caiwj
 * @date :   2019/8/17
 * @description :
 */
@RestController
public class HystrixHelloController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @GetMapping("/helloHystrix")
    public Object helloHystrix() {
        return new HystrixCommandImpl(restTemplate).execute();
    }


    @HystrixCommand(fallbackMethod = "callTimeoutFallback",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "1"),
                    @HystrixProperty(name = "queueSizeRejectionThreshold", value = "1")
            },
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100")
            })
    @GetMapping("helloHystrix2")
    public Object helloHystrix2() {
        return restTemplate.getForObject("http://localhost:8081/hello", String.class);
    }

    public Object callTimeoutFallback() {
        return "请求index2降级";
    }

}
