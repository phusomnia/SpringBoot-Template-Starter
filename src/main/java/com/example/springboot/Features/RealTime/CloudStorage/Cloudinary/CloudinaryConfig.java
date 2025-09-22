package com.example.springboot.Features.RealTime.CloudStorage.Cloudinary;

import com.cloudinary.Cloudinary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class CloudinaryConfig 
{
    @Value("${cloudinary.url}")
    private String url;
    
    @Bean
    public Cloudinary cloudinary()
    {
        return new Cloudinary(url);
    };
}
