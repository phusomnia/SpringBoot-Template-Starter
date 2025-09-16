package com.example.springboot.Infrastructure.Utils;

public interface IPasswordHasher {
    public String encodePassword(String rawPassword);
    public boolean validatePassword(String rawPassword, String hashedPassword);
}
