package com.jms.example.rabbitmqdemo.configuration;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.virtualHost}")
    private String virtualHost;

    public static final String CONNECTION_FACTORY = "connectionFactory";
    public static final String CONVERTER_FOR_HEADER_EXCHANGE = "converterForHeaderExchange";

    public static final String JACKSON_TO_JSON_MESSAGE_CONVERTER = "jackson2JsonMessageConverter";

    @Bean(name = CONNECTION_FACTORY)
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);

        return connectionFactory;
    }

    @Bean(name = CONVERTER_FOR_HEADER_EXCHANGE)
    public SimpleMessageConverter converterForHeaderExchange() {
        SimpleMessageConverter converter = new SimpleMessageConverter();
        converter.setAllowedListPatterns(List.of("com.jms.example.rabbitmqdemo.model.SimpleMessage"));
        return converter;
    }


    @Bean
    public Connection connection(ConnectionFactory connectionFactory) {
        Connection connection = connectionFactory.createConnection();
        connection.createChannel(true);
        return connection;
    }

    @Bean(name = JACKSON_TO_JSON_MESSAGE_CONVERTER)
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        return converter;
    }
}

