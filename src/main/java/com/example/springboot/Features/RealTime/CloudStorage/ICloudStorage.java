package com.example.springboot.Features.RealTime.CloudStorage;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ICloudStorage {
    Boolean isConnected();
    Map<?, ?> upload(MultipartFile file);
    Map<?, ?> delete(String public_id);
}
