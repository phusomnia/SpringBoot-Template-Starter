package com.example.springboot.Features.RealTime.CloudStorage.Cloudinary;

import com.cloudinary.utils.ObjectUtils;
import com.example.springboot.Features.RealTime.CloudStorage.ICloudStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class CloudinaryStorage implements ICloudStorage
{
    private final CloudinaryConfig _config;
    
    public CloudinaryStorage(CloudinaryConfig config)
    {
        _config = config;
    }

    @Override
    public Boolean isConnected() {
        try {
            var pingResponse = _config.cloudinary().api().ping(ObjectUtils.emptyMap());
            return "ok".equals(pingResponse.get("status"));        
        }  catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<?, ?> upload(MultipartFile file) {
        try {
            return _config.cloudinary()
                    .uploader()
                    .upload(file.getBytes(), ObjectUtils.asMap(
                            "resource_type", 
                            "image", 
                            "public_id", 
                            file.getOriginalFilename()
                        )
                    );
        } catch (IOException e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Map<?, ?> delete(String public_id) {
        try {
            return _config.cloudinary()
                    .uploader()
                    .destroy(public_id, ObjectUtils.asMap(
                            "resource_type", "image",   
                            "invalidate", true          
                        )
                    );
        } catch (IOException e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }
}
