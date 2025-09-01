package com.example.springboot.AuthAPI.Auth;

import com.example.springboot.AuthAPI.Auth.dtos.LoginDTO;
import com.example.springboot.AuthAPI.Auth.dtos.RegisterDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("AuthController")
@Slf4j
@RequestMapping("/api/v1/")
@Tag(name = "Security")
@RequiredArgsConstructor
public class AuthController {
    private final AuthSerivce _service;
    
    @PostMapping("register")
    public ResponseEntity<Object> register(@RequestBody RegisterDTO request) {
        try {
            _service.register(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    "Registered successfully"
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }
    
    @PostMapping("login")
    public ResponseEntity<Object> login(@RequestBody LoginDTO request) {
        try {
            _service.login(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    "Login successfully"
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }
}
