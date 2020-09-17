package com.verizon.trainingdemo.order.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisConfig {

        @Value("${redis.hostname:localhost}")
        private String redisHostName;

        @Value("${redis.port:6379}")
        private int redisPort;

        @Value("${redis.ttl.seconds:1}")
        private int redisDataTTL;

        @Bean
        public LettuceConnectionFactory redisConnectionFactory() {
                return new LettuceConnectionFactory(new RedisStandaloneConfiguration(redisHostName, redisPort));
        }

        @Bean
        public RedisTemplate<Object, Object> redisTemplate() {
                RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
                redisTemplate.setConnectionFactory(redisConnectionFactory());
                redisTemplate.setKeySerializer(new StringRedisSerializer());
                return redisTemplate;
        }

        @Bean
        public RedisCacheManager redisCacheManager(LettuceConnectionFactory lettuceConnectionFactory) {
               /**
                * If we want to use JSON Serialized then use the below config snippet
                */
                RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().disableCachingNullValues()
                                .entryTtl(Duration.ofSeconds(redisDataTTL))
                                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()));

              /**
                * If we want to use JAVA Serialized then use the below config snippet
                */

 //               RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().disableCachingNullValues()
   //                             .entryTtl(Duration.ofSeconds(redisDataTTL))
     //                           .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.java()));

                redisCacheConfiguration.usePrefix();

                  RedisCacheManager build = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(lettuceConnectionFactory)
                                .cacheDefaults(redisCacheConfiguration).build();
                  
                  return build;

        }
}
