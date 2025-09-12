package com.example.springboot.Core;

import lombok.Data;

import java.util.Collections;
import java.util.Map;

@Data
public class APIResponse <T> extends BaseResponse {
    public T data;
    public Map<String, Object> metadata = Collections.emptyMap();
    
    public APIResponse(){}
    
    public APIResponse(
            int status, 
            String message, 
            T data) {
        this(status, message, data, null); 
    }
    
    public APIResponse(
            int status,
            String message,
            T data, 
            Map<String, Object> metata
    ) 
    {
        super(status, message);
        this.data = data;
        this.metadata = metata;
    }
}
