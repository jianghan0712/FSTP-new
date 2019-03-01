package com.purefun.fstp.core.mq.ipc.sub;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import org.slf4j.Logger;

import com.purefun.fstp.core.cache.ICommonCache;
import com.purefun.fstp.core.logging.PLogger;
import com.purefun.fstp.core.mq.ipc.listener.ISubListener;
import com.purefun.fstp.core.mq.ipc.listener.SubMessageListener;

public class SubscriberForJMS implements Subscriber{
	Logger log = PLogger.getLogger(SubscriberForJMS.class);
	Session session = null;
	ICommonCache cache = null;
	
	public SubscriberForJMS(Session session) {
		this.session = session;
	}
	
	public void subscribe(String topic, ISubListener msglisteneer) {
		try {
			Destination destination = session.createTopic(topic);
			MessageConsumer messageConsumer = session.createConsumer(destination);
			log.info("subscribe topic : {}",topic);
		
		    messageConsumer.setMessageListener(msglisteneer);	
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}         
	}
}
