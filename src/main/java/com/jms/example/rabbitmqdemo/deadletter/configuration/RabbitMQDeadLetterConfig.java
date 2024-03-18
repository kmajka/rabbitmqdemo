package com.jms.example.rabbitmqdemo.deadletter.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.jms.example.rabbitmqdemo.configuration.RabbitMQConfig.CONNECTION_FACTORY;
import static com.jms.example.rabbitmqdemo.configuration.RabbitMQConfig.CONVERTER_FOR_HEADER_EXCHANGE;

@Configuration
public class RabbitMQDeadLetterConfig {

    public static final String RABBIT_TEMPORARY_TEMPLATE = "rabbitTemporaryTemplate";
    public static final String TEMPORARY_LISTENER_FACTORY = "temporaryListenerFactory";
    public static final String TEMPORARY_QUEUE_NAME = "temporaryQueue";
    public static final String TEMPORARY_EXCHANGE_NAME = "temporaryExchange";
    public static final String DLX_DEAD_LETTER_QUEUE_NAME = "dlxDeadLetterQueue";
    public static final String DLX_DEAD_LETTER_EXCHANGE_NAME = "dlxDeadLetterExchange";

    @Bean(name = TEMPORARY_LISTENER_FACTORY)
    public SimpleRabbitListenerContainerFactory temporaryListenerFactory(@Qualifier(CONNECTION_FACTORY) ConnectionFactory connectionFactory,
                                                                          @Qualifier(CONVERTER_FOR_HEADER_EXCHANGE) MessageConverter converterForHeaderExchange) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        factory.setPrefetchCount(50);
        factory.setMessageConverter(converterForHeaderExchange);
        return factory;
    }

    @Bean(name = RABBIT_TEMPORARY_TEMPLATE)
    public RabbitTemplate rabbitTemporaryTemplate(@Qualifier(CONNECTION_FACTORY) ConnectionFactory connectionFactory,
                                                   @Qualifier(CONVERTER_FOR_HEADER_EXCHANGE) MessageConverter converterForHeaderExchange) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converterForHeaderExchange);
        rabbitTemplate.setExchange(TEMPORARY_EXCHANGE_NAME);
        return rabbitTemplate;
    }

    @Bean(name = "temporaryQueue")
    public Queue temporaryQueue() {
        return QueueBuilder.durable(TEMPORARY_QUEUE_NAME)
                .withArgument("x-dead-letter-exchange", DLX_DEAD_LETTER_EXCHANGE_NAME)
                .build();
    }

    @Bean
    public DirectExchange temporaryExchange() {
        return new DirectExchange(TEMPORARY_EXCHANGE_NAME);
    }

    @Bean
    public Binding temporaryQueueBinding(DirectExchange temporaryExchange, Queue temporaryQueue) {
        return BindingBuilder.bind(temporaryQueue).to(temporaryExchange).with(TEMPORARY_QUEUE_NAME);
    }

    @Bean(name = "dlxDeadLetterQueue")
    public Queue dlxDeadLetterQueue() {
        return QueueBuilder.durable(DLX_DEAD_LETTER_QUEUE_NAME).build();
    }

    @Bean(name = "dlxDeadLetterExchange")
    public FanoutExchange dlxDeadLetterExchange() {
        return new FanoutExchange(DLX_DEAD_LETTER_EXCHANGE_NAME);
    }

    @Bean
    public Binding deadLetterBinding(FanoutExchange dlxDeadLetterExchange, Queue dlxDeadLetterQueue) {
        return BindingBuilder.bind(dlxDeadLetterQueue).to(dlxDeadLetterExchange);
    }

}

