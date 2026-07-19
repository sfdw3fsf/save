package vn.vnptit.sapi.oneapp.managed;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.integration.support.locks.PassThruLockRegistry;

@Configuration
public class LockRegistryConfig {
    @Value("${distributed-lock.key-prefix:appLock}")
    String prefix;
    @Value("${distributed-lock.expire.age:600000}")
    Long expireAge;

    @Bean
    @ConditionalOnExpression("{'redis','local'}.contains('${distributed-lock.type}'.toLowerCase()) == false")
    LockRegistry emptyLockRegistry() {
        return new PassThruLockRegistry();
    }

    @Bean
    @ConditionalOnExpression("'${distributed-lock.type}'.toLowerCase() == 'redis'")
    RedisLockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory) {
        return new RedisLockRegistry(redisConnectionFactory, prefix, expireAge);
    }
}
