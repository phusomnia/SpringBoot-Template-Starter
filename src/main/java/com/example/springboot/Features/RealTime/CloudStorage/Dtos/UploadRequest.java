package com.example.springboot.Features.RealTime.CloudStorage.Dtos;

import org.springframework.web.multipart.MultipartFile;


public class UploadRequest {
    public CloudProvider cloudProvider;
    public MultipartFile file;

    public UploadRequest(){}

    public UploadRequest(CloudProvider cloudProvider, MultipartFile file) {
        this.cloudProvider = cloudProvider;
        this.file = file;
    }
}
