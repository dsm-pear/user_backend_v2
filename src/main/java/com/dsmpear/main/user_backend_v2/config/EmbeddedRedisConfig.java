package com.dsmpear.main.user_backend_v2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.util.StringUtils;
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
            if(isAvailableRedisPort()) {
                redisServer = new RedisServer(redisPort);
            } else {
                redisServer = new RedisServer(findAvailablePort());
            }
            redisServer.start();
        }
    }

    @PreDestroy
    public void stopRedis() {
        if(redisServer != null)
            redisServer.stop();
    }

    public int findAvailablePort() throws IOException {

        for (int port = 10000; port <= 65535; port++) {
            Process process = executeGrepProcessCommand(port);
            if (!isRunning(process)) {
                return port;
            }
        }

        throw new IllegalArgumentException("No port avaliable");
    }

    private Process executeGrepProcessCommand(int port) throws IOException {
        String command = String.format("netstat -nat | grep LISTEN|grep %d", port);
        String[] shell = {"/bin/sh", "-c", command};
        return Runtime.getRuntime().exec(shell);
    }

    private boolean isRunning(Process process) {
        String line;
        StringBuilder pidInfo = new StringBuilder();

        try (BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()))) {

            while ((line = input.readLine()) != null) {
                pidInfo.append(line);
            }

        } catch (Exception ignored) {
        }

        return !StringUtils.isEmpty(pidInfo.toString());
    }

    private boolean isAvailableRedisPort() throws IOException {
        Process process = executeGrepProcessCommand(redisPort);
        return !isRunning(process);
    }
}