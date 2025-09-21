package com.example.springboot.Pun.Application.Features.AuthAPI.Auth.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailRequest {
    public String to;
    public String subject;
    public String body;
}
