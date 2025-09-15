package com.example.springboot.Features.AuthAPI.Auth;

import com.cloudinary.api.exceptions.ApiException;
import com.example.springboot.Core.APIException;
import com.example.springboot.Core.APIResponse;
import com.example.springboot.Core.CustomJson;
import com.example.springboot.Core.CustomJsonOptions;
import com.example.springboot.Features.AuthAPI.Auth.Dtos.LoginRequest;
import com.example.springboot.Features.AuthAPI.Auth.Dtos.RefreshRequest;
import com.example.springboot.Features.AuthAPI.Auth.Dtos.RegisterRequest;
import com.example.springboot.Features.AuthAPI.Auth.Dtos.Payload;
import com.example.springboot.Features.AuthAPI.Auth.Dtos.VerifiedOtp;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<Object> login(
        @Valid @RequestBody LoginRequest request,
        HttpServletResponse response
    ) {
        try {
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
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
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
