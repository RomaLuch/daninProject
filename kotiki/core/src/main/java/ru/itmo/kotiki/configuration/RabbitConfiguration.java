package ru.itmo.kotiki.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    @Bean
    public Queue catQueue() {
        return new Queue("catQueue", false);
    }

    @Bean
    public Queue ownerQueue() {
        return new Queue("ownerQueue", false);
    }

}
