package com.myproject.tinyalarmclock.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer {
    private static final String TTL_EXCHANGE_NAME = "ttl-exchange";
    private static final String TTL_ROUTING_KEY = "ttl-routing-key";

    @Autowired
    RabbitTemplate template;

    public void recordThoughts(String message) {
        System.out.println("Send<" + message + ">");
        template.convertAndSend(TTL_EXCHANGE_NAME, TTL_ROUTING_KEY, message);
    }

}
