package com.jms.example.rabbitmqdemo.fanout.controllers;

import com.jms.example.rabbitmqdemo.fanout.configuration.RabbitMQFanoutConfig;
import com.jms.example.rabbitmqdemo.fanout.publisher.RabbitFanoutPublisher;
import com.jms.example.rabbitmqdemo.model.SimpleMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitFanoutController {

    private final RabbitFanoutPublisher rabbitFanoutPublisher;

    public RabbitFanoutController(RabbitFanoutPublisher rabbitFanoutPublisher) {
        this.rabbitFanoutPublisher = rabbitFanoutPublisher;
    }

    //  http://localhost:8080/send-message-fanout-queue?body=this is some message
    @GetMapping("send-message-fanout-queue")
    public String sendMessageFanout(@RequestParam("body") String body) {

        var message1 = new SimpleMessage("server01", body + " send message to Queue as Fanout [1].");
        rabbitFanoutPublisher.sendMessageToQueue(RabbitMQFanoutConfig.FANOUT_EXCHANGE_NAME, message1);

        var message2 = new SimpleMessage("server02", body + " send message to Queue as Fanout [2].");
        rabbitFanoutPublisher.sendMessageToQueue(RabbitMQFanoutConfig.FANOUT_EXCHANGE_NAME, message2);

        var message3 = new SimpleMessage("server03", body + " send message to Queue as Fanout [2].");
        rabbitFanoutPublisher.sendMessageToQueue(RabbitMQFanoutConfig.FANOUT_EXCHANGE_NAME, message3);

        return "fanout massage: " + body;
    }
}
