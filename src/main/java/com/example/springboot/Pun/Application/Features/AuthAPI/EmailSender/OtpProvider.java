package com.example.springboot.Pun.Application.Features.AuthAPI.EmailSender;

import org.apache.tomcat.Jar;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class OtpProvider {
    private static final SecureRandom RANDOM = new SecureRandom();

    public String generateNumericOtp(int length) {
        StringBuilder digits = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            digits.append(RANDOM.nextInt(10));
        }
        return digits.toString();
    }
    
    public String hashOtp(String otp, String salt)
    {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] combined = (otp + salt).getBytes(StandardCharsets.UTF_8);
            byte[] hash = sha.digest(combined);
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }
}
