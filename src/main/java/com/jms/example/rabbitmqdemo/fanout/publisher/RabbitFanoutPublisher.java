package com.jms.example.rabbitmqdemo.fanout.publisher;

import com.jms.example.rabbitmqdemo.model.SimpleMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static com.jms.example.rabbitmqdemo.fanout.configuration.RabbitMQFanoutConfig.RABBIT_FANOUT_TEMPLATE;

@Component
public class RabbitFanoutPublisher {

    private final RabbitTemplate rabbitTemplate;

    public RabbitFanoutPublisher(@Qualifier(RABBIT_FANOUT_TEMPLATE) RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessageToQueue(String fanoutExchangeName, SimpleMessage simpleMessage) {
        this.rabbitTemplate.convertAndSend(fanoutExchangeName, simpleMessage);
    }
}
