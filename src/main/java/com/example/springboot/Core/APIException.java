package com.example.springboot.Core;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class APIException extends RuntimeException {
    private int status;
    private String message;
    
    public APIException(int status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }
}
