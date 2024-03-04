package com.jms.example.rabbitmqdemo.controllers;

import com.jms.example.rabbitmqdemo.config.RabbitMQConfig;
import com.jms.example.rabbitmqdemo.model.SimpleMessage;
import com.jms.example.rabbitmqdemo.direct.publisher.RabbitDirectMailPublisher;
import com.jms.example.rabbitmqdemo.topic.publisher.RabbitTopicPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitController {

    private final RabbitDirectMailPublisher rabbitDirectPublisher;
    private final RabbitTopicPublisher rabbitTopicPublisher;

    public RabbitController(RabbitDirectMailPublisher rabbitDirectPublisher, RabbitTopicPublisher rabbitTopicPublisher) {
        this.rabbitDirectPublisher = rabbitDirectPublisher;
        this.rabbitTopicPublisher = rabbitTopicPublisher;
    }

//  http://localhost:8080/send-message-direct-queue?body=this is some message
    @GetMapping("send-message-direct-queue")
    public String sendMessageQueue(@RequestParam("body") String body) {

        var message1 = new SimpleMessage("Tom", body + " send newMail to Queue using direct exchange [1].");
        rabbitDirectPublisher.sendMessageToQueue(RabbitMQConfig.DIRECT_EXCHANGE_NAME, "newMail" , message1);

        var message2 = new SimpleMessage("Joan", body + " send oldMail to Queue using direct exchange [2].");
        rabbitDirectPublisher.sendMessageToQueue(RabbitMQConfig.DIRECT_EXCHANGE_NAME, "oldMail" , message2);

        var message3 = new SimpleMessage("Max", body + " send direct to Queue as Direct [3].");
        rabbitDirectPublisher.sendMessageToQueue(RabbitMQConfig.queueMailboxName, message3);
        return "direct message: " + body;
    }

//  http://localhost:8080/send-message-topic-queue?body=this is some message
    @GetMapping("send-message-topic-queue")
    public String sendMessageTopic(@RequestParam("body") String body) {

        var message1 = new SimpleMessage("server01", body + " send error to Queue as Topic [1].");
        rabbitTopicPublisher.sendMessageToQueue(RabbitMQConfig.TOPIC_EXCHANGE_NAME, "log.error", message1);

        var message2 = new SimpleMessage("server02", body + " send warning to Queue as Topic [2].");
        rabbitTopicPublisher.sendMessageToQueue(RabbitMQConfig.TOPIC_EXCHANGE_NAME, "log.warning", message2);

        var message3 = new SimpleMessage("server03", body + " send warning to Queue as Topic [2].");
        rabbitTopicPublisher.sendMessageToQueue(RabbitMQConfig.TOPIC_EXCHANGE_NAME, "console.warning", message3);

        return "topic massage: " + body;
    }
}
