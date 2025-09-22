package com.example.springboot.SharedKernel.security.Jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtTokenProvider _jwtTokenProvider;
    
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException
    {
        try {
            String authHeader = request.getHeader("Authorization");
            log.info("{}", authHeader);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            String jwtToken = authHeader.substring(7);
            if(_jwtTokenProvider.isAccessTokenExpired(jwtToken)){
                throw new ServletException("Expired or invalid JWT token");
            }
            
            var auth = _jwtTokenProvider.getAuthentication(jwtToken);
//                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);
        } catch (Exception e)
        {
            throw new ServletException(e);
        }
    }
}
