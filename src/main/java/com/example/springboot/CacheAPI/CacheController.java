package com.example.springboot.CacheAPI;

import com.example.springboot.CacheAPI.dtos.GetRequest;
import com.example.springboot.CacheAPI.dtos.SetRequest;
import com.example.springboot.CacheAPI.service.CacheService;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.type.descriptor.java.ObjectJavaType;
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
    public ResponseEntity<?> getValue(@RequestBody GetRequest req)
    {
        try {
            var result = _cacheService.get(req);
            log.info(result.getClass().getName());
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    
    @PostMapping("/set")
    public ResponseEntity<?> setValue(@RequestBody SetRequest req)
    {
        try {
            _cacheService.set(req);
            return ResponseEntity.status(HttpStatus.OK).body(req.value);
        } catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

//    @PostMapping("/set-json")
//    public ResponseEntity<?> setValueJson(@RequestBody SetRequest req)
//    {
//        try {
//            _cacheService.setJson(req);
//            return ResponseEntity.status(HttpStatus.OK).body("Set");
//        } catch (Exception e)
//        {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }
//
//    @PostMapping("/get-json")
//    public ResponseEntity<?> setValueJson(@RequestBody GetRequest req)
//    {
//        try {
//            var result = _cacheService.getJson(req, Object.class);
//            return ResponseEntity.status(HttpStatus.OK).body(result);
//        } catch (Exception e)
//        {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }
}
