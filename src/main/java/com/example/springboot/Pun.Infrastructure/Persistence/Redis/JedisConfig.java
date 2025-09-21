package com.example.springboot.Pun.Infrastructure.Persistence.Redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPooled;

import java.net.URI;

@Configuration
public class JedisConfig {
    @Value("${spring.data.redis.url}")
    private String _redisURL;
    
    @Bean
    public JedisPool jedisPool()
    {
        var poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(2);
        poolConfig.setMaxIdle(5);
        poolConfig.setMinIdle(1);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setJmxEnabled(false);
        
        URI uri = URI.create(_redisURL);
        String password = "";
        if(uri.getPort() != 6379)
        {
            password = uri.getUserInfo().split(":")[1];
        }
        
        return new JedisPool(
            poolConfig,
            uri.getHost(),
            uri.getPort()
        );
    }
}
