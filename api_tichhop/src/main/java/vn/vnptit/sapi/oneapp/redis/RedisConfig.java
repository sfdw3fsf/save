package vn.vnptit.sapi.oneapp.redis;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
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

    @Bean
    StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return new StringRedisTemplate(redisConnectionFactory);
    }

    @Primary
    @Bean
    @Qualifier(value = "LettuceConnectionFactory")
    public LettuceConnectionFactory lettuceConnectionFactory() {
        final SocketOptions socketOptions = SocketOptions.builder().connectTimeout(Duration.ofSeconds(redisProperties.getSocketTimeout())).build();
        final ClientOptions clientOptions =
                ClientOptions.builder().socketOptions(socketOptions).build();

        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .commandTimeout(Duration.ofSeconds(redisProperties.getCommandTimeout()))
                .clientOptions(clientOptions).build();

         if (redisProperties.getRedisClusters() != null && redisProperties.getRedisClusters().size() > 1) {
             RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(redisProperties.getRedisClusters());
             redisClusterConfiguration.setPassword(RedisPassword.of(redisProperties.getPassword()));
             redisClusterConfiguration.setUsername(redisProperties.getUsername());

             return new LettuceConnectionFactory(redisClusterConfiguration);
         } else {
            RedisStandaloneConfiguration redisStandaloneConfiguration;
            redisStandaloneConfiguration = new RedisStandaloneConfiguration(
                        redisProperties.getHost(), Integer.parseInt(redisProperties.getPort())
                );
            return new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfig);
         }
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory());
        template.setEnableTransactionSupport(true);

        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.EVERYTHING, JsonTypeInfo.As.PROPERTY);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(jackson2JsonRedisSerializer);

        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        return template;
    }
}
