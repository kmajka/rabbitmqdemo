package com.jms.example.rabbitmqdemo.topic.consumer;

import com.jms.example.rabbitmqdemo.topic.configuration.RabbitMQTopicConfig;
import com.jms.example.rabbitmqdemo.model.SimpleMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitTopicWarningConsumer {

    @RabbitListener(queues = RabbitMQTopicConfig.QUEUE_WARNINGS_NAME, containerFactory = RabbitMQTopicConfig.TOPIC_LISTENER_FACTORY)
    public void receiveMessage(SimpleMessage simpleMessage) {
        System.out.println("queue.warnings -> Received topic message for warning consumer: " + simpleMessage);
    }
}
