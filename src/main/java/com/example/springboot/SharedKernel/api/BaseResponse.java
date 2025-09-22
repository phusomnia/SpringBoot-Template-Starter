package com.example.springboot.SharedKernel.api;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BaseResponse {
    private int statusCode;
    private String message;
    
    public BaseResponse(){}
    public BaseResponse(int statusCode,String message){
        this.statusCode = statusCode;
        this.message = message;
    }
}
