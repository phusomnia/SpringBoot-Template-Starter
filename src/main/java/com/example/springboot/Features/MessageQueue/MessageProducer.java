package com.example.springboot.Features.MessageQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer 
{
    private static final Logger log = LoggerFactory.getLogger(MessageProducer.class);
    @Autowired private RabbitTemplate rabbitTemplate;
    
    public void send(Message message)
    {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                message
        );
        log.info("Sent: " + message);
    }
}
