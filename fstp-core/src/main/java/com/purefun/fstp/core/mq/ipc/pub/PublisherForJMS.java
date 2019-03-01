package com.purefun.fstp.core.mq.ipc.pub;

import javax.jms.BytesMessage;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import org.slf4j.Logger;

import com.purefun.fstp.core.bo.commom.ICommom_OTW;
import com.purefun.fstp.core.cache.ICommonCache;
import com.purefun.fstp.core.cache.ignite.IgniteCacheImp;
import com.purefun.fstp.core.cache.redis.RedisCacheImp;
import com.purefun.fstp.core.constant.PublishMode;
import com.purefun.fstp.core.logging.PLogger;

public class PublisherForJMS implements Publisher{
	Logger log = PLogger.getLogger(PublisherForJMS.class);
	Session session = null;
//	ICommonCache cache = null;
	IgniteCacheImp cache = null;
	
	public PublisherForJMS(Session session, IgniteCacheImp cache) {
		this.session = session;
		this.cache = cache;
	}

	public void publish(ICommom_OTW bo,int mode) {
		if(session == null) {
			log.error("There is no useful connect to broker");
			return;
		}
		
		try {
			Destination destination = session.createTopic(bo.getDestination());
			MessageProducer messageProducer = session.createProducer(destination);
			
        	BytesMessage message = session.createBytesMessage();
        	message.writeBytes(bo.getBuilder().build().toByteArray());
        	      	
            messageProducer.send(message);
            if(mode == PublishMode.PUBLISH_AND_DUR)
            	durableInCache(bo);
            log.info("publish BO:[{}]",bo.toString());
            
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
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
