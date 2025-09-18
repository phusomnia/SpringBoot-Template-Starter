package com.example.springboot.Pun.Application.Features.CacheAPI;

import com.example.springboot.Pun.Application.Features.CacheAPI.Dtos.CacheProvider;
import com.example.springboot.Pun.Application.Features.CacheAPI.Dtos.GetCacheRequest;
import com.example.springboot.Pun.Application.Features.CacheAPI.Dtos.SetCacheRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class CacheService {
    private final Map<String, ICache> _cacheInstances;
    
    public CacheService(Map<String, ICache> cacheInstances)
    {
        _cacheInstances = cacheInstances;
    }

    public ICache createInstance(CacheProvider provider)
    {
        return _cacheInstances.get(provider.toString());
    }

    public <T> T getValue(GetCacheRequest<T> req)
    {
        var instance = createInstance(req.cacheProvider);
        return instance.getValue(req);
    }
    
    public void setValue(SetCacheRequest req)
    {
        var instance = createInstance(req.cacheProvider);
        instance.setValue(req);
    }
}
