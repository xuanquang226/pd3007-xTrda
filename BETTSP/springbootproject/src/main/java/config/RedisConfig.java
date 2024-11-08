package config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableCaching
public class RedisConfig {

    // @Bean
    // public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory
    // connectionFactory) {
    // RedisTemplate<String, Object> template = new RedisTemplate<>();
    // template.setConnectionFactory(connectionFactory);
    // template.setKeySerializer(new StringRedisSerializer());
    // template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
    // return template;
    // }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration("localhost", 6379);
        return new JedisConnectionFactory(configuration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }
}
