package com.example.springboot.AuthAPI.Auth.Utils.impl;

public interface IPasswordHasher {
    public String encodePassword(String rawPassword);
    public boolean validatePassword(String rawPassword, String hashedPassword);
}
