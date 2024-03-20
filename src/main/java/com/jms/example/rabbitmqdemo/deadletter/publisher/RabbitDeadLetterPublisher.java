package com.jms.example.rabbitmqdemo.deadletter.publisher;

import com.jms.example.rabbitmqdemo.model.SimpleMessage;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import static com.jms.example.rabbitmqdemo.configuration.RabbitMQConfig.CONVERTER_FOR_HEADER_EXCHANGE;
import static com.jms.example.rabbitmqdemo.deadletter.configuration.RabbitMQDeadLetterConfig.DLQ_DEAD_LETTER_QUEUE_NAME;
import static com.jms.example.rabbitmqdemo.deadletter.configuration.RabbitMQDeadLetterConfig.RABBIT_TEMPORARY_TEMPLATE;

@Service
public class RabbitDeadLetterPublisher {

    private final RabbitTemplate rabbitTemplate;

    private final MessageConverter converterForHeaderExchange;

    public RabbitDeadLetterPublisher(@Qualifier(RABBIT_TEMPORARY_TEMPLATE) RabbitTemplate rabbitTemplate,
                                     @Qualifier(CONVERTER_FOR_HEADER_EXCHANGE) MessageConverter converterForHeaderExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.converterForHeaderExchange = converterForHeaderExchange;
    }

    public void sendMessageToQueue(String deadLetterExchangeName, String key, SimpleMessage simpleMessage) {

        MessageProperties messageProperties = new MessageProperties();
        Message message = converterForHeaderExchange.toMessage(simpleMessage, messageProperties);

        this.rabbitTemplate.convertAndSend(deadLetterExchangeName, key, message);
    }

    public void sendMessageToQueue2(String deadLetterExchangeName, String key, SimpleMessage simpleMessage) {

        MessageProperties messageProperties = new MessageProperties();
        Message message = converterForHeaderExchange.toMessage(simpleMessage, messageProperties);

        this.rabbitTemplate.convertAndSend(deadLetterExchangeName, key, message);
    }
}
