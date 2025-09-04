package com.example.springboot.AuthAPI.Auth.Utils;

import com.example.springboot.AuthAPI.Auth.Utils.impl.IPasswordHasher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordHasherBcrypt implements IPasswordHasher {
    private final PasswordEncoder _passwordEncoder;
    
    public PasswordHasherBcrypt()
    {
        _passwordEncoder = new BCryptPasswordEncoder();
    }
    
    public String encodePassword(String rawPassword)
    {
        return _passwordEncoder.encode(rawPassword);
    }

    public boolean validatePassword(String rawPassword, String hashedPassword) {
        return _passwordEncoder.matches(rawPassword, hashedPassword);
    }
}
