//package vn.vnpt.redis.config;
//
//import java.time.Duration;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cache.annotation.CachingConfigurer;
//import org.springframework.cache.annotation.CachingConfigurerSupport;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cache.interceptor.CacheErrorHandler;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializationContext;
//import org.springframework.data.redis.serializer.RedisSerializer;
//
//import io.lettuce.core.ClientOptions;
//import io.lettuce.core.SocketOptions;
//import java.util.HashMap;
//import java.util.Map;
//import org.springframework.beans.factory.annotation.Autowired;
//
//@Configuration
//@EnableCaching
//public class RedisCacheConfig extends CachingConfigurerSupport implements CachingConfigurer {
//
//	@Value("${spring.redis.host:localhost}")
//	private String redisHost;
//
//	@Value("${spring.redis.port:6379}")
//	private int redisPort;
//
//	@Value("${spring.redis.timeout:1}")
//	private int redisTimeoutInSecs;
//
//	@Value("${redis.socket.timeout.secs:1}")
//	private int redisSocketTimeoutInSecs;
//
//	@Value("${spring.redis.time-to-live:60}")
//	private int redisDataTTL;
//
//	@Value("${spring.redis.database:0}")
//	private int indexDB;
//
//	@Autowired
//	private RedisCache redisCache;
//
//	@Bean
//	public LettuceConnectionFactory redisConnectionFactory() {
//
//		final SocketOptions socketOptions = SocketOptions.builder()
//				.connectTimeout(Duration.ofSeconds(redisSocketTimeoutInSecs)).build();
//
//		final ClientOptions clientOptions = ClientOptions.builder().socketOptions(socketOptions).build();
//
//		LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
//				.commandTimeout(Duration.ofSeconds(redisTimeoutInSecs)).clientOptions(clientOptions).build();
//		RedisStandaloneConfiguration serverConfig = new RedisStandaloneConfiguration(redisHost, redisPort);
//
//		final LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(serverConfig,
//				clientConfig);
//		lettuceConnectionFactory.setDatabase(indexDB);
//		lettuceConnectionFactory.setValidateConnection(true);
//		return lettuceConnectionFactory;
//
//	}
//
//	@Bean
//	public RedisTemplate<Object, Object> redisTemplate() {
//		RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
//		redisTemplate.setConnectionFactory(redisConnectionFactory());
//		return redisTemplate;
//	}
//
//	@SuppressWarnings("unchecked")
//	@Bean
//	public RedisCacheManager redisCacheManager(LettuceConnectionFactory lettuceConnectionFactory) {
//		RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer(getClass().getClassLoader());
//
//		RedisCacheConfiguration redisCacheConfigurationDefault = RedisCacheConfiguration.defaultCacheConfig()
//				.disableCachingNullValues().entryTtl(Duration.ofSeconds(redisDataTTL))
//				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer));
//
//		redisCacheConfigurationDefault.usePrefix();
//
//		@SuppressWarnings("rawtypes")
//		Map<String, RedisCacheConfiguration> cacheConfiguration = new HashMap();
//		redisCache.getSpecs().forEach((spec) -> {
//			RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
//					.disableCachingNullValues().entryTtl(Duration.ofSeconds(spec.getTtl()))
//					.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer));
//
//			redisCacheConfiguration.usePrefix();
//			cacheConfiguration.put(spec.getName(), redisCacheConfiguration);
//		});
//
//		RedisCacheManager redisCacheManager = RedisCacheManager.RedisCacheManagerBuilder
//				.fromConnectionFactory(lettuceConnectionFactory).withInitialCacheConfigurations(cacheConfiguration)
//				.cacheDefaults(redisCacheConfigurationDefault).build();
//
//		redisCacheManager.setTransactionAware(true);
//		return redisCacheManager;
//	}
//
//	@Override
//	public CacheErrorHandler errorHandler() {
//		return new RedisCacheErrorHandler();
//	}
//
////	@Override
////	public CacheManager cacheManager() {
////		// TODO Auto-generated method stub
////		return null;
////	}
////
////	@Override
////	public CacheResolver cacheResolver() {
////		// TODO Auto-generated method stub
////		return null;
////	}
////
////	@Override
////	public KeyGenerator keyGenerator() {
////		// TODO Auto-generated method stub
////		return null;
////	}
//}
