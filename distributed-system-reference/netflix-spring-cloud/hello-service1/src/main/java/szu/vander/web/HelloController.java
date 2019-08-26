package szu.vander.web;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author : Vander
 * @date :   2019/8/24
 * @description : 
 */
@Slf4j
@RestController
public class HelloController {

    @Autowired
    Map<String, String> feignUserMap;

    @GetMapping("/hello")
    public String hello() {
        return "Hello, I am hello-service1!!!";
    }

    @GetMapping("getDescByName")
    public String getDescByName(@RequestParam String name) {
        log.info("Call hello-service1's getDescByName method");
        return feignUserMap.get(name);
    }

    @PostMapping("/addUser")
    public String addUser(@RequestBody Map<String, String> map) {
        String name = map.get("name");
        String desc = map.get("desc");
        if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(desc)) {
            feignUserMap.put(name, desc);
            return String.format("hello-service1 add user %s success!", name);
        }
        return "hello-service1 add user failed!!!";
    }

    @RequestMapping(value = "/getUser", method = RequestMethod.POST)
    public String getUser(@RequestBody Map<String, String> map) {
        String name = map.get("name");
        if (StringUtils.isNotBlank(name)) {
            String desc = feignUserMap.get(name);
            return desc;
        }
        return "hello-service1 get user failed!!!";
    }

}
