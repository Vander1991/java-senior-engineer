package szu.vander.service;

import feign.Body;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import szu.vander.fallback.HelloDemoFallback;

@Component
@FeignClient(name = "hello-service", fallback = HelloDemoFallback.class)
public interface HelloDemoService {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    String hello();

    @GetMapping("/helloHystrix")
    String helloHystrix();

    @GetMapping(value = "/getDescByName")
    String getDescByName(@RequestParam("name") String name);

    /**
     * {--%7B %7D--}
     * @param name
     * @return
     */
    @Body("%7B\"name\":\"{name}\"%7D")
    @RequestMapping(value = "/getUser", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    String getUser(@Param("name") String name);

}
