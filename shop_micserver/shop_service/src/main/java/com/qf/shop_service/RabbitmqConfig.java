package com.qf.shop_service;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {
    @Bean
    public FanoutExchange getExchange(){
     return new FanoutExchange("good_exchange");
    }
}
