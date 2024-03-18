package com.jms.example.rabbitmqdemo.fanout.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
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
public class RabbitMQFanoutConfig {

    public static final String FANOUT_LISTENER_FACTORY = "fanoutListenerFactory";
    public static final String RABBIT_FANOUT_TEMPLATE = "rabbitFanoutTemplate";
    public static final String QUEUE_SMS_NAME = "queue.sms";
    public static final String QUEUE_EMAIL_NAME = "queue.email";
    public static final String QUEUE_SOCIAL_MEDIA_NETWORK_NAME = "queue.socialmedianetwork";
    public static final String FANOUT_EXCHANGE_NAME = "messages.fanout";

    @Bean(name = FANOUT_LISTENER_FACTORY)
    public SimpleRabbitListenerContainerFactory fanoutListenerFactory(@Qualifier(CONNECTION_FACTORY) ConnectionFactory connectionFactory,
                                                                      @Qualifier(JACKSON_TO_JSON_MESSAGE_CONVERTER) Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        factory.setPrefetchCount(50);
        factory.setMessageConverter(jackson2JsonMessageConverter);
        return factory;
    }

    @Bean(name = RABBIT_FANOUT_TEMPLATE)
    public RabbitTemplate rabbitFanoutTemplate(@Qualifier(CONNECTION_FACTORY) ConnectionFactory connectionFactory,
                                               @Qualifier(JACKSON_TO_JSON_MESSAGE_CONVERTER) Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
        rabbitTemplate.setExchange(FANOUT_EXCHANGE_NAME);
        return rabbitTemplate;
    }

    @Bean
    public Queue queueSMS() {
        return new Queue(QUEUE_SMS_NAME);
    }

    @Bean
    public Queue queueEmail() {
        return new Queue(QUEUE_EMAIL_NAME);
    }

    @Bean
    public Queue queueSocialMediaNetwork() {
        return new Queue(QUEUE_SOCIAL_MEDIA_NETWORK_NAME);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE_NAME);
    }

    @Bean
    public Binding bindingFanoutForQueueSMS(FanoutExchange fanoutExchange, Queue queueSMS) {
        return BindingBuilder.bind(queueSMS).to(fanoutExchange);
    }

    @Bean
    public Binding bindingFanoutForQueueEmailName(FanoutExchange fanoutExchange, Queue queueEmail) {
        return BindingBuilder.bind(queueEmail).to(fanoutExchange);
    }

    @Bean
    public Binding bindingFanoutForQueueSocialMediaNetworkName(FanoutExchange fanoutExchange, Queue queueSocialMediaNetwork) {
        return BindingBuilder.bind(queueSocialMediaNetwork).to(fanoutExchange);
    }
}

