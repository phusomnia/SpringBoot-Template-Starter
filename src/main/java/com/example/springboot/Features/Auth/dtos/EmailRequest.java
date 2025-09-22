package com.example.springboot.Features.Auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailRequest {
    public String to;
    public String subject;
    public String body;
}
