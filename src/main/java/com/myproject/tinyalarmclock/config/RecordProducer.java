package com.myproject.tinyalarmclock.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class RecordProducer {
    private static final String TTL_EXCHANGE_NAME = "ttl-exchange";
    private static final String TTL_ROUTING_KEY = "ttl-routing-key";
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
    @Autowired
    RabbitTemplate template;

    public void recordThoughts(String message) {
        System.out.println("Send<" + message + "> at "+sdf.format(new Date()));
        template.convertAndSend(TTL_EXCHANGE_NAME, TTL_ROUTING_KEY, message);
    }

}
