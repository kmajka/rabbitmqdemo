package com.jms.example.rabbitmqdemo.direct.consumer;

import com.jms.example.rabbitmqdemo.config.RabbitMQConfig;
import com.jms.example.rabbitmqdemo.model.SimpleMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitDirectMailConsumer1 {

    @RabbitListener(queues = RabbitMQConfig.queueMailboxName, containerFactory = RabbitMQConfig.myDirectListenerFactory)
    public void receiveMessage(SimpleMessage simpleMessage) {
        System.out.println("queueMail.mailbox -> Received direct message for 1 mail consumer: " + simpleMessage);
    }
}
