package com.lntptdds.core.integration.adapters.gprs.parser.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
public class RedisMessagePublisher implements MessagePublisher {

    @Autowired
    RedisTemplate<String, Object> redisTemplate1;
    @Autowired
    ChannelTopic topic;

    public RedisMessagePublisher() {
    }

    public RedisMessagePublisher( RedisTemplate<String, Object> redisTemplate,  ChannelTopic topic) {
        this.redisTemplate1 = redisTemplate;
        this.topic = topic;
    }

    public void publish( String message) {
        redisTemplate1.convertAndSend(topic.getTopic(), message);
    }
}