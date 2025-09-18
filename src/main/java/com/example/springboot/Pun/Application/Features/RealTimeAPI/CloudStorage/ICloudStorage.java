package com.example.springboot.Pun.Application.Features.RealTimeAPI.CloudStorage;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.FutureTask;

public interface ICloudStorage {
    Boolean isConnected();
    Map<?, ?> upload(MultipartFile file);
    Map<?, ?> delete(String public_id);
}
