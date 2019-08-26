package szu.vander.fallback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import szu.vander.service.HelloDemoService;


@Slf4j
@Component
public class HelloDemoFallback implements HelloDemoService {

    @Override
    public String hello() {
        log.info("call hello-service's hello method failed, using fallback strategy!!!");
        return "call hello-service's hello method failed, using fallback strategy!!!";
    }

    @Override
    public String helloHystrix() {
        log.info("call hello-service's helloHystrix method timeout, using fallback strategy!!!");
        return "call hello-service's helloHystrix method timeout, using fallback strategy!!!";
    }

    @Override
    public String getDescByName(String name) {
        log.info("call hello-service's getDescByName method failed, using fallback strategy!!!");
        return "call hello-service's getDescByName method failed, using fallback strategy!!!";
    }

    @Override
    public String getUser(String name) {
        log.info("call hello-service's getUser method failed, using fallback strategy!!!");
        return "call hello-service's getUser method failed, using fallback strategy!!!";
    }

}
