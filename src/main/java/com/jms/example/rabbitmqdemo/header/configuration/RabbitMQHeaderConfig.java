package com.jms.example.rabbitmqdemo.header.configuration;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

import static com.jms.example.rabbitmqdemo.configuration.RabbitMQConfig.CONNECTION_FACTORY;
import static com.jms.example.rabbitmqdemo.configuration.RabbitMQConfig.CONVERTER_FOR_HEADER_EXCHANGE;

@Configuration
public class RabbitMQHeaderConfig {
    public static final String HEADER_LISTENER_FACTORY = "headerListenerFactory";
    public static final String RABBIT_HEADER_TEMPLATE = "rabbitHeaderTemplate";
    public static final String QUEUE_EMAIL_WITH_HEADER_NAME = "queue.emailwithheader";
    public static final String HEADERS_EXCHANGE_NAME = "messages.headers";

    @Bean(name = HEADER_LISTENER_FACTORY)
    public SimpleRabbitListenerContainerFactory headerListenerFactory(@Qualifier(CONNECTION_FACTORY) ConnectionFactory connectionFactory,
                                                                      @Qualifier(CONVERTER_FOR_HEADER_EXCHANGE) MessageConverter converterForHeaderExchange) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        factory.setPrefetchCount(50);
        factory.setMessageConverter(converterForHeaderExchange);
        return factory;
    }



    @Bean(name = RABBIT_HEADER_TEMPLATE)
    public RabbitTemplate rabbitHeaderTemplate(@Qualifier(CONNECTION_FACTORY)ConnectionFactory connectionFactory,
                                               @Qualifier(CONVERTER_FOR_HEADER_EXCHANGE) MessageConverter converterForHeaderExchange) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converterForHeaderExchange);
        rabbitTemplate.setExchange(HEADERS_EXCHANGE_NAME);
        return rabbitTemplate;
    }

    @Bean
    public Queue queueEmailWithHeader() {
        return new Queue(QUEUE_EMAIL_WITH_HEADER_NAME);
    }

    @Bean
    public HeadersExchange headersExchange() {
        return new HeadersExchange(HEADERS_EXCHANGE_NAME);
    }

    @Bean
    public Declarables bindingHeadersForQueueEmailName(HeadersExchange headersExchange, Queue queueEmailWithHeader) {
        return new Declarables(BindingBuilder.bind(queueEmailWithHeader).to(headersExchange)
                .whereAny(Map.of("email-type", "text")).match());
    }

    @Bean
    public Declarables bindingHeadersForQueueEmailName2(HeadersExchange headersExchange, Queue queueEmailWithHeader) {
        return new Declarables(BindingBuilder.bind(queueEmailWithHeader).to(headersExchange)
                .whereAll(Map.of("email-type", "text2",
                                 "mime-type", "application")).match());
    }
}

