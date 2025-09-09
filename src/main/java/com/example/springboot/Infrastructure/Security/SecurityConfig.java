package com.example.springboot.Infrastructure.Security;

import com.example.springboot.Core.JwtAuthEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtFilter _jwtFilter;
    private final JwtAuthEntryPoint _jwtAuthenticationEntryPoint;
    
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer()
    {
        return webSecurity -> webSecurity.ignoring().requestMatchers(
                "api/v1/**",
                "/api-docs",
                "/scalar.html",
                "/chat.html"
        );
    }
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(CorsConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/api/v1/auth/secure").permitAll()
                        .requestMatchers("/ws/**").permitAll()
                        .requestMatchers("/topic/**").permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .httpBasic(basic -> basic.authenticationEntryPoint(_jwtAuthenticationEntryPoint))
                .exceptionHandling(Customizer.withDefaults())
                .addFilterBefore(_jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }    
}
