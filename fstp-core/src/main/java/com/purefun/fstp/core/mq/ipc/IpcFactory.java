package com.purefun.fstp.core.mq.ipc;

import javax.jms.Session;

import org.springframework.kafka.core.KafkaTemplate;

import com.purefun.fstp.core.cache.ignite.IgniteCacheImp;
import com.purefun.fstp.core.mq.ipc.pub.Publisher;
import com.purefun.fstp.core.mq.ipc.pub.PublisherForJMS;
import com.purefun.fstp.core.mq.ipc.pub.PublisherForKafka;
import com.purefun.fstp.core.mq.ipc.qns.QNSubscriber;
import com.purefun.fstp.core.mq.ipc.query.Query;
import com.purefun.fstp.core.mq.ipc.sub.Subscriber;
import com.purefun.fstp.core.mq.ipc.sub.SubscriberForJMS;
import com.purefun.fstp.core.mq.ipc.sub.SubscriberForKafka;

public class IpcFactory {
	static Session session = null;
//	static ICommonCache cache = null;
	static IgniteCacheImp cache = null;
	static KafkaTemplate<String, byte[]> kafkaTemplate = null;
	
	static String type = null;
	
	public IpcFactory(Session session, IgniteCacheImp cache, KafkaTemplate<String, byte[]> kafkaTemplate) {
		IpcFactory.session = session;
		IpcFactory.cache = cache;
		IpcFactory.kafkaTemplate = kafkaTemplate;
	}
	
	public static Publisher createPublisherJMS() {
		Publisher pub= new PublisherForJMS(session, cache);		
		return pub;		
	}
	
	public static Subscriber createSubscriberJMS() {
		Subscriber sub = new SubscriberForJMS(session);
		return sub;		
	}
	
	public static QNSubscriber createQNSubscriber() {
		QNSubscriber qns = new QNSubscriber(session);
		return qns;		
	}
	
	public static Query createQuery() {
		Query query = new Query(session);
		return query;		
	}
	
	public static Publisher createPublisherKafka() {
		Publisher pub= new PublisherForKafka(kafkaTemplate, cache);		
		return pub;		
	}
	
	public static SubscriberForKafka createSubscriberKafka() {
		SubscriberForKafka sub = new SubscriberForKafka();
		return sub;		
	}
}
