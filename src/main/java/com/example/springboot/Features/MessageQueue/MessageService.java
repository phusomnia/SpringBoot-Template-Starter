package com.example.springboot.Features.MessageQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MessageService 
{
    @Autowired private MessageProducer messageProducer;
    
    public Message sendMessage(String content)
    {
        Message message = new Message(
                UUID.randomUUID().toString(),
                content
        );
        messageProducer.send(message);
        return message;
    }
}
