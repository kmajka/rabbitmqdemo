package com.jms.example.rabbitmqdemo.defaultexchange.configuration;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.jms.example.rabbitmqdemo.configuration.RabbitMQConfig.CONNECTION_FACTORY;
import static com.jms.example.rabbitmqdemo.configuration.RabbitMQConfig.JACKSON_TO_JSON_MESSAGE_CONVERTER;

@Configuration
public class RabbitMQDefaultExchangeConfig {

    public static final String RABBIT_DEFAULTEXCHANGE_TEMPLATE = "rabbitDefaultExchangeTemplate";
    public static final String DEFAULTEXCHANGE_LISTENER_FACTORY = "defaultExchangeListenerFactory";
    public static final String QUEUE_DEFAULTEXCHANGE_NAME = "queue.defaultexchange";

    @Bean(name = DEFAULTEXCHANGE_LISTENER_FACTORY)
    public SimpleRabbitListenerContainerFactory defaultExchangeListenerFactory(@Qualifier(CONNECTION_FACTORY) ConnectionFactory connectionFactory,
                                                                               @Qualifier(JACKSON_TO_JSON_MESSAGE_CONVERTER) Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        factory.setPrefetchCount(50);
        factory.setMessageConverter(jackson2JsonMessageConverter);
        return factory;
    }

    @Bean(name = RABBIT_DEFAULTEXCHANGE_TEMPLATE)
    public RabbitTemplate rabbitDefaultExchangeTemplate(@Qualifier(CONNECTION_FACTORY) ConnectionFactory connectionFactory,
                                                        @Qualifier(JACKSON_TO_JSON_MESSAGE_CONVERTER) Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
        return rabbitTemplate;
    }

    @Bean
    public Queue queueDefaultExchange() {
        return new Queue(QUEUE_DEFAULTEXCHANGE_NAME);
    }
    
}



