package com.example.springboot.Pun.Application.Features.AuthAPI.Auth.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
@Jacksonized
public class Payload {
    private String hash;
    private String salt;
    private Date createdAt;
    private int triesLeft;
    
    public void consumeTry()
    {
        this.triesLeft--;
    }
    
    public Boolean isIOutOfTries()
    {
        return triesLeft <= 0;
    }
}
