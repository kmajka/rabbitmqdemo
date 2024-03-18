package com.jms.example.rabbitmqdemo.direct.publisher;

import com.jms.example.rabbitmqdemo.model.SimpleMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import static com.jms.example.rabbitmqdemo.direct.configuration.RabbitMQDirectConfig.RABBIT_DIRECT_TEMPLATE;

@Service
public class RabbitDirectMailPublisher {

    private final RabbitTemplate rabbitTemplate;

    public RabbitDirectMailPublisher(@Qualifier(RABBIT_DIRECT_TEMPLATE) RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessageToQueue(String directExchangeName, String key, SimpleMessage simpleMessage) {
        this.rabbitTemplate.convertAndSend(directExchangeName, key,  simpleMessage);
    }

    public void sendMessageToQueue(String queueName, SimpleMessage simpleMessage) {
        this.rabbitTemplate.convertAndSend(queueName,  simpleMessage);
    }
}
