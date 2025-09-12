package com.example.springboot.Features.AuthAPI.Auth;

import com.example.springboot.Core.APIResponse;
import com.example.springboot.Features.AuthAPI.Auth.Dtos.LoginRequest;
import com.example.springboot.Features.AuthAPI.Auth.Dtos.RefreshRequest;
import com.example.springboot.Features.AuthAPI.Auth.Dtos.RegisterRequest;
import com.example.springboot.Features.AuthAPI.Auth.Dtos.Payload;
import com.example.springboot.Features.AuthAPI.Auth.Dtos.VerifiedOtp;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

import java.util.Map;

@RestController("AuthController")
@Slf4j
@RequestMapping("/api/v1/")
@Tag(name = "Security")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService _authService;
    
    @PostMapping("register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequest request) {
        try {
            _authService.register(request);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    "Registered successfully"
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }
    
    @PostMapping("login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginRequest request) {
        try {
            var result = _authService.login(request);

            var response = new APIResponse<Object>(
                    HttpStatus.OK.value(), 
                    "Login successfully",
                    result
            );
            
            return ResponseEntity.status(response.statusCode).body(response);
        } catch (Exception e) {
            var errorResponse = new APIResponse<>(
                    HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(),
                    null
            );
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PostMapping("refresh")
    public ResponseEntity<Object> refresh(@RequestBody RefreshRequest request) {
        try {
            var result = _authService.refresh(request);
            
            
            
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    result
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("forget-password")
    public ResponseEntity<Object> forgetPassword(@RequestParam String email) {
        try {
            var result = _authService.generateOtp(email);

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    result
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("verify-otp")
    public ResponseEntity<Object> verifyOtp(@RequestBody VerifiedOtp request) {
        try {
            var result = _authService.verifiedOtp(request);

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    result
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @SecurityRequirement(name = "JWT")
    @GetMapping("auth/secure-by-role")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Object> securebyRole() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    "Ok"
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @SecurityRequirement(name = "JWT")
    @GetMapping("auth/secure-by-permission")
    @PreAuthorize("hasAuthority('Read')")
    public ResponseEntity<Object> secure() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    "Ok"
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
