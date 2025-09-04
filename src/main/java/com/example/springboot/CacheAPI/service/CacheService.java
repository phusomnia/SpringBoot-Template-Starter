package com.example.springboot.CacheAPI.service;

import com.example.springboot.CacheAPI.dtos.GetRequest;
import com.example.springboot.CacheAPI.dtos.SetRequest;
import com.example.springboot.Core.ConvertUtils;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class CacheService {
    private final CacheFactory _cacheFactory;
    
    public CacheService(CacheFactory cacheFactory)
    {
        _cacheFactory = cacheFactory;
    }

    public Object get(GetRequest req)
    {
        var instance = _cacheFactory.createInstance(req.cacheProvider);
        var result = instance.get(req.key);
        log.info(result.getClass().toString());
        return result;
    }
    
    public void set(SetRequest req)
    {
        var instance = _cacheFactory.createInstance(req.cacheProvider);
        instance.set(req.key, req.value);
    }

//    public void setJson(SetRequest req)
//    {
//        var instance = _cacheFactory.createInstance(req.cacheProvider);
//        instance.setJson(req.key, String.valueOf(req.value));
//    }
//
//    public <T> T getJson(GetRequest req)
//    {
//        var instance = _cacheFactory.createInstance(req.cacheProvider);
//        return instance.getJson(req.key);
//    }
}
