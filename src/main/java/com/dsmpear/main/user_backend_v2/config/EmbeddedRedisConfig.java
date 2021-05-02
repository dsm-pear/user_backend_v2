package com.dsmpear.main.user_backend_v2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Configuration
@Profile("test")
public class EmbeddedRedisConfig {

    private static RedisServer redisServer;

    @Value("${spring.redis.port}")
    int redisPort;

    @PostConstruct
    public void runRedis() throws IOException {
        if(redisServer == null) {
            if(getProcess(redisPort) == -1) {
                redisServer = new RedisServer(redisPort);
            } else {
                int port = 10000;
                while(getProcess(port) != -1) {
                    port++;
                }
                redisServer = new RedisServer(port);
            }
            redisServer.start();
        }
    }

    @PreDestroy
    public void stopRedis() {
        if(redisServer != null)
            redisServer.stop();
    }

    private int getProcess(int port) throws IOException {
        Process ps = new ProcessBuilder("cmd", "/c", "netstat -a -o").start();
        BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
        String line;
        while ((line = br.readLine()) != null) {
            if (line.contains(":" + port)) {
                while (line.contains("  ")) {
                    line = line.replaceAll("  ", " ");
                }
                int pid = Integer.parseInt(line.split(" ")[5]);
                ps.destroy();
                return pid;
            }
        }
        return -1;
    }
}