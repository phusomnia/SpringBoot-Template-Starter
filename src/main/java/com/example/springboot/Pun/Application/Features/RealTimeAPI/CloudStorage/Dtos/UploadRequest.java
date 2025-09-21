package com.example.springboot.Pun.Application.Features.RealTimeAPI.CloudStorage.Dtos;

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
