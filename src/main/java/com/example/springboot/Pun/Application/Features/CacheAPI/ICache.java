package com.example.springboot.Pun.Application.Features.CacheAPI;

import com.example.springboot.Pun.Application.Features.CacheAPI.Dtos.GetCacheRequest;
import com.example.springboot.Pun.Application.Features.CacheAPI.Dtos.SetCacheRequest;

public interface ICache {
    <T> T getValue(GetCacheRequest<T> request);
    void setValue(SetCacheRequest request);
}
