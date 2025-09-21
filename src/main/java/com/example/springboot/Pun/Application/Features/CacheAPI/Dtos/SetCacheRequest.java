package com.example.springboot.Pun.Application.Features.CacheAPI.Dtos;

public class SetCacheRequest {
    public CacheProvider cacheProvider;
    public String key;
    public Object value;

    public SetCacheRequest(
            CacheProvider cacheProvider,
            String key,
            Object value
    )
    {
        this.cacheProvider = cacheProvider;
        this.key = key;
        this.value = value;
    }
}