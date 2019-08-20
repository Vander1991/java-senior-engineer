package szu.vander.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : caiwj
 * @date :   2019/8/17
 * @description :
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, I am hello-service1!!!";
    }

}
