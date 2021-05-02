package com.dsmpear.main.user_backend_v2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
@Profile("test")
public class EmbeddedRedisConfig {

    private final RedisServer redisServer;

    @Value("${spring.redis.port}")
    int redisPort;

    public EmbeddedRedisConfig() {
        this.redisServer = RedisServer.builder()
                .setting("maxheap 128M")
                .build();
    }

    @PostConstruct
    public void runRedis() {
        redisServer.start();
    }

    @PreDestroy
    public void stopRedis() {
        if(redisServer != null)
            redisServer.stop();
    }

}