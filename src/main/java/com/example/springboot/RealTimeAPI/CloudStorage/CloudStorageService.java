package com.example.springboot.RealTimeAPI.CloudStorage;

import com.example.springboot.Core.CustomJson;
import com.example.springboot.Core.CustomJsonOptions;
import com.example.springboot.RealTimeAPI.CloudStorage.dto.DeleteRequest;
import com.example.springboot.RealTimeAPI.CloudStorage.service.CloudStorageFactory;
import com.example.springboot.RealTimeAPI.CloudStorage.dto.UploadRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class CloudStorageService {
    private final CloudStorageFactory _cloudStorageFactory;
    
    public Map<?, ?> uploadImage(UploadRequest req)
    {
        String type = req.file.getContentType();
        if(type != null && !type.startsWith("image/")) throw new RuntimeException("File upload is not an image");
        var cloudInstance = _cloudStorageFactory.createInstance(req.cloudProvider);
        return cloudInstance.upload(req.file);
    }

    public Map<?, ?> deleteImage(DeleteRequest req)
    {
        if(req.public_id != null && req.public_id.isEmpty()) throw new RuntimeException("Public id is null or empty");
        log.info(CustomJson.json(req, CustomJsonOptions.WRITE_INDENTED));
        var cloudInstance = _cloudStorageFactory.createInstance(req.cloudProvider);
        return cloudInstance.delete(req.public_id);
    }
}
