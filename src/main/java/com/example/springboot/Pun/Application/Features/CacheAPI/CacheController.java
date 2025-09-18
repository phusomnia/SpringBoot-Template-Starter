package com.example.springboot.Pun.Application.Features.CacheAPI;

import com.example.springboot.Pun.Application.Features.CacheAPI.Dtos.GetCacheRequest;
import com.example.springboot.Pun.Application.Features.CacheAPI.Dtos.SetCacheRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "Cache")
@RequestMapping("api/v1/cache")
public class CacheController {
    private final CacheService _cacheService;
    
    public CacheController(CacheService cacheService)
    {
        _cacheService = cacheService;
    }
    
    @PostMapping("/get")
    public ResponseEntity<?> getValue(@RequestBody GetCacheRequest req)
    {
        try {
            var result = _cacheService.getValue(req);
            log.info(result.getClass().getName());
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    
    @PostMapping("/set")
    public ResponseEntity<?> setValue(@RequestBody SetCacheRequest req)
    {
        try {
            _cacheService.setValue(req);
            return ResponseEntity.status(HttpStatus.OK).body(req.value);
        } catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
