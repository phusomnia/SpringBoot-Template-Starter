package com.example.springboot.Pun.Application.Features.MessageQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class MessageConsumer 
{
    private static final Logger log = LoggerFactory.getLogger(MessageConsumer.class);

    @RabbitListener()
    public void recieve(Message message)
    {
        log.info("Recieved: " + message);
    }
}
