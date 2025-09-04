package com.example.springboot.AuthAPI.Auth;

import com.example.springboot.AuthAPI.Auth.dtos.LoginDTO;
import com.example.springboot.AuthAPI.Auth.dtos.RefreshTokenDTO;
import com.example.springboot.AuthAPI.Auth.dtos.RegisterDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
            var result = _service.login(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    result
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    @PostMapping("refresh")
    public ResponseEntity<Object> refresh(@RequestBody RefreshTokenDTO request) {
        try {
            var result = _service.refresh(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    result
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }
    
    @SecurityRequirement(name = "JWT")
    @GetMapping("auth/secureByRole")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Object> securebyRole() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    "Ok"
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }
    
    @SecurityRequirement(name = "JWT")
    @GetMapping("auth/secureByPermission")
    @PreAuthorize("hasAuthority('Read')")
    public ResponseEntity<Object> secure() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    "Ok"
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }
}
