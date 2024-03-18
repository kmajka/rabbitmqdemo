package com.jms.example.rabbitmqdemo.topic.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
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
public class RabbitMQTopicConfig {

    public static final String TOPIC_LISTENER_FACTORY = "topicListenerFactory";
    public static final String RABBIT_TOPIC_TEMPLATE = "rabbitTopicTemplate";
    public static final String QUEUE_WARNINGS_NAME = "queue.warnings";
    public static final String QUEUE_ERRORS_NAME = "queue.errors";
    public static final String QUEUE_LOGS_NAME = "queue.logs";
    public static final String TOPIC_EXCHANGE_NAME = "messages.topic";

    @Bean(name = TOPIC_LISTENER_FACTORY)
    public SimpleRabbitListenerContainerFactory topicListenerFactory(@Qualifier(CONNECTION_FACTORY) ConnectionFactory connectionFactory,
                                                                     @Qualifier(JACKSON_TO_JSON_MESSAGE_CONVERTER)Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        factory.setPrefetchCount(50);
        factory.setMessageConverter(jackson2JsonMessageConverter);
        return factory;
    }

    @Bean(name = RABBIT_TOPIC_TEMPLATE)
    public RabbitTemplate rabbitTopicTemplate(@Qualifier(CONNECTION_FACTORY) ConnectionFactory connectionFactory,
                                              @Qualifier(JACKSON_TO_JSON_MESSAGE_CONVERTER) Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
        rabbitTemplate.setExchange(TOPIC_EXCHANGE_NAME);
        return rabbitTemplate;
    }

    @Bean
    public Queue queueWarnings() {
        return new Queue(QUEUE_WARNINGS_NAME);
    }

    @Bean
    public Queue queueErrors() {
        return new Queue(QUEUE_ERRORS_NAME);
    }

    @Bean
    public Queue queueLogs() {
        return new Queue(QUEUE_LOGS_NAME);
    }
    
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }

    @Bean
    public Binding bindingTopicForQueueWarnings(TopicExchange topicExchange, Queue queueWarnings) {
        return BindingBuilder.bind(queueWarnings).to(topicExchange).with("*.warning");
    }

    @Bean
    public Binding bindingTopicForQueueErrors(TopicExchange topicExchange, Queue queueErrors) {
        return BindingBuilder.bind(queueErrors).to(topicExchange).with("log.error");
    }

    @Bean
    public Binding bindingTopicForQueueLogs(TopicExchange topicExchange, Queue queueLogs) {
        return BindingBuilder.bind(queueLogs).to(topicExchange).with("log.*");
    }
}

