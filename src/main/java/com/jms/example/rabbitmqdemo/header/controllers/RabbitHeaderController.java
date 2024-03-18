package com.jms.example.rabbitmqdemo.header.controllers;

import com.jms.example.rabbitmqdemo.header.configuration.RabbitMQHeaderConfig;
import com.jms.example.rabbitmqdemo.header.publisher.RabbitHeaderPublisher;
import com.jms.example.rabbitmqdemo.model.SimpleMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
public class RabbitHeaderController {

    private final RabbitHeaderPublisher rabbitHeaderPublisher;

    public RabbitHeaderController(RabbitHeaderPublisher rabbitHeaderPublisher) {
        this.rabbitHeaderPublisher = rabbitHeaderPublisher;
    }

    //  http://localhost:8080/send-message-headers-queue?body=this is some message
    @GetMapping("send-message-headers-queue")
    public String sendMessageHeader(@RequestParam("body") String body) throws IOException {

        var message1 = new SimpleMessage("server01", body + " send message to Queue as headers [1].");
        final Map<String, Object> headers = Map.of("email-type", "text");
        rabbitHeaderPublisher.sendMessageToQueue(RabbitMQHeaderConfig.HEADERS_EXCHANGE_NAME, "",
                message1, headers);

        var message2 = new SimpleMessage("server01", body + " send message to Queue as headers [2].");

        final Map<String, Object> headers2 = Map.of("email-type", "text2", "mime-type", "application2");
        rabbitHeaderPublisher.sendMessageToQueue(RabbitMQHeaderConfig.HEADERS_EXCHANGE_NAME, "",
                message2, headers2);

        return "headers massage: " + body;
    }

}
