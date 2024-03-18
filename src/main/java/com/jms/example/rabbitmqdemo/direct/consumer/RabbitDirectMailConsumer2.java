package com.jms.example.rabbitmqdemo.direct.consumer;

import com.jms.example.rabbitmqdemo.direct.configuration.RabbitMQDirectConfig;
import com.jms.example.rabbitmqdemo.model.SimpleMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitDirectMailConsumer2 {

    @RabbitListener(queues = RabbitMQDirectConfig.QUEUE_MAILBOX_NAME, containerFactory = RabbitMQDirectConfig.DIRECT_LISTENER_FACTORY)
    public void receiveMessage(SimpleMessage simpleMessage) {
        System.out.println("queueMail.mailbox -> Received direct message for 2 mail consumer: " + simpleMessage);
    }
}
