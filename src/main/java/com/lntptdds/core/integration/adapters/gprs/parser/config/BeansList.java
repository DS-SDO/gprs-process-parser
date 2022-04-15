package com.lntptdds.core.integration.adapters.gprs.parser.config;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.lntptdds.core.integration.adapters.gprs.parser.config.RedisMessageSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Configuration
@ComponentScan("com.lntptdds.core.integration.adapters.gprs.parser")

@PropertySource("classpath:application.properties")
public class BeansList {

    public BeansList(){}

    List<String> unitIdList = JsonLoader.jsonMapUnits();
    ArrayList<String> unitIdList1 = new ArrayList<String>(Arrays.asList("1111","2222","3333","4444","5555"));
    int a = unitIdList.size();

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

//    @Bean
//    JedisConnectionFactory jedisConnectionFactory1() {
//        return new JedisConnectionFactory();
//    }


    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
        return template;
    }

//    @Bean
//    public RedisTemplate<String, Object> redisTemplate1() {
//        final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
//        template.setConnectionFactory(jedisConnectionFactory1());
//        template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
//        return template;
//    }

//    @Bean
//    MessageListenerAdapter messageListener() {
//        return new MessageListenerAdapter(new RedisMessageSubscriber(redisTemplate()));
//    }


    @Bean
    MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(new RedisMessageSubscriber(redisPublisher()));
    }

    @Bean
    RedisMessageListenerContainer redisContainer() {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory());
        for (int i=0;i<a;i++){
        container.addMessageListener(new MessageListenerAdapter(new RedisMessageSubscriber(new RedisMessagePublisher
                (redisTemplate(),new ChannelTopic(unitIdList.get(i)+"_MEASUREMENT")))),
                new ChannelTopic(unitIdList.get(i)+"_FRAME"));}

        return container;
    }

    @Bean
    RedisMessagePublisher redisPublisher() {
        return new RedisMessagePublisher(redisTemplate(), topic1());
    }

    @Bean
    ChannelTopic topic() {
        return new ChannelTopic("2222_FRAME");
    }

    @Bean
    ChannelTopic topic1() {
        return new ChannelTopic("MEASUREMENT");
    }
}