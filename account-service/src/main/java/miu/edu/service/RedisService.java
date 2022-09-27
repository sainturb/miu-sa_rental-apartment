package miu.edu.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import miu.edu.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Qualifier("redisTemplate")
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ObjectMapper mapper;

    public boolean isExist(String key) {
        return Objects.nonNull(redisTemplate.opsForValue().get(key));
    }

    public void setValue(final String key, User user) {
        try {
            redisTemplate.opsForValue().set(key, mapper.writeValueAsString(user));
            redisTemplate.expire(key, 2, TimeUnit.DAYS);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public User getValue(final String key) {
        try {
            return mapper.readValue(redisTemplate.opsForValue().get(key), User.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteByKey(String key) {
        redisTemplate.delete(key);
    }
}
