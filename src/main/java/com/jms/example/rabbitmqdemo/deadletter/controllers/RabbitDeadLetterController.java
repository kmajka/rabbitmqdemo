package com.jms.example.rabbitmqdemo.deadletter.controllers;

import com.jms.example.rabbitmqdemo.deadletter.configuration.RabbitMQDeadLetterConfig;
import com.jms.example.rabbitmqdemo.deadletter.publisher.RabbitDeadLetterPublisher;
import com.jms.example.rabbitmqdemo.model.SimpleMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitDeadLetterController {

    private final RabbitDeadLetterPublisher rabbitDeadLetterPublisher;

    public RabbitDeadLetterController(RabbitDeadLetterPublisher rabbitDeadLetterPublisher) {
        this.rabbitDeadLetterPublisher = rabbitDeadLetterPublisher;
    }

    //  http://localhost:8080/send-message-deadletter-queue?body=this is some message
    @GetMapping("send-message-deadletter-queue")
    public String sendMessageDeadLetter(@RequestParam("body") String body) {

        var message1 = new SimpleMessage("server01", body + " send message to Queue as dead letter [1].");
        rabbitDeadLetterPublisher.sendMessageToQueue(RabbitMQDeadLetterConfig.TEMPORARY_EXCHANGE_NAME,
                RabbitMQDeadLetterConfig.TEMPORARY_QUEUE_NAME, message1);

        return "dead letter massage: " + body;
    }

    //  http://localhost:8080/send-message-deadletter-dlq-queue?body=this is some message
    @GetMapping("send-message-deadletter-dlq-queue")
    public String sendMessageDeadLetterDlq(@RequestParam("body") String body) {

        var message1 = new SimpleMessage("server01", body + " send message to Queue as dead letter [1].");
        rabbitDeadLetterPublisher.sendMessageToQueue2(RabbitMQDeadLetterConfig.TEMPORARY_EXCHANGE_NAME,
                RabbitMQDeadLetterConfig.TEMPORARY_QUEUE_NAME, message1);

        return "dead letter massage: " + body;
    }
}
