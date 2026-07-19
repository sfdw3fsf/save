package vn.vnptit.sapi.oneapp.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RedisProperties {
    @Getter
    @Setter
    @Value("${redis.cluster-nodes}")
    private List<String> redisClusters;

    @Getter @Setter
    @Value("${redis.host}")
    private String host;

    @Getter @Setter
    @Value("${redis.password}")
    private String password;

    @Getter @Setter
    @Value("${redis.username}")
    private String username;

    @Getter @Setter
    @Value("${redis.port}")
    private String port;

    @Getter @Setter
    @Value("${redis.socket-timeout}")
    private Long socketTimeout;

    @Getter @Setter
    @Value("${redis.command-timeout}")
    private Long commandTimeout;

}
