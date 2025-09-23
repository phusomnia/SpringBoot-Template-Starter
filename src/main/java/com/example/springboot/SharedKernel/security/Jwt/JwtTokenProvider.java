package com.example.springboot.SharedKernel.security.Jwt;

import com.example.springboot.Features.Auth.repositories.AccountRepository;
import com.example.springboot.Features.Auth.repositories.RefreshTokenRepository;
import com.example.springboot.Domain.entities.Account;
import com.example.springboot.Domain.entities.RefreshToken;
import com.example.springboot.Domain.entities.enums.CustomJsonOptions;
import com.example.springboot.SharedKernel.utils.ConvertUtils;
import com.example.springboot.SharedKernel.utils.CustomJson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String JWT_SECRET;
    @Value("${jwt.expiration}")
    private String JWT_EXPIRATION;

    private final AccountRepository _accountRepo;
    private final RefreshTokenRepository _refreshTokenRepo;
    
    private Key key()
    {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_SECRET));
    }
    
    // --- ACCESS TOKEN ---
    @SneakyThrows
    public <TAccount> String generateAccessToken(TAccount account) {
        var mapAccount = ConvertUtils.toMap(account);
        Expression expression = new ExpressionBuilder(JWT_EXPIRATION).build();
        long result = Double.valueOf(expression.evaluate()).longValue();

        String permissionList = mapAccount.get("permissionList").toString();
        String[] permissions = permissionList.split(", ");
        
        var token = Jwts.builder()
                .setClaims(Map.of(
                        "roles", mapAccount.get("roleName"),
                        "permissions", permissions
                    )
                )
                .setSubject(mapAccount.get("id").toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + result))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
        
        return token;
    }

    public Claims extractAllClaims(String token)
    {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractSubject(String token)
    {
        return extractAllClaims(token).getSubject();
    }

    public Date extractExpiration(String token)
    {
        return extractAllClaims(token).getExpiration();
    }

    public boolean isAccessTokenExpired(String token)
    {
        return extractExpiration(token).before(new Date());
    }

    public <TAccount> boolean validateToken(String token,TAccount account)
    {
        var mapAccount = ConvertUtils.toMap(account);
        log.info(mapAccount.toString());
        String accountId = extractSubject(token);
        return accountId.equals(mapAccount.get("id").toString()) && !isAccessTokenExpired(token);
    }
    
    // --- REFRESH TOKEN ---
    public <TAccount> String generateRefreshToken(TAccount account)
    {
        var mapAccount = ConvertUtils.toMap(account);
        Account acc = _accountRepo.findByUsername(mapAccount.get("username").toString()).orElseThrow(() -> new RuntimeException("Account is not found"));

        Expression expression = new ExpressionBuilder(JWT_EXPIRATION).build();
        long rtTimeExpiry = Double.valueOf(expression.evaluate()).longValue();
        
        String refreshToken = UUID.randomUUID().toString(); 
        
        RefreshToken rt = new RefreshToken();
        rt.setId(UUID.randomUUID().toString());
        rt.setToken(refreshToken);
        rt.setAccountId(acc.getId());
        rt.setExpiryDate(Instant.now().plusMillis(rtTimeExpiry));
        
//        _refreshTokenRepo.save(rt);
        
        return rt.getToken();
    }

    public Boolean isRefreshTokenExpired(RefreshToken refreshToken)
    {
        return refreshToken.getExpiryDate().isBefore(Instant.now());
    }
    
    public Authentication getAuthentication(String token)
    {
        Claims claims = extractAllClaims(token);
        log.info(CustomJson.json(claims, CustomJsonOptions.WRITE_INDENTED));

        String subject = claims.getSubject();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        
        authorities.add(new SimpleGrantedAuthority("ROLE_" + claims.get("roles").toString()));
        
        List<?> permissions = (List<?>) claims.get("permissions");
        permissions
                .stream()
                .map(permission -> new SimpleGrantedAuthority(String.valueOf(permission)))
                .forEach(authorities::add);
        
        var auth =  new UsernamePasswordAuthenticationToken(subject, null, authorities);
        log.info(CustomJson.json(auth, CustomJsonOptions.WRITE_INDENTED));
        return auth;
    }    
}
