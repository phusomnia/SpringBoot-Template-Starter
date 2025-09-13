package com.example.springboot.Infrastructure.Exception;

import com.example.springboot.Core.APIException;
import com.example.springboot.Core.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import redis.clients.authentication.core.AuthXException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalValidationExceptionHandler {
    
    @ExceptionHandler(APIException.class)
    public ResponseEntity<Object> handleApiException(APIException ex) {
        var res = new APIResponse<>(
            ex.getStatus(),
            ex.getMessage()
        );

        return new ResponseEntity<>(res.getMessage(), HttpStatus.valueOf(res.getStatusCode()));
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
        MethodArgumentNotValidException ex        
    )
    {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((error) -> {
            errors.put(
                    error.getField(), 
                    error.getDefaultMessage()
            );
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }    
}
