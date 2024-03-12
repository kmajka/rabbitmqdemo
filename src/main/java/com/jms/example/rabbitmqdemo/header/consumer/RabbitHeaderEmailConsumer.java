package com.jms.example.rabbitmqdemo.header.consumer;

import com.jms.example.rabbitmqdemo.config.RabbitMQConfig;
import com.jms.example.rabbitmqdemo.model.SimpleMessage;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class RabbitHeaderEmailConsumer {

    @Qualifier("converterForHeaderExchange")
    private final MessageConverter converterForHeaderExchange;

    public RabbitHeaderEmailConsumer(MessageConverter converterForHeaderExchange) {
        this.converterForHeaderExchange = converterForHeaderExchange;
    }

    @RabbitListener(queues = RabbitMQConfig.queueEmailWithHeaderName, containerFactory = RabbitMQConfig.myHeaderListenerFactory)
    public void receiveMessage(Message message) {

       //for String we can use MessageConverter
       //MessageConverter messageConverter = new SimpleMessageConverter();
       //in other hand we see:
       //Caused by: java.lang.SecurityException: Attempt to deserialize unauthorized class
       // com.jms.example.rabbitmqdemo.model.SimpleMessage; add allowed class name patterns
       // to the message converter or, if you trust the message originator, set environment
       // variable 'SPRING_AMQP_DESERIALIZATION_TRUST_ALL' or system property 'spring.amqp.deserialization.trust.all' to true
       SimpleMessage message2 = (SimpleMessage)converterForHeaderExchange.fromMessage(message);

       Object headerValue = message.getMessageProperties().getHeaders().get("email-type");
       System.out.println("Received message: " + message2);
       System.out.println("email-type: " + headerValue);

        System.out.println("queue -> Received header message for 'email bin' consumer: " + message2);
    }
}
