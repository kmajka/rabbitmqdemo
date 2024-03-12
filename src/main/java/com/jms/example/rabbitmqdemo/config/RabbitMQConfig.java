package com.jms.example.rabbitmqdemo.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;
import java.util.Map;

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

    public static final String connectionFactory = "connectionFactory";

    @Bean(name = connectionFactory)
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);

        return connectionFactory;
    }

    @Bean
    public Connection connection(ConnectionFactory connectionFactory) {
        Connection connection = connectionFactory.createConnection();
        connection.createChannel(true);
        return connection;
    }

    public static final String myDirectListenerFactory = "myDirectListenerFactory";

    @Bean(name = myDirectListenerFactory)
    public SimpleRabbitListenerContainerFactory myDirectListenerFactory(ConnectionFactory connectionFactory,
                                                                        Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        factory.setPrefetchCount(50);
        factory.setMessageConverter(jackson2JsonMessageConverter);
        return factory;
    }

    public static final String myTopicListenerFactory = "myTopicListenerFactory";

    @Bean(name = myTopicListenerFactory)
    public SimpleRabbitListenerContainerFactory myTopicListenerFactory(ConnectionFactory connectionFactory,
                                                                       Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        factory.setPrefetchCount(50);
        factory.setMessageConverter(jackson2JsonMessageConverter);
        return factory;
    }

    public static final String myFanoutListenerFactory = "myFanoutListenerFactory";

    @Bean(name = myFanoutListenerFactory)
    public SimpleRabbitListenerContainerFactory myFanoutListenerFactory(ConnectionFactory connectionFactory,
                                                                        Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        factory.setPrefetchCount(50);
        factory.setMessageConverter(jackson2JsonMessageConverter);
        return factory;
    }

    public static final String myHeaderListenerFactory = "myHeaderListenerFactory";

    @Bean(name = myHeaderListenerFactory)
    public SimpleRabbitListenerContainerFactory myHeaderListenerFactory(ConnectionFactory connectionFactory,
                                                                        MessageConverter converterForHeaderExchange) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        factory.setPrefetchCount(50);
        factory.setMessageConverter(converterForHeaderExchange);
        return factory;
    }

    @Bean(name = "converterForHeaderExchange")
    public SimpleMessageConverter converterForHeaderExchange() {
        SimpleMessageConverter converter = new SimpleMessageConverter();
        converter.setAllowedListPatterns(List.of("com.jms.example.rabbitmqdemo.model.SimpleMessage"));
        return converter;
    }

    @Bean(name = "jackson2JsonMessageConverter")
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        return converter;
    }

    public static final String rabbitDirectTemplate = "rabbitDirectTemplate";

    @Bean(name = rabbitDirectTemplate)
    @Primary
    public RabbitTemplate rabbitDirectTemplate(ConnectionFactory connectionFactory,
                                               Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
        return rabbitTemplate;
    }

    public static final String rabbitTopicTemplate = "rabbitTopicTemplate";

    @Bean(name = rabbitTopicTemplate)
    public RabbitTemplate rabbitTopicTemplate(ConnectionFactory connectionFactory,
                                              Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
        rabbitTemplate.setExchange(TOPIC_EXCHANGE_NAME);
        return rabbitTemplate;
    }

    public static final String rabbitFanoutTemplate = "rabbitFanoutTemplate";

    @Bean(name = rabbitFanoutTemplate)
    public RabbitTemplate rabbitFanoutTemplate(ConnectionFactory connectionFactory,
                                               Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
        rabbitTemplate.setExchange(FANOUT_EXCHANGE_NAME);
        return rabbitTemplate;
    }

    public static final String rabbitHeaderTemplate = "rabbitHeaderTemplate";

    @Bean(name = rabbitHeaderTemplate)
    public RabbitTemplate rabbitHeaderTemplate(ConnectionFactory connectionFactory,
                                               MessageConverter converterForHeaderExchange) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converterForHeaderExchange);
        rabbitTemplate.setExchange(HEADERS_EXCHANGE_NAME);
        return rabbitTemplate;
    }


    //start configuration for TOPIC exchange
    public static final String queueWarningsName = "queue.warnings";

    @Bean
    public Queue queueWarnings() {
        return new Queue(queueWarningsName);
    }

    public static final String queueErrorsName = "queue.errors";

    @Bean
    public Queue queueErrors() {
        return new Queue(queueErrorsName);
    }

    public static final String queueLogsName = "queue.logs";

    @Bean
    public Queue queueLogs() {
        return new Queue(queueLogsName);
    }

    public static final String TOPIC_EXCHANGE_NAME = "messages.topic";

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
    //end configuration for TOPIC exchange


    //start configuration for DIRECT exchange
    public static final String queueMailboxName = "queue.mailbox";

    @Bean
    public Queue queueMailbox() {
        return new Queue(queueMailboxName);
    }

    public static final String DIRECT_EXCHANGE_NAME = "messages.direct";

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
    //end configuration for DIRECT exchange

    //start configuration for FANOUT exchange
    public static final String queueSMSName = "queue.sms";

    @Bean
    public Queue queueSMS() {
        return new Queue(queueSMSName);
    }

    public static final String queueEmailName = "queue.email";

    @Bean
    public Queue queueEmail() {
        return new Queue(queueEmailName);
    }

    public static final String queueSocialMediaNetworkName = "queue.socialmedianetwork";

    @Bean
    public Queue queueSocialMediaNetwork() {
        return new Queue(queueSocialMediaNetworkName);
    }

    public static final String FANOUT_EXCHANGE_NAME = "messages.fanout";

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

    //end configuration for FANOUT exchange


    //start configuration for HEADER exchange

    public static final String queueEmailWithHeaderName = "queue.emailwithheader";

    @Bean
    public Queue queueEmailWithHeader() {
        return new Queue(queueEmailWithHeaderName);
    }

    public static final String HEADERS_EXCHANGE_NAME = "messages.headers";

    @Bean
    public HeadersExchange headersExchange() {
        return new HeadersExchange(HEADERS_EXCHANGE_NAME);
    }

    @Bean
    public Declarables bindingHeadersForQueueEmailName(HeadersExchange headersExchange, Queue queueEmailWithHeader) {
        return new Declarables(BindingBuilder.bind(queueEmailWithHeader).to(headersExchange)
                .whereAny(Map.of("email-type", "text")).match());
    }

    //end configuration for HEADER exchange
}

