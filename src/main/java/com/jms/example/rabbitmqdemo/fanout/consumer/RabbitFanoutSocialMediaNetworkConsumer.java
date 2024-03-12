package com.jms.example.rabbitmqdemo.fanout.consumer;

import com.jms.example.rabbitmqdemo.config.RabbitMQConfig;
import com.jms.example.rabbitmqdemo.model.SimpleMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitFanoutSocialMediaNetworkConsumer {

    @RabbitListener(queues = RabbitMQConfig.queueSocialMediaNetworkName, containerFactory = RabbitMQConfig.myFanoutListenerFactory)
    public void receiveMessage(SimpleMessage simpleMessage) {
        System.out.println("queue -> Received fanout message for social media network consumer: " + simpleMessage);
    }
}
