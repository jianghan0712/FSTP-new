package com.purefun.fstp.web.kafka;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
//@AutoConfigureAfter({Receiver.class})
public class SubscriberConfig {
	
	@Bean
	public RedisTemplate<String, byte[]> redisTemplate(RedisConnectionFactory redisConnectionFactory) {

		RedisTemplate<String, byte[]> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);

		// 设置value的序列化规则和 key的序列化规则
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new BytesRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new BytesRedisSerializer());
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}
 
    /**
     * 消息监听适配器，注入接受消息方法，输入方法名字 反射方法
     *
     * @param receiver
     * @return
     */
    @Bean
    public MessageListenerAdapter getMessageListenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage"); //当没有继承MessageListener时需要写方法名字
    }
 
    /**
     * 创建消息监听容器
     *
     * @param redisConnectionFactory
     * @param messageListenerAdapter
     * @return
     */
    @Bean
    public RedisMessageListenerContainer getRedisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory, MessageListenerAdapter messageListenerAdapter) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        redisMessageListenerContainer.addMessageListener(messageListenerAdapter, new PatternTopic("riskResult"));
        return redisMessageListenerContainer;
    }
}