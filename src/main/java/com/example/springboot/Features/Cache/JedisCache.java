package com.example.springboot.Features.Cache;

import com.example.springboot.Features.Cache.dtos.GetCacheRequest;
import com.example.springboot.Features.Cache.dtos.SetCacheRequest;
import com.example.springboot.Features.RealTime.config.JedisConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.io.IOException;

@Slf4j
@Component("REDIS")
public class JedisCache implements ICache {
    private final JedisConfig _config;
    private final ObjectMapper _objectMapper;
    
    public JedisCache(
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
