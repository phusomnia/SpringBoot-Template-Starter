package com.example.springboot.Pun.Application.Features.RealTimeAPI.Chat;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "chat")
@Slf4j
public class ChatController 
{
    @MessageMapping("/send")
    @SendTo("/topic/message")
    public ChatHub chat(@Payload ChatHub message)
    {
        var logInfo = String.format("%s - %s", message.getFrom(), message.getText());
        log.info(logInfo);
        return message;
    }
    
}
