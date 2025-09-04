package com.example.springboot.CacheAPI.dtos;

public class GetRequest {
    public CacheProvider cacheProvider;
    public String key;

    public GetRequest(
            CacheProvider cacheProvider,
            String key
    )
    {
        this.cacheProvider = cacheProvider;
        this.key = key;
    }
}
