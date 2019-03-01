package com.purefun.fstp.core.mq.ipc.qns;

import javax.jms.Session;

import org.slf4j.Logger;

import com.purefun.fstp.core.logging.PLogger;
import com.purefun.fstp.core.mq.ipc.listener.QnsMessageListener;
import com.purefun.fstp.core.mq.ipc.query.Query;
import com.purefun.fstp.core.mq.ipc.sub.Subscriber;
import com.purefun.fstp.core.mq.ipc.sub.SubscriberForJMS;

public class QNSubscriber{
	Logger log = PLogger.getLogger(QNSubscriber.class);
	Session session = null;
	QnsMessageListener qnsListener = null;
	
	Query query = null;
	Subscriber sub = null;
	
	public QNSubscriber(Session session) {
		this.session = session;		
	}

	public void QNSubscribe(String des, QnsMessageListener qnsListener) {
		query = new Query(session);
		sub = new SubscriberForJMS(session);
		this.qnsListener = qnsListener;	
		//如果分别有query和sub监听器，分别处理两种事件
		if(qnsListener.getqListener()!=null && qnsListener.getsListener()!=null) {
			query.query(des, qnsListener.getqListener());
			sub.subscribe(des, qnsListener.getsListener());
		}else {//如果没有特殊要求，使用同一个监听器处理两种事件
			query.query(des, qnsListener);
			sub.subscribe(des, qnsListener);
		}
	}	
	
}
