package com.example.springboot.Features.Auth.repositories;

import com.example.springboot.Domain.entities.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    RefreshToken findByToken(String refreshToken);
}
