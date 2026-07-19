package com.vn.vnptit.Oneapp_Com.redis;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
public class RedisConfig {
    private final RedisProperties redisProperties;
    @Autowired
    RedisConfig(RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
    }

	public static final int TIME_OUT_REDIS = 500;

    @Bean
    StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return new StringRedisTemplate(redisConnectionFactory);
    }

    @Primary
    @Bean
    @Qualifier(value = "LettuceConnectionFactory")
    public LettuceConnectionFactory lettuceConnectionFactory() {
        final SocketOptions socketOptions = SocketOptions.builder()
                .connectTimeout(Duration.ofMillis(TIME_OUT_REDIS))
                .build();
        final ClientOptions clientOptions = ClientOptions.builder()
                .socketOptions(socketOptions)
                .build();

        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .commandTimeout(Duration.ofMillis(TIME_OUT_REDIS))
                .shutdownTimeout(Duration.ofSeconds(redisProperties.getShutdownTimeout()))
                .clientOptions(clientOptions)
                .build();

        if (redisProperties.getCluster() != null && redisProperties.getCluster().getNodes() != null && !redisProperties.getCluster().getNodes().isEmpty()) {
            RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(redisProperties.getCluster().getNodes());
            if (redisProperties.getPassword() != null && !redisProperties.getPassword().isEmpty()) {
                redisClusterConfiguration.setPassword(RedisPassword.of(redisProperties.getPassword()));
            }

            return new LettuceConnectionFactory(redisClusterConfiguration, clientConfig);
        } else {
            RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(
                    redisProperties.getHost(), Integer.parseInt(redisProperties.getPort())
            );
            redisStandaloneConfiguration.setDatabase(redisProperties.getDatabase());
            if (redisProperties.getPassword() != null && !redisProperties.getPassword().isEmpty()) {
                redisStandaloneConfiguration.setPassword(RedisPassword.of(redisProperties.getPassword()));
            }
            return new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfig);
        }
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory());

        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        // objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.EVERYTHING, JsonTypeInfo.As.PROPERTY);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(jackson2JsonRedisSerializer);

        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        return template;
    }
}
