package com.example.springboot.Core;

public class BaseResponse {
    public int statusCode;
    public String message;
    
    public BaseResponse(){}
    public BaseResponse(int statusCode,String message){
        this.statusCode = statusCode;
        this.message = message;
    }
}
