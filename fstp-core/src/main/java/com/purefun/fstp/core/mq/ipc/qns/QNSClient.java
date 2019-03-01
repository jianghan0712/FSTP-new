package com.purefun.fstp.core.mq.ipc.qns;

import javax.jms.BytesMessage;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TemporaryQueue;
import javax.jms.TextMessage;

import org.slf4j.Logger;

import com.purefun.fstp.core.bo.commom.ICommom_OTW;
import com.purefun.fstp.core.bo.otw.QNSRespondBO_OTW;
import com.purefun.fstp.core.bo.tool.RPCTool;
import com.purefun.fstp.core.logging.PLogger;

public class QNSClient{
	static Logger log = PLogger.getLogger(QNSClient.class);
	Session session = null;
	String msgdef = null;
	
	Destination destination = null;
	TemporaryQueue responseQueue = null;
    MessageConsumer messageConsumer = null;
    MessageProducer messageProducer = null;
    
    String serverName = null;
    
    String[] topics = null;
	
	public QNSClient(Session session,String topic,String servername) {
		this.session = session;
		this.msgdef = topic;
		
		if(session == null) {
			log.error("There is no useful connect to broker");
			System.exit(1);
		}else {
			try {
				destination = session.createTopic(msgdef);
				responseQueue = session.createTemporaryQueue();
				messageConsumer = session.createConsumer(responseQueue);
				messageProducer = session.createProducer(destination);								
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}
	
	public String[] publish(ICommom_OTW bo) {
		if(session == null) {
			log.error("There is no useful connect to broker");
			return null;
		}	
		String respond = null;
		String ret[] = null;
		try {		
        	BytesMessage message = session.createBytesMessage();
        	message.writeBytes(bo.getBuilder().build().toByteArray());
        	message.setJMSReplyTo(responseQueue);
        	
        	messageProducer.send(message);
        	
        	BytesMessage responseMessage = (BytesMessage)messageConsumer.receive(5000);
//        	TextMessage responseMessage = (TextMessage) messageConsumer.receive(5000);
            if (responseMessage != null) {
            	byte[] byteArray = new byte[1024];
            	int len = -1;
            	len = responseMessage.readBytes(byteArray);
            	if(len == -1){ 
            		return null;
            	}
            	QNSRespondBO_OTW respondbo = new QNSRespondBO_OTW(RPCTool.subBytes(byteArray, 0, len));
//            	respond = responseMessage.getText();
            	getTopics(respondbo.getRespond());
            } else {
            	log.info("query failure");
            }       	        
	        } catch (Exception exp) {
	           System.out.println("[CLIENT] Caught exception, exiting.");
	           exp.printStackTrace(System.out);
	       }
		
		return topics;          
	}

	private void getTopics(String respond) {
		while(respond==null) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		String temp = respond;
		if(temp.equalsIgnoreCase("1")) {
			log.info("format error");
		}else if(temp.equalsIgnoreCase("2")) {
			log.info("bo-destination init error");
		}else {
			topics = temp.split(",");
		}				
	}
}
