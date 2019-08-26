package szu.vander.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import szu.vander.service.HelloDemoService;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CustomerController {

    @Autowired
    HelloDemoService helloDemoService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @GetMapping("/hello")
    public String hello() {
        return helloDemoService.hello();
    }

    @GetMapping("/helloHystrix")
    public String helloHystrix() {
        return helloDemoService.helloHystrix();
    }

    @GetMapping("/findUser")
    public String getUserDesc(@RequestParam("name") String name) {
        return helloDemoService.getDescByName(name);
    }

    @GetMapping("/getUser")
    public String getUser(@RequestParam("name") String name) {
        return helloDemoService.getUser(name);
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(@RequestParam("name") String name) {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", name);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //封装成一个请求对象
        HttpEntity entity = new HttpEntity(map, headers);
        String result = restTemplate.postForEntity("http://127.0.0.1:8081/getUser", entity, String.class).getBody();
        return result;
    }

}
