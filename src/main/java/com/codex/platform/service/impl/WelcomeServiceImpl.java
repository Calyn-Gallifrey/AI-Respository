package com.codex.platform.service.impl;

import com.codex.platform.domain.WelcomeMessage;
import com.codex.platform.mapper.WelcomeMessageMapper;
import com.codex.platform.service.WelcomeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class WelcomeServiceImpl implements WelcomeService {

    public static final String DEFAULT_MESSAGE = "欢迎使用Open Ai CodeX 平台服务，世界的边界在这里开始变成无限。";

    private static final String REDIS_KEY = "welcome:message";

    private final WelcomeMessageMapper welcomeMessageMapper;
    private final ValueOperations<String, String> valueOperations;

    private static final Logger log = LoggerFactory.getLogger(WelcomeServiceImpl.class);

    public WelcomeServiceImpl(WelcomeMessageMapper welcomeMessageMapper, RedisTemplate<String, String> redisTemplate) {
        this.welcomeMessageMapper = welcomeMessageMapper;
        this.valueOperations = redisTemplate.opsForValue();
    }

    @Override
    public String fetchWelcomeMessage() {
        try {
            String cachedMessage = valueOperations.get(REDIS_KEY);
            if (cachedMessage != null) {
                return cachedMessage;
            }
        } catch (Exception ex) {
            log.warn("Redis unavailable, using fallback", ex);
        }

        try {
            WelcomeMessage message = welcomeMessageMapper.findTopMessage();
            if (message != null && message.getContent() != null) {
                cacheMessage(message.getContent());
                return message.getContent();
            }
        } catch (DataAccessException ex) {
            log.warn("Database unavailable, using default message", ex);
        }

        cacheMessage(DEFAULT_MESSAGE);
        return DEFAULT_MESSAGE;
    }

    private void cacheMessage(String message) {
        try {
            valueOperations.set(REDIS_KEY, message);
        } catch (Exception ex) {
            log.debug("Skip caching welcome message", ex);
        }
    }
}
