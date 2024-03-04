package com.jms.example.rabbitmqdemo.topic.publisher;

import com.jms.example.rabbitmqdemo.model.SimpleMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static com.jms.example.rabbitmqdemo.config.RabbitMQConfig.rabbitTopicTemplate;

@Component
public class RabbitTopicPublisher {

    @Autowired
    @Qualifier(rabbitTopicTemplate)
    private final RabbitTemplate rabbitTemplate;

    public RabbitTopicPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessageToQueue(String topicExchangeName, String routingKey, SimpleMessage simpleMessage) {
        this.rabbitTemplate.convertAndSend(topicExchangeName, routingKey, simpleMessage);
    }
}
