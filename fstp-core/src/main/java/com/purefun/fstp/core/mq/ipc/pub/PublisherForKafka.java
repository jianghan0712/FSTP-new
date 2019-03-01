package com.purefun.fstp.core.mq.ipc.pub;

import javax.jms.BytesMessage;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.purefun.fstp.core.bo.commom.ICommom_OTW;
import com.purefun.fstp.core.cache.ICommonCache;
import com.purefun.fstp.core.cache.ignite.IgniteCacheImp;
import com.purefun.fstp.core.cache.redis.RedisCacheImp;
import com.purefun.fstp.core.constant.PublishMode;
import com.purefun.fstp.core.logging.PLogger;

@Component
public class PublisherForKafka implements Publisher{
	Logger log = PLogger.getLogger(PublisherForKafka.class);
//	Session session = null;
//	ICommonCache cache = null;
	IgniteCacheImp cache = null;
	
    private KafkaTemplate<String, byte[]> kafkaTemplate;
	
	public PublisherForKafka(KafkaTemplate<String, byte[]> kafkaTemplate, IgniteCacheImp cache) {
		this.kafkaTemplate = kafkaTemplate;
		this.cache = cache;
	}

	public void publish(ICommom_OTW bo, int mode) {
		kafkaTemplate.send(bo.getDestination(), bo.getBuilder().build().toByteArray());
		if(mode == PublishMode.PUBLISH_AND_DUR)
        	durableInCache(bo);
        log.info("publish BO:[{}]",bo.toString());
	}
	
	public void durableInCache(ICommom_OTW bo) {
		String cacheName = bo.getBo().getClass().getName();
		cache.put(bo.getBo().getClass(), bo.getUuid(), bo.getBuilder().build().toByteArray());
//		if(RedisCacheImp.class.isInstance(cache))
//			cache.put(cacheName, bo.getUuid(), bo.getBuilder().build().toByteArray());
//		else if(IgniteCacheImp.class.isInstance(cache))
//			cache.put(cacheName, bo.getUuid(), bo.getBo());
	}	
}
