package utils;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void setString(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void setObject(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void setStringWithExpiry(String key, String value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    public void putValue(String key, String field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    public <T> T getValue(String key, String field, Class<T> clazz) {
        // Object value = redisTemplate.opsForHash().get(key, field);
        // if (value == null) {
        // return null;
        // }
        // return clazz.cast(value);
        return (T) redisTemplate.opsForHash().get(key, field);
    }

    public String getString(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    public <T> T getObject(String key, Class<T> clazz) {
        Object value = redisTemplate.opsForValue().get(key);
        if (value == null) {
            return null;
        }
        return clazz.cast(value);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
