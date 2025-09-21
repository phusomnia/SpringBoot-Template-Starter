package com.example.springboot.Pun.Application.Features.RealTimeAPI.Chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Data
public class ChatHub {
    private String from;
    private String text;
}
