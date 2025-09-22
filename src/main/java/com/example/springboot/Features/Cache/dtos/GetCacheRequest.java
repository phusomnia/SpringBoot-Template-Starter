package com.example.springboot.Features.Cache.dtos;

import lombok.Data;

@Data
public class GetCacheRequest<T> {
    public CacheProvider cacheProvider;
    public String key;
    public Class<T> type;

    public GetCacheRequest(
            CacheProvider cacheProvider,
            String key,
            Class<T> type
    )
    {
        this.cacheProvider = cacheProvider;
        this.key = key;
        this.type = type;
    }
}
