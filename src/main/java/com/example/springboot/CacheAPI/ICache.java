package com.example.springboot.CacheAPI;

import com.example.springboot.CacheAPI.dtos.SetRequest;
import com.fasterxml.jackson.databind.JsonNode;

public interface ICache {
    Object get(String key);
    void set(String key,Object value);
//    <T> void setJson(String key, T value);
//    <T> T getJson(String key, Class<T> clazz);
}
