package szu.vander.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : Vander
 * @date :   2019/7/20
 * @description :
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello My Spring Boot Application";
    }

}
