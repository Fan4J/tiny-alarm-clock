package com.myproject.tinyalarmclock.config;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Component
public class NotifyReceiver {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");

    @RabbitListener(containerFactory = "rabbitListenerContainerFactoryMultiple",queues = "dlx-queue")
    public void notifyReceive(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        System.out.println("Receive<" + message + "> at " + sdf.format(new Date()));
        channel.basicAck(deliveryTag, false);
    }
}
