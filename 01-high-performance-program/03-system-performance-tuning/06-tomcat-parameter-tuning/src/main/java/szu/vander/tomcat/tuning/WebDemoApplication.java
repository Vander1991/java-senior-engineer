package szu.vander.tomcat.tuning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.Callable;

@SpringBootApplication
@EnableAsync
public class WebDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebDemoApplication.class, args);
    }
}
