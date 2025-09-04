package com.example.springboot.RealTimeAPI.CloudStorage;

import com.example.springboot.RealTimeAPI.CloudStorage.dto.CloudProvider;
import com.example.springboot.RealTimeAPI.CloudStorage.dto.DeleteRequest;
import com.example.springboot.RealTimeAPI.CloudStorage.dto.UploadRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Tag(name = "ICloudStorage")
@RequestMapping("api/v1/storage")
@RequiredArgsConstructor
public class CloudStorageController {
    private final CloudStorageService _cloudStorageService;
    
    @PostMapping(value = "/upload-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImageAPI(
            @RequestParam CloudProvider provider, 
            @RequestParam MultipartFile file
            )
    {
        try {
            UploadRequest request = new UploadRequest(provider,file);
            var response = _cloudStorageService.uploadImage(request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    
    @DeleteMapping(value = "/delete-image")
    public ResponseEntity<?> deleteImageAPI(
            @RequestParam CloudProvider provider, 
            @RequestParam String public_id
            )
    {
        try {
            var request = new DeleteRequest(provider, public_id);
            var response = _cloudStorageService.deleteImage(request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}

