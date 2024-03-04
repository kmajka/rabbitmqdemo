package com.jms.example.rabbitmqdemo.topic.consumer;

import com.jms.example.rabbitmqdemo.config.RabbitMQConfig;
import com.jms.example.rabbitmqdemo.model.SimpleMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitTopicErrorConsumer {

   @RabbitListener(queues = RabbitMQConfig.queueErrorsName, containerFactory = RabbitMQConfig.myTopicListenerFactory)
    public void receiveMessage(SimpleMessage simpleMessage) {
        System.out.println("queue.errors -> Received topic message for error consumer: " + simpleMessage);
    }
}
