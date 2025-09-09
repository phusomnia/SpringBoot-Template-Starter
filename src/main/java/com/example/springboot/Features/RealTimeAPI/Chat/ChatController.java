package com.example.springboot.RealTimeAPI.Chat;

import com.example.springboot.Core.CustomJson;
import com.example.springboot.Features.RealTimeAPI.Chat.ChatHub;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
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
        var loginfo = String.format("%s - %s", message.getFrom(), message.getText());
        log.info(loginfo);
        return message;
    }
    
}
