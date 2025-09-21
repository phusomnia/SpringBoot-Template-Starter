package com.example.springboot.Pun.Application.Shared.Utils;

public interface IPasswordHasher {
    public String encodePassword(String rawPassword);
    public boolean validatePassword(String rawPassword, String hashedPassword);
}
