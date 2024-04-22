package com.library.Library.configurations;

import com.library.Library.entities.Book;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.Jedis;

@Configuration
public class AppConfig {

    @Bean
    public Jedis jedisConnectionFactory()
    {
           Jedis jedis = new Jedis("redis://localhost:6379");
        return jedis;
    }
}
