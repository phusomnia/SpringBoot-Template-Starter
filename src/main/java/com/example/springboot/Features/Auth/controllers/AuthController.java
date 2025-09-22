package com.example.springboot.Features.Auth.controllers;

import com.example.springboot.Features.Auth.dtos.LoginRequest;
import com.example.springboot.Features.Auth.dtos.RefreshRequest;
import com.example.springboot.Features.Auth.dtos.VerifiedOtp;
import com.example.springboot.Features.Auth.services.AuthService;
import com.example.springboot.Features.Auth.dtos.RegisterRequest;
import com.example.springboot.SharedKernel.enums.CustomJsonOptions;
import com.example.springboot.SharedKernel.utils.CustomJson;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
        _authService.register(request);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(
                "Registered successfully");
    }
    
    @PostMapping("login")
    public ResponseEntity<Object> login(
        @Valid @RequestBody LoginRequest request,
        HttpServletResponse response
    ) {
        var result = _authService.authenticate(request);
        
        log.info(CustomJson.json(result.getData(), CustomJsonOptions.WRITE_INDENTED));
        if(result.getData() != null)
        {
            log.info("set cookie");
            ResponseCookie cookie = ResponseCookie.from("refresh-token", String.valueOf(result.getData().get("refresh-token")))
                    .httpOnly(true)
                    .secure(false)
                    .sameSite("Strict")
                    .path("/")
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());            
        }
        
        return ResponseEntity.status(result.getStatusCode()).body(result);
    }

    @PostMapping("refresh")
    public ResponseEntity<Object> refresh(@RequestBody RefreshRequest request) {
        try {
            var result = _authService.refresh(request);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    result
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
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
    @GetMapping("auth/{url}")
    @PreAuthorize("@authService.hasRole(authentication, #roleAllowed)")
    public ResponseEntity<Object> checkRole(
            @Parameter String roleAllowed,
            @PathVariable String url) 
    {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("url", url));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
