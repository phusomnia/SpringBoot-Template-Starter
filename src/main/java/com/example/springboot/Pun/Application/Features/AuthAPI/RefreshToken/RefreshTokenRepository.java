package com.example.springboot.Pun.Application.Features.AuthAPI.RefreshToken;

import com.example.springboot.Pun.Domain.Entities.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    RefreshToken findByToken(String refreshToken);
}
