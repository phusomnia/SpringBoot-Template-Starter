package com.example.springboot.Features.AuthAPI.RefreshToken;

import com.example.springboot.Entity.Account;
import com.example.springboot.Entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    Optional<RefreshToken> findByToken(String refreshToken);
}
