package com.jms.example.rabbitmqdemo.direct.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static com.jms.example.rabbitmqdemo.configuration.RabbitMQConfig.CONNECTION_FACTORY;
import static com.jms.example.rabbitmqdemo.configuration.RabbitMQConfig.JACKSON_TO_JSON_MESSAGE_CONVERTER;

@Configuration
public class RabbitMQDirectConfig {

    public static final String RABBIT_DIRECT_TEMPLATE = "rabbitDirectTemplate";
    public static final String DIRECT_LISTENER_FACTORY = "directListenerFactory";
    public static final String QUEUE_MAILBOX_NAME = "queue.mailbox";
    public static final String DIRECT_EXCHANGE_NAME = "messages.direct";

    @Bean(name = DIRECT_LISTENER_FACTORY)
    public SimpleRabbitListenerContainerFactory directListenerFactory(@Qualifier(CONNECTION_FACTORY) ConnectionFactory connectionFactory,
                                                                      @Qualifier(JACKSON_TO_JSON_MESSAGE_CONVERTER) Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        factory.setPrefetchCount(50);
        factory.setMessageConverter(jackson2JsonMessageConverter);
        return factory;
    }

    @Bean(name = RABBIT_DIRECT_TEMPLATE)
    public RabbitTemplate rabbitDirectTemplate(@Qualifier(CONNECTION_FACTORY)ConnectionFactory connectionFactory,
                                               @Qualifier(JACKSON_TO_JSON_MESSAGE_CONVERTER) Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
        return rabbitTemplate;
    }

    @Bean
    public Queue queueMailbox() {
        return new Queue(QUEUE_MAILBOX_NAME);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE_NAME);
    }

    @Bean
    public Binding bindingDirectForQueueNewMail(DirectExchange directExchange, Queue queueMailbox) {
        return BindingBuilder.bind(queueMailbox).to(directExchange).with("newMail");
    }

    @Bean
    public Binding bindingDirectForQueueOldMail(DirectExchange directExchange, Queue queueMailbox) {
        return BindingBuilder.bind(queueMailbox).to(directExchange).with("oldMail");
    }

}

