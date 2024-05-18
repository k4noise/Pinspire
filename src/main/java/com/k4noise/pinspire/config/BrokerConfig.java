package com.k4noise.pinspire.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BrokerConfig {

    @Bean
    public Queue likesQueue() {
        return new Queue("likes", false);
    }

    @Bean
    public Queue commentsQueue() {
        return new Queue("comments", false);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("notifications");
    }

    @Bean
    public Binding likesBinding() {
        return BindingBuilder.bind(likesQueue()).to(directExchange()).with("like");
    }

    @Bean
    public Binding commentsBinding() {
        return BindingBuilder.bind(commentsQueue()).to(directExchange()).with("comment");
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }
}