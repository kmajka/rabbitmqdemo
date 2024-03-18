package com.jms.example.rabbitmqdemo.fanout.consumer;

import com.jms.example.rabbitmqdemo.fanout.configuration.RabbitMQFanoutConfig;
import com.jms.example.rabbitmqdemo.topic.configuration.RabbitMQTopicConfig;
import com.jms.example.rabbitmqdemo.model.SimpleMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitFanoutSMSConsumer {

   @RabbitListener(queues = RabbitMQFanoutConfig.QUEUE_SMS_NAME, containerFactory = RabbitMQFanoutConfig.FANOUT_LISTENER_FACTORY)
    public void receiveMessage(SimpleMessage simpleMessage) {
        System.out.println("queue -> Received fanout message for sms consumer: " + simpleMessage);
    }
}
