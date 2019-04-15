package com.myproject.tinyalarmclock.config;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class NotifyReceiver {

    @RabbitListener(queues = "dlx-queue")
    public void notifyReceive(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        System.out.println("Receive<" + message + ">");
        channel.basicAck(deliveryTag, false);
    }
}
