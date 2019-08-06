package szu.vander.web;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;
import szu.vander.service.DemoService;

/**
 * @author : Vander
 * @date :   2019/8/3
 * @description :
 */
@Component("annotationAction")
public class AnnotationAction {

    @Reference
    private DemoService demoService;

    public String doSayHello(String name) {
        return demoService.sayHello(name);
    }
}

