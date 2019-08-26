package szu.vander.web;

import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author : caiwj
 * @date :   2019/8/17
 * @description :
 */
@Slf4j
@RestController
public class HelloController {

    @Autowired
    private Map<String, String> feignUserMap;

    @GetMapping("/hello")
    public String hello() {
        return "Hello, I am hello-service2!!!";
    }

    @GetMapping("getDescByName")
    public String getDescByName(@RequestParam String name) {
        log.info("Call hello-service2's getDescByName method");
        String result = feignUserMap.get(name);
        if (StringUtils.isNotBlank(result)) {
            return result;
        }
        return String.format("there isn't this guy : %s", name);
    }

    @PostMapping("/addUser")
    public String addUser(@RequestBody Map<String, String> map) {
        String name = map.get("name");
        String desc = map.get("desc");
        if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(desc)) {
            feignUserMap.put(name, desc);
            return String.format("hello-service2 add user %s success!", name);
        }
        return "hello-service2 add user failed!!!";
    }

    @PostMapping("/getUser")
    public String getUser(@RequestBody Map<String, String> map) {
        String name = map.get("name");
        if (StringUtils.isNotBlank(name)) {
            String desc = feignUserMap.get(name);
            return desc;
        }
        return "hello-service2 get user failed!!!";
    }

}
