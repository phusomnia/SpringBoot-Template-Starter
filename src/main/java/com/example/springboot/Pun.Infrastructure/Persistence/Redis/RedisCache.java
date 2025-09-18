package com.example.springboot.Pun.Infrastructure.Persistence.Redis;

import com.example.springboot.Pun.Application.Features.CacheAPI.Dtos.GetCacheRequest;
import com.example.springboot.Pun.Application.Features.CacheAPI.Dtos.SetCacheRequest;
import com.example.springboot.Pun.Application.Features.CacheAPI.ICache;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.io.IOException;

@Slf4j
@Component("REDIS")
public class RedisCache implements ICache {
    private final JedisConfig _config;
    private final ObjectMapper _objectMapper;
    
    public RedisCache(
            JedisConfig config,
            ObjectMapper objectMapper
    )
    {
        _config = config;
        _objectMapper = objectMapper;
    }

    @Override
    public <T> T getValue(GetCacheRequest<T> request) {
        try (Jedis jedis = _config.jedisPool().getResource()) {
            String value = jedis.get(request.key);
            
            if(value == null) return null;
            
            return _objectMapper.readValue(value, request.type);
        } catch (IOException e)
        {
            throw new RuntimeException("Failed to parse JSON from Redis", e);
        }
    }

    @Override
    public void setValue(SetCacheRequest request) {
        try (Jedis jedis = _config.jedisPool().getResource()) {
            var stringJson = _objectMapper.writeValueAsString(request.value);
            jedis.set(request.key, stringJson);
        } catch (IOException e) {
            throw new RuntimeException("Failed to serialize value to JSON", e);
        }
    }
}
