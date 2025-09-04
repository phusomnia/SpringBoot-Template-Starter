package com.example.springboot.RealTimeAPI.CloudStorage.Cloudinary;

import com.cloudinary.Cloudinary;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@Slf4j
public class CloudinaryConfig 
{
    @Value("${cloudinary.url}")
    private String url;

    @Bean
    public Cloudinary cloudinary()
    {
        log.info("[CLOUDINARY] Load cloudinary config...");
        return new Cloudinary(url);
    };
}
