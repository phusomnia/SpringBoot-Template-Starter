package com.example.springboot.Features.Cache;

import com.example.springboot.Features.Cache.dtos.GetCacheRequest;
import com.example.springboot.Features.Cache.dtos.SetCacheRequest;

public interface ICache {
    <T> T getValue(GetCacheRequest<T> request);
    void setValue(SetCacheRequest request);
}
