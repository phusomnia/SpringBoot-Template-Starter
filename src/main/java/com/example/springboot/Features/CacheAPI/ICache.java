package com.example.springboot.Features.CacheAPI;

import com.example.springboot.Features.CacheAPI.Dtos.GetCacheRequest;
import com.example.springboot.Features.CacheAPI.Dtos.SetCacheRequest;

public interface ICache {
    <T> T getValue(GetCacheRequest<T> request);
    void setValue(SetCacheRequest request);
}
