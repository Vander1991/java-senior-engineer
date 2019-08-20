package szu.vander.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author : caiwj
 * @date :   2019/8/17
 * @description :
 */
@RestController
public class HelloConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${hello.service.url:http\\://hello-service/hello}")
    private String requestUrl;

    @GetMapping("/hello/consume")
    public String consume() {
        String result = restTemplate.getForObject(requestUrl, String.class);
        return result;
    }

}
