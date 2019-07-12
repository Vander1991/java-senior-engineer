package szu.vander.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import szu.vander.demo.service.Message;
import szu.vander.demo.service.Producer;

/**
 * @Auther: allen
 * @Date: 2019/2/26 15:03
 */
@RestController
@RequestMapping("kafka")
public class SendController {

    @Autowired
    private Producer producer;

    @RequestMapping(value = "send")
    public String send() {
        producer.sendMessage(new Message());
        return "{\"code\":0}";
    }
}
