package com.example.springboot.Pun.Application.Features.RealTimeAPI.CloudStorage;

import com.example.springboot.Pun.Application.Features.RealTimeAPI.CloudStorage.Cloudinary.CloudinaryConfig;
import com.example.springboot.Pun.Application.Features.RealTimeAPI.CloudStorage.Cloudinary.CloudinaryStorage;
import com.example.springboot.Pun.Application.Features.RealTimeAPI.CloudStorage.Dtos.CloudProvider;
import com.example.springboot.Pun.Application.Features.RealTimeAPI.CloudStorage.Dtos.DeleteRequest;
import com.example.springboot.Pun.Application.Features.RealTimeAPI.CloudStorage.Dtos.UploadRequest;
import com.example.springboot.Pun.Domain.Core.CustomJson;
import com.example.springboot.Pun.Domain.Core.CustomJsonOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class CloudStorageService {
    private final CloudinaryConfig _cloudinaryConfig;

    public CloudStorageService(CloudinaryConfig cloudinaryConfig)
    {
        _cloudinaryConfig = cloudinaryConfig;
    }

    private ICloudStorage createInstance(CloudProvider provider)
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
    
    public Boolean isConnected()
    {
        var instance = createInstance(CloudProvider.CLOUDINARY);
        return instance.isConnected();
    }
    
    public Map<?, ?> uploadImage(UploadRequest req)
    {
        String type = req.file.getContentType();
        if(type != null && !type.startsWith("image/")) throw new RuntimeException("File upload is not an image");
        
        var instance = createInstance(req.cloudProvider);
        return instance.upload(req.file);
    }

    public Map<?, ?> deleteImage(DeleteRequest req)
    {
        if(req.public_id != null && req.public_id.isEmpty()) throw new RuntimeException("Public id is null or empty");
        log.info(CustomJson.json(req, CustomJsonOptions.WRITE_INDENTED));
        
        var instance = createInstance(req.cloudProvider);
        return instance.delete(req.public_id);
    }
}
