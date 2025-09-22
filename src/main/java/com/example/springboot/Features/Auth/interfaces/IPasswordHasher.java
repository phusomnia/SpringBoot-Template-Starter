package com.example.springboot.Features.Auth.interfaces;

public interface IPasswordHasher {
    public String encodePassword(String rawPassword);
    public boolean validatePassword(String rawPassword, String hashedPassword);
}
