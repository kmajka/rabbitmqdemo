package com.jms.example.rabbitmqdemo.topic.controllers;

import com.jms.example.rabbitmqdemo.model.SimpleMessage;
import com.jms.example.rabbitmqdemo.topic.configuration.RabbitMQTopicConfig;
import com.jms.example.rabbitmqdemo.topic.publisher.RabbitTopicPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitTopicController {

    private final RabbitTopicPublisher rabbitTopicPublisher;

    public RabbitTopicController(RabbitTopicPublisher rabbitTopicPublisher) {
        this.rabbitTopicPublisher = rabbitTopicPublisher;
    }

//  http://localhost:8080/send-message-topic-queue?body=this is some message
    @GetMapping("send-message-topic-queue")
    public String sendMessageTopic(@RequestParam("body") String body) {

        var message1 = new SimpleMessage("server01", body + " send error to Queue as Topic [1].");
        rabbitTopicPublisher.sendMessageToQueue(RabbitMQTopicConfig.TOPIC_EXCHANGE_NAME, "log.error", message1);

        var message2 = new SimpleMessage("server02", body + " send warning to Queue as Topic [2].");
        rabbitTopicPublisher.sendMessageToQueue(RabbitMQTopicConfig.TOPIC_EXCHANGE_NAME, "log.warning", message2);

        var message3 = new SimpleMessage("server03", body + " send warning to Queue as Topic [2].");
        rabbitTopicPublisher.sendMessageToQueue(RabbitMQTopicConfig.TOPIC_EXCHANGE_NAME, "console.warning", message3);

        return "topic massage: " + body;
    }

}
