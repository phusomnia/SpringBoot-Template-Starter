package com.example.springboot.AuthAPI.Auth;

import com.example.springboot.AuthAPI.Account.AccountRepository;
import com.example.springboot.AuthAPI.Auth.dtos.LoginDTO;
import com.example.springboot.AuthAPI.Auth.dtos.RefreshTokenDTO;
import com.example.springboot.AuthAPI.Auth.dtos.RegisterDTO;
import com.example.springboot.AuthAPI.Auth.Utils.PasswordHasherBcrypt;
import com.example.springboot.AuthAPI.RefreshToken.RefreshTokenRepository;
import com.example.springboot.Core.CustomJson;
import com.example.springboot.Core.CustomJsonOptions;
import com.example.springboot.Entity.Account;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthSerivce {
    private final PasswordHasherBcrypt _passwordHasher;
    private final JwtTokenProvider _provider;

    private final AccountRepository _accountRepo;
    private final RefreshTokenRepository _refreshTokenRepo;
    
    public void register(RegisterDTO request)
    {
        Account account = new Account();
        account.setId(UUID.randomUUID().toString());
        account.setUsername(request.username);
        account.setPassword(_passwordHasher.encodePassword(request.password));
        _accountRepo.save(account);
    }
    
    @SneakyThrows
    public Map<String, Object> login(LoginDTO request) {
        var account = _accountRepo.findAccountRoleAndPermission(request.username)
                .orElseThrow(() -> new Exception("Can't find username"));
        
        log.info(CustomJson.json(account, CustomJsonOptions.WRITE_INDENTED));
        
        var accessToken = _provider.generateAccessToken(account);
        var refreshToken = _provider.generateRefreshToken(account);

        boolean isPassword = _passwordHasher.validatePassword(request.password, account.get("password").toString());
        if(!isPassword) throw new Exception("Password is not correct");
        
        return mapTokenResponse(accessToken, refreshToken);
    }

    @SneakyThrows
    public Map<String, Object> refresh(RefreshTokenDTO request) {
        var rt = _refreshTokenRepo.findByToken(request.refreshToken)
                .orElseThrow(() -> new Exception("Can't find refresh token"));
        
        if(_provider.isRefreshTokenExpired(rt)) throw new Exception("Refresh token is expired");
        
        var account = _accountRepo.findById(rt.getId());
        var accessToken = _provider.generateAccessToken(account);
        var refreshToken = _provider.generateRefreshToken(account);
        
        return mapTokenResponse(accessToken, refreshToken);
    }
    
    public Map<String, Object> mapTokenResponse(String accessToken, String refreshToken)
    {
        Map<String, Object> map = new HashMap<>();
        map.put("access-token", accessToken);
        map.put("refresh-token", refreshToken);
        return map;
    }
}

