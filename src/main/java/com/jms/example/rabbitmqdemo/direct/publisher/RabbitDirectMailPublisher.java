package com.jms.example.rabbitmqdemo.direct.publisher;

import com.jms.example.rabbitmqdemo.model.SimpleMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import static com.jms.example.rabbitmqdemo.config.RabbitMQConfig.rabbitDirectTemplate;

@Service
public class RabbitDirectMailPublisher {

    @Autowired
    @Qualifier(rabbitDirectTemplate)
    private final RabbitTemplate rabbitTemplate;

    public RabbitDirectMailPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessageToQueue(String directName, String key, SimpleMessage simpleMessage) {
        this.rabbitTemplate.convertAndSend(directName, key,  simpleMessage);
    }

    public void sendMessageToQueue(String queueName, SimpleMessage simpleMessage) {
        this.rabbitTemplate.convertAndSend(queueName,  simpleMessage);
    }
}
