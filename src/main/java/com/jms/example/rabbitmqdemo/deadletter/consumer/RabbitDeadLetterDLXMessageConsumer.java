package com.jms.example.rabbitmqdemo.deadletter.consumer;

import com.jms.example.rabbitmqdemo.deadletter.configuration.RabbitMQDeadLetterConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static com.jms.example.rabbitmqdemo.configuration.RabbitMQConfig.CONVERTER_FOR_HEADER_EXCHANGE;

@Component
public class RabbitDeadLetterDLXMessageConsumer {

    private final MessageConverter converterForHeaderExchange;

    public RabbitDeadLetterDLXMessageConsumer(@Qualifier(CONVERTER_FOR_HEADER_EXCHANGE) MessageConverter converterForHeaderExchange) {
        this.converterForHeaderExchange = converterForHeaderExchange;
    }

    @RabbitListener(queues = RabbitMQDeadLetterConfig.DLX_DEAD_LETTER_QUEUE_NAME, containerFactory = RabbitMQDeadLetterConfig.TEMPORARY_LISTENER_FACTORY)
    public void processMessage(Message message) {
        System.out.println("queue -> Received dead letter DLX message for consumer: " + message);
    }
}
