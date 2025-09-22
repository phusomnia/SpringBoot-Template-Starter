package com.example.springboot.SharedKernel.exception;

import com.example.springboot.SharedKernel.api.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("You do not have permission to access this resource.");
    }
}
