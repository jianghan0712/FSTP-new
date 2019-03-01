package com.purefun.fstp.web.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

@Component
public class Receiver implements MessageListener {
//    private static Logger logger = LoggerFactory.getLogger(Receiver.class);
 
    @Autowired
    private RedisTemplate<String, byte[]> redisTemplate;
 
    @Override
    public void onMessage(Message message, byte[] pattern) {
        RedisSerializer<String> valueSerializer = redisTemplate.getStringSerializer();
        String deserialize = valueSerializer.deserialize(message.getBody());
        System.out.println("收到的mq消息" + deserialize);
    }

}
