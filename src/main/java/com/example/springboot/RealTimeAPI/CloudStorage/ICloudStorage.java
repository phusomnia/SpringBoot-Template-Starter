package com.example.springboot.RealTimeAPI.CloudStorage;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ICloudStorage {
    Map<?, ?> upload(MultipartFile file);
    void download();
    Map<?, ?> delete(String public_id);
}
