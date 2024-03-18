package com.jms.example.rabbitmqdemo.header.consumer;

import com.jms.example.rabbitmqdemo.header.configuration.RabbitMQHeaderConfig;
import com.jms.example.rabbitmqdemo.topic.configuration.RabbitMQTopicConfig;
import com.jms.example.rabbitmqdemo.model.SimpleMessage;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static com.jms.example.rabbitmqdemo.configuration.RabbitMQConfig.CONVERTER_FOR_HEADER_EXCHANGE;

@Component
public class RabbitHeaderEmailConsumer {

    private final MessageConverter converterForHeaderExchange;

    public RabbitHeaderEmailConsumer(@Qualifier(CONVERTER_FOR_HEADER_EXCHANGE) MessageConverter converterForHeaderExchange) {
        this.converterForHeaderExchange = converterForHeaderExchange;
    }

    @RabbitListener(queues = RabbitMQHeaderConfig.QUEUE_EMAIL_WITH_HEADER_NAME, containerFactory = RabbitMQHeaderConfig.HEADER_LISTENER_FACTORY)
    public void receiveMessage(Message message) {

        //for String we can use MessageConverter
        //MessageConverter messageConverter = new SimpleMessageConverter();
        //in other hand we see:
        //Caused by: java.lang.SecurityException: Attempt to deserialize unauthorized class
        // com.jms.example.rabbitmqdemo.model.SimpleMessage; add allowed class name patterns
        // to the message converter or, if you trust the message originator, set environment
        // variable 'SPRING_AMQP_DESERIALIZATION_TRUST_ALL' or system property 'spring.amqp.deserialization.trust.all' to true
        SimpleMessage message2 = (SimpleMessage)converterForHeaderExchange.fromMessage(message);
        System.out.println("Received message: " + message2);
        Object headerValue = message.getMessageProperties().getHeaders().get("email-type");

        System.out.println("email-type: " + headerValue);
        Object headerValue2 = message.getMessageProperties().getHeaders().get("mime-type");
        System.out.println("mime-type: " + headerValue2);

        System.out.println("queue -> Received header message for 'email bin' consumer: " + message2);
    }
}
