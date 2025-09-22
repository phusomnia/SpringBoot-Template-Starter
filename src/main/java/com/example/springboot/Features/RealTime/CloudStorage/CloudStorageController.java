package com.example.springboot.Features.RealTime.CloudStorage;


import com.example.springboot.Features.RealTime.CloudStorage.Dtos.DeleteRequest;
import com.example.springboot.Features.RealTime.CloudStorage.Dtos.UploadRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "CloudStorage")
@RequestMapping("api/v1/storage/")
@RequiredArgsConstructor
public class CloudStorageController {
    private final CloudStorageService _service;

    @GetMapping(value = "is-connected")
    public ResponseEntity<?> isConnectedAPI()
    {
        try {
            var isConnected = _service.isConnected();
            if(!isConnected) throw new RuntimeException("WTF");
            return ResponseEntity.status(HttpStatus.OK).body("Connected");
        } catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    
    @PostMapping(value = "upload-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImageAPI(UploadRequest request)
    {
        try {
            var response = _service.uploadImage(request);
            
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    
    @DeleteMapping(value = "delete-image")
    public ResponseEntity<?> deleteImageAPI(DeleteRequest request)
    {
        try {
            var response = _service.deleteImage(request);
            
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}

