package szu.vander.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author : caiwj
 * @date :   2019/8/17
 * @description :
 */
@RestController
@RibbonClients(
        @RibbonClient("hello-service")
)
public class HelloConsumerController {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${hello.service.url:http\\://hello-service/hello}")
    private String requestUrl;

    @GetMapping("/hello/consume")
    public String consume() {
        ServiceInstance serviceInstance = loadBalancerClient.choose("hello-service");
        String ipAddress = serviceInstance.getHost();
        int port = serviceInstance.getPort();
        String result = restTemplate.getForObject(String.format("http://%s:%s/hello", ipAddress, port), String.class);
        return result;
    }

}
