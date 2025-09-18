package com.example.springboot.Pun.Application.Features.AuthAPI.Auth;

import com.example.springboot.Pun.Application.Features.AuthAPI.Account.AccountRepository;
import com.example.springboot.Pun.Application.Features.AuthAPI.Auth.Dtos.LoginRequest;
import com.example.springboot.Pun.Application.Features.AuthAPI.Auth.Dtos.RefreshRequest;
import com.example.springboot.Pun.Application.Features.AuthAPI.Auth.Dtos.RegisterRequest;
import com.example.springboot.Pun.Application.Features.AuthAPI.Auth.Dtos.EmailRequest;
import com.example.springboot.Pun.Application.Features.AuthAPI.EmailSender.OtpProvider;
import com.example.springboot.Pun.Application.Features.AuthAPI.Auth.Dtos.Payload;
import com.example.springboot.Pun.Application.Features.AuthAPI.Auth.Dtos.VerifiedOtp;
import com.example.springboot.Pun.Application.Features.CacheAPI.CacheService;
import com.example.springboot.Pun.Application.Features.CacheAPI.Dtos.CacheProvider;
import com.example.springboot.Pun.Application.Features.CacheAPI.Dtos.GetCacheRequest;
import com.example.springboot.Pun.Application.Features.CacheAPI.Dtos.SetCacheRequest;
import com.example.springboot.Pun.Application.Features.AuthAPI.RefreshToken.RefreshTokenRepository;
import com.example.springboot.Pun.Domain.Core.APIException;
import com.example.springboot.Pun.Domain.Core.APIResponse;
import com.example.springboot.Pun.Api.Security.Jwt.JwtTokenProvider;
import com.example.springboot.Pun.Application.Shared.Utils.PasswordHasherBcrypt;
import com.example.springboot.Pun.Domain.Entities.Account;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordHasherBcrypt   _passwordHarsher;
    private final JwtTokenProvider       _tokenProvider;
    private final AccountRepository      _accountRepo;
    private final RefreshTokenRepository _refreshTokenRepo;
    private final JavaMailSender         _mailSender;
    private final OtpProvider            _otpProvider;
    private final CacheService           _cacheService;
    
    public void register(RegisterRequest request)
    {
        Account account = new Account();
        account.setId(UUID.randomUUID().toString());
        account.setUsername(request.username);
        account.setPassword(_passwordHarsher.encodePassword(request.password));
        _accountRepo.save(account);
    }
    
    @SneakyThrows
    public APIResponse<Map<String, Object>> authenticate(LoginRequest request) {
        
        var account = _accountRepo.findAccountRoleAndPermission(request.username);
        if(account.isEmpty()) throw new APIException(HttpStatus.BAD_REQUEST.value(), "Can't find username");
        
        var accessToken = _tokenProvider.generateAccessToken(account);
        var refreshToken = _tokenProvider.generateRefreshToken(account);

        boolean isPassword = _passwordHarsher.validatePassword(
                request.password, 
                account.get("password").toString()
        );
        
        if(!isPassword) throw new APIException(HttpStatus.BAD_REQUEST.value(), "Wrong password");
        
        return new APIResponse<Map<String, Object>>(
                HttpStatus.OK.value(),
                "Login successfully",
                mapTokenResponse(accessToken, refreshToken)
        );
    }

    @SneakyThrows
    public APIResponse<?> refresh(RefreshRequest request) {
        var rt = _refreshTokenRepo.findByToken(request.refreshToken);
        
        if(rt == null) throw new APIException(HttpStatus.BAD_REQUEST.value(), "Can't find refresh token");
        
        if(_tokenProvider.isRefreshTokenExpired(rt)) throw new APIException(HttpStatus.BAD_REQUEST.value(), "Refresh token expired");
        
        var account = _accountRepo.findById(rt.getId());
        var accessToken = _tokenProvider.generateAccessToken(account);
        var refreshToken = _tokenProvider.generateRefreshToken(account);

        return new APIResponse<>(
                HttpStatus.OK.value(),
                "Refresh successfully",
                mapTokenResponse(accessToken, refreshToken)
        );
    }
    
    public Map<String, Object> mapTokenResponse(String accessToken, String refreshToken)
    {
        Map<String, Object> map = new HashMap<>();
        map.put("access-token", accessToken);
        map.put("refresh-token", refreshToken);
        return map;
    }
    
    // --- ---
    public void sendMessage(EmailRequest request) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(request.to);
        message.setSubject(request.to);
        message.setText(request.body);
        _mailSender.send(message);
    }

    public Object generateOtp(String email) throws JsonProcessingException {
        String otp = _otpProvider.generateNumericOtp(6);
        String salt = UUID.randomUUID().toString().replace("-", "");
        String hash = _otpProvider.hashOtp(otp, salt);
        String key = "otp:" + email;
        
        Payload payload = new Payload(
                hash,
                salt,
                new Date(),
                2
        );
        
        SetCacheRequest req = new SetCacheRequest(CacheProvider.REDIS, key, payload);
        
        _cacheService.createInstance(req.cacheProvider).setValue(req);

        EmailRequest emailReq = new EmailRequest(email, "OTP Verification", otp);
        sendMessage(emailReq);

        return otp;
    }
    
    public Object verifiedOtp(VerifiedOtp request) throws Exception {
        String key = "otp:" + request.email;
        var getCache = new GetCacheRequest<>(CacheProvider.REDIS, key, Payload.class);
        Payload payload = _cacheService.createInstance(CacheProvider.REDIS).getValue(getCache);
        
        if(payload.isIOutOfTries()) throw new Exception("Out of tries");
        
        var hash = _otpProvider.hashOtp(request.otptCode, payload.getSalt());
        
        if(!hash.equals(payload.getHash()))
        {

            payload.consumeTry();
            var retryRequest = new SetCacheRequest(
                    CacheProvider.REDIS,
                    key,
                    payload 
            );
            _cacheService.createInstance(retryRequest.cacheProvider).setValue(retryRequest);
            throw new RuntimeException("Invalid code");
        }
        
        log.info("[SERVICE - verifiedOtp]" + key);
        
        return request.otptCode;
    }

    public boolean hasRole(Authentication auth, String role) {
        if (auth == null || role == null) return false;
        
        return auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + role)); 
    }
}

