package com.vn.vnptit.Oneapp_Com.managed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.core.RedisTemplate;
import com.vn.vnptit.Oneapp_Com.redis.RedisProperties;
import org.springframework.stereotype.Component;

@Component("redisManaged")
public class RedisManaged {
	@Autowired
	CacheManager cacheManager;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Value("${spring.redis.cache-prefix:App_common:}")
	private String defaultPrefix;

	@Autowired
	private RedisProperties redisProperties;

	@javax.annotation.PostConstruct
	public void init() {
		System.out.println("[RedisManaged] INITIALIZING with Configuration:");
		if (redisProperties.getCluster() != null && redisProperties.getCluster().getNodes() != null && !redisProperties.getCluster().getNodes().isEmpty()) {
			System.out.println("  - Mode: CLUSTER");
			System.out.println("  - Nodes: " + redisProperties.getCluster().getNodes());
		} else {
			System.out.println("  - Mode: STANDALONE");
			System.out.println("  - Host: " + redisProperties.getHost());
			System.out.println("  - Port: " + redisProperties.getPort());
		}
		System.out.println("  - Prefix: " + defaultPrefix);

		// PRE-WARMING: Warm up connections and serializers/deserializers
		System.out.println("[RedisManaged] PRE-WARMING: Initializing connections and serializers...");
		try {
			ping();
			getFromCache("WARMUP_", "init");
			System.out.println("[RedisManaged] PRE-WARMING: Completed.");
		} catch (Exception e) {
			System.err.println("[RedisManaged] PRE-WARMING: Failed (Redis might be down). Error: " + e.getMessage());
		}
	}

	// Circuit Breaker
	private volatile boolean redisAvailable = true;
	private volatile long lastFailTime = 0;

	// for PROD
	private static final long COOLDOWN_MS = 1800_000; // 30 minutes

	// for DEV
	// private static final long COOLDOWN_MS = 10_000; // 10 seconds

	/**
	 * Check Redis connection
	 */
	public boolean isRedisAvailable() {
		if (redisAvailable) {
			return true;
		}
		long elapsed = System.currentTimeMillis() - lastFailTime;
		if (elapsed > COOLDOWN_MS) {
			System.out.println("[RedisManaged] COOLDOWN EXPIRED (" + (elapsed / 1000) + "s). Attempting to RECOVER Redis connection...");
			return ping();
		}
		return false;
	}

	/**
	 * Check connection
	 */
	private boolean ping() {
		try {
			redisTemplate.execute((RedisConnection connection) -> {
				if (connection instanceof RedisClusterConnection) {
					RedisClusterConnection clusterConn = (RedisClusterConnection) connection;
					Iterable<RedisClusterNode> nodes = clusterConn.clusterGetNodes();
					System.out.println("[RedisManaged] CLUSTER NODE STATUS:");
					for (RedisClusterNode node : nodes) {
						String status = (node.getFlags() != null && !node.getFlags().isEmpty()) ? node.getFlags().toString() : "OK";
						System.out.println("  - Node: " + node.getHost() + ":" + node.getPort() + " [" + node.getType() + "] | Status: " + status);
					}
				}
				connection.ping();
				return null;
			});
			redisAvailable = true;
			return true;
		} catch (Exception e) {
			lastFailTime = System.currentTimeMillis();
			redisAvailable = false;
			System.err.println("[RedisManaged] Ping status: FAILED. Error: " + e.getMessage());
			return false;
		}
	}

	private void markRedisDown(Exception e) {
		redisAvailable = false;
		lastFailTime = System.currentTimeMillis();
		System.err.println("[RedisManaged] Redis CRITICAL: Connection lost or operation failed.");
		System.err.println("[RedisManaged] Detail: " + e.getMessage());
		System.out.println("[RedisManaged] FALLBACK: Routing requests to Database for the next " + (COOLDOWN_MS / 1000) + "s.");
	}

	private String resolvePrefix(String prefix) {
		return defaultPrefix + "/" + ((prefix != null && !prefix.isEmpty()) ? prefix : "");
	}

	public void saveToCache(String prefix, String key, Object value, long minutes) {
		if (!isRedisAvailable())
			return;
		try {
			redisTemplate.opsForValue().set(resolvePrefix(prefix) + key, value, minutes,
					java.util.concurrent.TimeUnit.MINUTES);
		} catch (Exception e) {
			markRedisDown(e);
		}
	}

	public Object getFromCache(String prefix, String key) {
		if (!isRedisAvailable())
			return null;
		try {
			return redisTemplate.opsForValue().get(resolvePrefix(prefix) + key);
		} catch (Exception e) {
			markRedisDown(e);
			return null;
		}
	}

	public void deleteCache(String prefix, String key) {
		if (!isRedisAvailable())
			return;
		try {
			redisTemplate.delete(resolvePrefix(prefix) + key);
		} catch (Exception e) {
			markRedisDown(e);
		}
	}
}
