package szu.vander.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Size;
import java.util.Random;

/**
 * @author : Vander
 * @date :   2019/8/24
 * @description : 
 */
@Slf4j
@RestController
public class SleepController {

    @GetMapping("/helloHystrix")
    public String hello() throws InterruptedException {
        Random random = new Random();
        int sleepTime = random.nextInt(200);
        log.info("hello-service1：sleepTime : " + sleepTime);
        Thread.sleep(sleepTime);
        return "hello-service1's hello hystrix !!!";
    }

}
