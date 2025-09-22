package com.example.springboot.Features.Auth.repositories;

import com.example.springboot.SharedKernel.entities.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    RefreshToken findByToken(String refreshToken);
}
