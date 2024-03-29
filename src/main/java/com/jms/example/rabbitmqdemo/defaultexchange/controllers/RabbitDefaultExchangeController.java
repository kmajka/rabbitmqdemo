package com.jms.example.rabbitmqdemo.defaultexchange.controllers;

import com.jms.example.rabbitmqdemo.defaultexchange.configuration.RabbitMQDefaultExchangeConfig;
import com.jms.example.rabbitmqdemo.defaultexchange.publisher.RabbitDefaultExchangePublisher;
import com.jms.example.rabbitmqdemo.model.SimpleMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitDefaultExchangeController {

    private final RabbitDefaultExchangePublisher rabbitDefaultExchangePublisher;

    public RabbitDefaultExchangeController(RabbitDefaultExchangePublisher rabbitDefaultExchangePublisher) {
        this.rabbitDefaultExchangePublisher = rabbitDefaultExchangePublisher;
    }

//  http://localhost:8080/send-message-defaultexchange-queue?body=this is some message
    @GetMapping("send-message-defaultexchange-queue")
    public String sendMessageQueue(@RequestParam("body") String body) {

        var message = new SimpleMessage("Joe", body + " send direct to Queue as Default Exchange.");
        rabbitDefaultExchangePublisher.sendMessageToQueue(RabbitMQDefaultExchangeConfig.QUEUE_DEFAULTEXCHANGE_NAME, message);
        return "default exchange message: " + body;
    }
}
