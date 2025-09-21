package com.example.springboot.Pun.Application.Features.MessageQueue;

import com.example.springboot.Pun.Domain.Core.APIResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "MessageQueue")
@RequestMapping("/api/v1/message-queue")
public class MessageController 
{
    @Autowired  private MessageService messageService;
    
    @PostMapping
    public ResponseEntity<?> publish(@RequestParam String content)
    {
        var response = messageService.sendMessage(content);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
