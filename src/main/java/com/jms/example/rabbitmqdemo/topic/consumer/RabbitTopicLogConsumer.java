package com.jms.example.rabbitmqdemo.topic.consumer;

import com.jms.example.rabbitmqdemo.config.RabbitMQConfig;
import com.jms.example.rabbitmqdemo.model.SimpleMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitTopicLogConsumer {

   @RabbitListener(queues = RabbitMQConfig.queueLogsName, containerFactory = RabbitMQConfig.myTopicListenerFactory)
    public void receiveMessage(SimpleMessage simpleMessage) {
        System.out.println("queue.logs -> Received topic message for log consumer: " + simpleMessage);
    }
}
