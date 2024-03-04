package com.jms.example.rabbitmqdemo.topic.consumer;

import com.jms.example.rabbitmqdemo.config.RabbitMQConfig;
import com.jms.example.rabbitmqdemo.model.SimpleMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitTopicWarningConsumer {

    @RabbitListener(queues = RabbitMQConfig.queueWarningsName, containerFactory = RabbitMQConfig.myTopicListenerFactory)
    public void receiveMessage(SimpleMessage simpleMessage) {
        System.out.println("queue.warnings -> Received topic message for warning consumer: " + simpleMessage);
    }
}
