package com.jms.example.rabbitmqdemo.defaultexchange.consumer;

import com.jms.example.rabbitmqdemo.defaultexchange.configuration.RabbitMQDefaultExchangeConfig;
import com.jms.example.rabbitmqdemo.model.SimpleMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitDefaultExchangeConsumer {

    @RabbitListener(queues = RabbitMQDefaultExchangeConfig.QUEUE_DEFAULTEXCHANGE_NAME, containerFactory = RabbitMQDefaultExchangeConfig.DEFAULTEXCHANGE_LISTENER_FACTORY)
    public void receiveMessage(SimpleMessage simpleMessage) {
        System.out.println("queueMail.mailbox -> Received default exchange message for mail consumer: " + simpleMessage);
    }
}
