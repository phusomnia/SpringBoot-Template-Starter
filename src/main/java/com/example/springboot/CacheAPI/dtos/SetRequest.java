package com.example.springboot.CacheAPI.dtos;

public class SetRequest {
    public CacheProvider cacheProvider;
    public String key;
    public Object value;

    public SetRequest(
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