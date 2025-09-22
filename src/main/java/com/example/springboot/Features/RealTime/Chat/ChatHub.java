package com.example.springboot.Features.RealTime.Chat;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ChatHub {
    private String from;
    private String text;
}
