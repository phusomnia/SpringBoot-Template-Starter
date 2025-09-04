package com.example.springboot.CacheAPI.service;

import com.example.springboot.CacheAPI.ICache;
import com.example.springboot.CacheAPI.dtos.CacheProvider;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CacheFactory {
    private final Map<String, ICache> _cacheInstances;
    
    public CacheFactory(Map<String, ICache> cacheInstances)
    {
        _cacheInstances = cacheInstances;
    }
    
    public ICache createInstance(CacheProvider provider)
    {
        return _cacheInstances.get(provider.toString());
    }
}
