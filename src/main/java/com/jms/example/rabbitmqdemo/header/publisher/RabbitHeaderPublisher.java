package com.jms.example.rabbitmqdemo.header.publisher;

import com.jms.example.rabbitmqdemo.model.SimpleMessage;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.jms.example.rabbitmqdemo.configuration.RabbitMQConfig.CONVERTER_FOR_HEADER_EXCHANGE;
import static com.jms.example.rabbitmqdemo.header.configuration.RabbitMQHeaderConfig.RABBIT_HEADER_TEMPLATE;

@Component
public class RabbitHeaderPublisher {

    private final RabbitTemplate rabbitTemplate;

    private final MessageConverter converterForHeaderExchange;

    public RabbitHeaderPublisher(@Qualifier(RABBIT_HEADER_TEMPLATE) RabbitTemplate rabbitTemplate,
                                 @Qualifier(CONVERTER_FOR_HEADER_EXCHANGE) MessageConverter converterForHeaderExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.converterForHeaderExchange = converterForHeaderExchange;
    }

    public void sendMessageToQueue(String headerExchangeName, String routingKey,
                                   SimpleMessage simpleMessage, Map<String, Object> headers) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeaders(headers);

        //MessageConverter messageConverter = new SimpleMessageConverter();
        Message message = converterForHeaderExchange.toMessage(simpleMessage, messageProperties);

        this.rabbitTemplate.send(headerExchangeName, routingKey, message);
    }
}
