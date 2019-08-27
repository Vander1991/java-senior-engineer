package szu.vander.stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import java.util.Locale;

@SpringBootApplication
@EnableBinding(value = {Sink.class, Source.class})
public class SpringCloudStreamApplication {

    public static void main(String[] args) throws InterruptedException {
       ConfigurableApplicationContext context =  SpringApplication.run(SpringCloudStreamApplication.class, args);
       Source source = context.getBean(Source.class);
       Thread.sleep(3000L);
       source.output().send(new GenericMessage("12345"));
    }

    @Bean
    @InboundChannelAdapter(value = Source.OUTPUT,poller = @Poller(fixedDelay = "5000",maxMessagesPerPoll = "1"))
    public MessageSource<String> sendMessage() {
        return new MessageSource<String>() {
            @Override
            public Message<String> receive() {
                return new GenericMessage("hello,spring cloud stream");
            }
        };
    }




}
