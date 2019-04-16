package com.myproject.tinyalarmclock.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitBeanConfig {

    private static final String DLX_EXCHANGE_NAME = "dlx-exchange";
    private static final String TTL_EXCHANGE_NAME = "ttl-exchange";
    private static final String TTL_ROUTING_KEY = "ttl-routing-key";
    private static final String TTL_QUEUE_NAME = "ttl-queue";
    private static final String DLX_QUEUE_NAME = "dlx-queue";

    private static final Integer QUEUE_EXPIRATION = 10000;

    /**
     * create DLX exchange
     */
    @Bean
    public DirectExchange createDLXExchange() {
        return new DirectExchange(DLX_EXCHANGE_NAME);
    }

    /**
     * create TTL exchange
     */
    @Bean
    public DirectExchange createTTLExchange() {
        return new DirectExchange(TTL_EXCHANGE_NAME);
    }

    @Bean
    public Queue createTTLQueue() {
        Map<String, Object> args = new HashMap();
        args.put("x-dead-letter-exchange", DLX_EXCHANGE_NAME);
        args.put("x-dead-letter-routing-key", TTL_ROUTING_KEY);
        args.put("x-message-ttl", QUEUE_EXPIRATION);
        return new Queue(TTL_QUEUE_NAME, false, false, false, args);
    }

    @Bean
    public Queue createDLXQueue() {
        return new Queue(DLX_QUEUE_NAME);
    }

    @Bean
    public Binding createDLXBinding(@Qualifier("createDLXExchange") DirectExchange directExchange,
                                    @Qualifier("createDLXQueue") Queue dlxQueue) {
        return BindingBuilder.bind(dlxQueue).to(directExchange).with(TTL_ROUTING_KEY);
    }

    @Bean
    public Binding createTTLBinding(@Qualifier("createTTLExchange") DirectExchange directExchange,
                                    @Qualifier("createTTLQueue") Queue ttlQueue) {
        return BindingBuilder.bind(ttlQueue).to(directExchange).with(TTL_ROUTING_KEY);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactoryMultiple(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(3);
        factory.setPrefetchCount(10);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }

}
