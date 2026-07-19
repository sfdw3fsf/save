package vn.vnptit.sapi.oneapp.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RedisRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public Object getKeyValue(Object key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void setKeyValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void setKeyValueExpire(String key, Object value, Long expireMinute) {
        redisTemplate.opsForValue().set(key, value, Duration.ofMinutes(expireMinute));
    }

    public List<Object> getListValue(String name, long start, long end) {
        return redisTemplate.opsForList().range(name, start, end);
    }

    public List<Object> getListValue(String name) {
        return redisTemplate.opsForList().range(name, 0, -1);
    }

    public void pushListValue(String name, List<Object> listData) {
        redisTemplate.opsForList().rightPushAll(name, listData);
    }

    public void pushListValue(String name, Object data) {
        redisTemplate.opsForList().rightPush(name, data);
    }

    public void removeKey(String name) {
        redisTemplate.delete(name);
    }

    public boolean checkKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public List<String> findKeysWithSubstring(String substring) {
        List<String> keysWithSubstring = new ArrayList<>();
        ScanOptions scanOptions = ScanOptions.scanOptions().match("*" + substring + "*").count(100).build();
        
        Cursor<byte[]> cursor = redisTemplate.executeWithStickyConnection(
                redisConnection -> redisConnection.scan(scanOptions));
        
        while (cursor.hasNext()) {
            keysWithSubstring.add(new String(cursor.next()));
        }
        
        return keysWithSubstring;
    }
}


