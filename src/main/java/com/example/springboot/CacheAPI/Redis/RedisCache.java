package com.example.springboot.CacheAPI.Redis;

import com.example.springboot.CacheAPI.ICache;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONString;
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
    public Object get(String key) {
        try (Jedis jedis = _config.jedisPool().getResource()) {
            String value = jedis.get(key);
            // parse value
            return _objectMapper.readValue(value, Object.class);
        } catch (IOException e)
        {
            throw new RuntimeException("Failed to parse JSON from Redis", e);
        }
    }
    
    @Override
    public void set(String key,Object value) {
        try (Jedis jedis = _config.jedisPool().getResource()) {
            var stringJson = _objectMapper.writeValueAsString(value);
            jedis.set(key, stringJson);
        } catch (IOException e) {
            throw new RuntimeException("Failed to serialize value to JSON", e);
        }
    }
}
