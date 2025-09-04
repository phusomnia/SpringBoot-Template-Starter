package com.example.springboot.RealTimeAPI.CloudStorage.service;

import com.example.springboot.RealTimeAPI.CloudStorage.Cloudinary.CloudinaryStorage;
import com.example.springboot.RealTimeAPI.CloudStorage.Cloudinary.CloudinaryConfig;
import com.example.springboot.RealTimeAPI.CloudStorage.ICloudStorage;
import com.example.springboot.RealTimeAPI.CloudStorage.dto.CloudProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CloudStorageFactory {
    private final CloudinaryConfig _cloudinaryConfig;
    
    public ICloudStorage createInstance(CloudProvider provider)
    {
        switch (provider)
        {
            case CLOUDINARY:
                log.info("[CLOUDINARY] Load cloudinary service...");
                return new CloudinaryStorage(_cloudinaryConfig);
            default:
                throw new IllegalArgumentException("Unknown cloud provider" + provider); 
        }
    }    
}
