package szu.vander.mq.simple.queue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;

@SpringBootApplication
@EnableJms
public class Consumer {

    @JmsListener(destination = "boot-simple-queue")
    public void receive(String message) {
        System.out.println("收到消息：" + message);
    }

    public static void main(String[] args) {
        SpringApplication.run(Consumer.class, args);
    }
}
