package szu.vander.web;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import szu.vander.service.DemoService;

/**
 * @author : Vander
 * @date :   2019/8/3
 * @description :
 */
@RestController
public class AnnotationAction {

    @Reference
    private DemoService demoService;

    @GetMapping("/hello")
    public String doSayHello(String name) {
        return demoService.sayHello(name);
    }
}

