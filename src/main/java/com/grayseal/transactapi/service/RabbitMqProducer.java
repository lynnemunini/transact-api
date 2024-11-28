package com.grayseal.transactapi.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.template.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.template.routingkey}")
    private String routingKey;

    public RabbitMqProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendTransactionMessage(String transactionDetails) {
        rabbitTemplate.convertAndSend(exchange, routingKey, transactionDetails);
        System.out.println("Message sent to RabbitMQ: " + transactionDetails);
    }
}
