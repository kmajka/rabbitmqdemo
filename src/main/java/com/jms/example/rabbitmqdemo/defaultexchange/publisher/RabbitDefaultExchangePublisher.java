package com.jms.example.rabbitmqdemo.defaultexchange.publisher;

import com.jms.example.rabbitmqdemo.model.SimpleMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import static com.jms.example.rabbitmqdemo.defaultexchange.configuration.RabbitMQDefaultExchangeConfig.RABBIT_DEFAULTEXCHANGE_TEMPLATE;

@Service
public class RabbitDefaultExchangePublisher {

    private final RabbitTemplate rabbitTemplate;

    public RabbitDefaultExchangePublisher(@Qualifier(RABBIT_DEFAULTEXCHANGE_TEMPLATE) RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessageToQueue(String queueName, SimpleMessage simpleMessage) {
        this.rabbitTemplate.convertAndSend(queueName, simpleMessage);
    }

}
