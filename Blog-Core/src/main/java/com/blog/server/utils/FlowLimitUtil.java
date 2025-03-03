package com.blog.server.utils;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class FlowLimitUtil {

    @Resource
    private StringRedisTemplate template;

    public boolean limitOnceCheck(String key, int limitTime) {

        if (Boolean.TRUE.equals(template.hasKey(key))){
            return false;
        } else {
            template.opsForValue().set(key, "", limitTime, TimeUnit.SECONDS);
            return true;
        }

    }
}
