package com.ym.config;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.redis.RedisVectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPooled;

@Configuration
public class RedisVectorStoreConfig {

    @Autowired
    private EmbeddingModel embeddingModel;
    @Bean
    public JedisPooled jedisPooled() {
        // 这里可根据 application.yml 配置调整
        return new JedisPooled("localhost", 6379);
    }

    @Bean
    public RedisVectorStore redisVectorStore(JedisPooled jedisPooled) {
        return RedisVectorStore.builder(jedisPooled, embeddingModel)
                .initializeSchema(true) // 可根据需要调整
                .build();
    }
} 