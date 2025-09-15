package com.example.springboot.Features.AuthAPI.RefreshToken;

import com.example.springboot.Entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    RefreshToken findByToken(String refreshToken);
}
