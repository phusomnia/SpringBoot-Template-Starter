package com.example.springboot.Core;

import com.example.springboot.AuthAPI.Account.AccountRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtTokenProvider _jwtTokenProvider;
    private final AccountRepository _accountRepository;
    
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException
    {
        try {
            String authHeader = request.getHeader("Authorization");
            log.info("Auth header: {}", authHeader);
//            if (authHeader != null) {
//                String jwtToken = authHeader.substring(7);
//                if(jwtTokenProvider.isTokenExpired(jwtToken)){
//                    Claims claims = jwtTokenProvider.extractAllClaims(jwtToken);
//                    log.info(claims.toString());
//                    String username = jwtTokenProvider.extractUsername(jwtToken);
//                    UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
//                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                            userDetails, null, userDetails.getAuthorities()
//                    );
//                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    SecurityContextHolder.getContext().setAuthentication(authToken);
//                }
//            }
        } catch (Exception e)
        {
            throw new ServletException(e);
        }
        log.info("filter");
        filterChain.doFilter(request, response);
    }
}
