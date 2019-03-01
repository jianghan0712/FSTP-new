package com.purefun.fstp.core.mq.ipc.query;

import javax.jms.BytesMessage;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TemporaryQueue;
import org.slf4j.Logger;

import com.google.protobuf.InvalidProtocolBufferException;
import com.purefun.fstp.core.bo.QueryRequestBO;
import com.purefun.fstp.core.bo.TestBO;
import com.purefun.fstp.core.bo.otw.QueryRequestBO_OTW;
import com.purefun.fstp.core.bo.otw.TestBO_OTW;
import com.purefun.fstp.core.bo.tool.BoFactory;
import com.purefun.fstp.core.logging.PLogger;
import com.purefun.fstp.core.mq.ipc.listener.IQueryListener;
import com.purefun.fstp.core.mq.ipc.listener.QueryMessageListener;

public class Query{
	Logger log = PLogger.getLogger(Query.class);
	Session session = null;
	
	Destination destination = null;
	TemporaryQueue responseQueue = null;
    MessageConsumer messageConsumer = null;
    MessageProducer messageProducer = null;
    
	public Query(Session session) {
		this.session = session;
		
		if(session == null) {
			log.error("There is no useful connect to broker");
			System.exit(1);
		}else {
			try {
				destination = session.createTopic("QueryTopic");
				responseQueue = session.createTemporaryQueue();
				messageConsumer = session.createConsumer(responseQueue);
				messageProducer = session.createProducer(destination);
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}
	
	public void query(String dest, IQueryListener listener) {
		if(session == null) {
			log.error("There is no useful connect to broker");
			return;
		}	

		try {		
			QueryRequestBO_OTW bo = (QueryRequestBO_OTW) BoFactory.createBo(QueryRequestBO.class);
			bo.setQuerytopic(dest);
	        BytesMessage message = session.createBytesMessage();
	        message.writeBytes(bo.serial());
        	message.setJMSReplyTo(responseQueue);
        	       	
        	messageProducer.send(message);
        	
        	messageConsumer.setMessageListener(listener);
	        } catch (Exception exp) {
	           System.out.println("[CLIENT] Caught exception, exiting.");
	           exp.printStackTrace(System.out);
	       }
		        
	}

}
