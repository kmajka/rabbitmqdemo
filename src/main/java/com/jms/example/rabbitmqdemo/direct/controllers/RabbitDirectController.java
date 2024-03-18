package com.jms.example.rabbitmqdemo.direct.controllers;

import com.jms.example.rabbitmqdemo.direct.configuration.RabbitMQDirectConfig;
import com.jms.example.rabbitmqdemo.direct.publisher.RabbitDirectMailPublisher;
import com.jms.example.rabbitmqdemo.model.SimpleMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitDirectController {

    private final RabbitDirectMailPublisher rabbitDirectPublisher;

    public RabbitDirectController(RabbitDirectMailPublisher rabbitDirectPublisher) {
        this.rabbitDirectPublisher = rabbitDirectPublisher;
    }

//  http://localhost:8080/send-message-direct-queue?body=this is some message
    @GetMapping("send-message-direct-queue")
    public String sendMessageQueue(@RequestParam("body") String body) {

        var message1 = new SimpleMessage("Tom", body + " send newMail to Queue using direct exchange [1].");
        rabbitDirectPublisher.sendMessageToQueue(RabbitMQDirectConfig.DIRECT_EXCHANGE_NAME, "newMail" , message1);

        var message2 = new SimpleMessage("Joan", body + " send oldMail to Queue using direct exchange [2].");
        rabbitDirectPublisher.sendMessageToQueue(RabbitMQDirectConfig.DIRECT_EXCHANGE_NAME, "oldMail" , message2);

        var message3 = new SimpleMessage("Max", body + " send direct to Queue as Direct [3].");
        rabbitDirectPublisher.sendMessageToQueue(RabbitMQDirectConfig.QUEUE_MAILBOX_NAME, message3);
        return "direct message: " + body;
    }
}
