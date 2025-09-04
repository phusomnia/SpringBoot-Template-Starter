package com.example.springboot.RealTimeAPI.CloudStorage.Cloudinary;

import com.cloudinary.utils.ObjectUtils;
import com.example.springboot.RealTimeAPI.CloudStorage.ICloudStorage;
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
    public void download() {

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
