package com.jms.example.rabbitmqdemo.header.publisher;

import com.jms.example.rabbitmqdemo.model.SimpleMessage;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static com.jms.example.rabbitmqdemo.config.RabbitMQConfig.rabbitHeaderTemplate;

@Component
public class RabbitHeaderPublisher {

    @Qualifier(rabbitHeaderTemplate)
    private final RabbitTemplate rabbitTemplate;

    @Qualifier("converterForHeaderExchange")
    private final MessageConverter converterForHeaderExchange;

    public RabbitHeaderPublisher(RabbitTemplate rabbitTemplate, MessageConverter converterForHeaderExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.converterForHeaderExchange = converterForHeaderExchange;
    }

    public void sendMessageToQueue(String headerExchangeName, String routingKey,
                                   SimpleMessage simpleMessage, String headerKey, Object headerValue) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader(headerKey, headerValue);

        //MessageConverter messageConverter = new SimpleMessageConverter();
        Message message = converterForHeaderExchange.toMessage(simpleMessage, messageProperties);

        this.rabbitTemplate.send(headerExchangeName, routingKey, message);
    }
}
