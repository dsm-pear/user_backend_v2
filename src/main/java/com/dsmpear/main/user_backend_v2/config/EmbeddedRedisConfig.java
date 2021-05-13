package com.dsmpear.main.user_backend_v2.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@RequiredArgsConstructor
@Profile({"test", "local"})
@Configuration
public class EmbeddedRedisConfig {

    private static RedisServer redisServer;

    @Value("${spring.redis.port}")
    private Integer port;

    @PostConstruct
    public void runRedis() {
        if(redisServer == null) {
            redisServer = new RedisServer(port);
            redisServer.start();
        }
    }

    @PreDestroy
    public void stopRedis() {
        if(redisServer != null)
            redisServer.stop();
    }

}