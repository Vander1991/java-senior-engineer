package szu.vander.mq.simple.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.core.JmsTemplate;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Producer {

    @Autowired
    private JmsTemplate jmsTemplate;

    @PostConstruct
    public void init() {
        jmsTemplate.convertAndSend("boot-simple-queue", "Hello Spring 4");
    }

    public static void main(String[] args) {
        SpringApplication.run(Producer.class, args);
    }
}
