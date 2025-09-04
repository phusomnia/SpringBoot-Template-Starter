package com.example.springboot.AuthAPI.Auth;

import com.example.springboot.AuthAPI.Account.AccountRepository;
import com.example.springboot.AuthAPI.RefreshToken.RefreshTokenRepository;
import com.example.springboot.Core.ConvertUtils;
import com.example.springboot.Core.CustomJson;
import com.example.springboot.Core.CustomJsonOptions;
import com.example.springboot.Entity.Account;
import com.example.springboot.Entity.RefreshToken;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.security.Key;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    
    @SneakyThrows
    public <TAccount> String generateAccessToken(TAccount account) {
        var dictAccount = ConvertUtils.toDict(account);
        Expression expression = new ExpressionBuilder(JWT_EXPIRATION).build();
        long result = Double.valueOf(expression.evaluate()).longValue();

        String permissionList = dictAccount.get("permissionList").toString();
        String[] permissions = permissionList.split(", ");
        
        var token = Jwts.builder()
                .setClaims(Map.of(
                        "roles", dictAccount.get("roleName"),
                        "permissions", permissions
                    )
                )
                .setSubject(dictAccount.get("id").toString())
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
        var dictAcc = ConvertUtils.toDict(account);
        log.info(dictAcc.toString());
        String accountId = extractSubject(token);
        return accountId.equals(dictAcc.get("id").toString()) && !isAccessTokenExpired(token);
    }
    
    // ### REFRESH TOKEN ###
    public <TAccount> String generateRefreshToken(TAccount account)
    {
        var dictAcc = ConvertUtils.toDict(account);
        Account acc = _accountRepo.findByUsername(dictAcc.get("username").toString()).orElseThrow(() -> new RuntimeException("Account is not found"));

        Expression expression = new ExpressionBuilder(JWT_EXPIRATION).build();
        long rtTimeExpiry = Double.valueOf(expression.evaluate()).longValue();
        
        var refreshToken = Base64.getEncoder().encodeToString(acc.getId().getBytes());
        RefreshToken rt = new RefreshToken();
        rt.setId(UUID.randomUUID().toString());
        rt.setToken(refreshToken);
        rt.setAccountId(acc.getId());
        rt.setExpiryDate(Instant.now().plusMillis(rtTimeExpiry));
        _refreshTokenRepo.save(rt);
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
