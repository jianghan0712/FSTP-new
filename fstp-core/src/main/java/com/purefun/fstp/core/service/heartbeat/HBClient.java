package com.purefun.fstp.core.service.heartbeat;

import java.io.IOException;
import javax.jms.BytesMessage;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TemporaryQueue;
import javax.jms.TextMessage;

import org.slf4j.Logger;

import com.purefun.fstp.core.bo.ServerStatsBO;
import com.purefun.fstp.core.bo.commom.ICommom_OTW;
import com.purefun.fstp.core.bo.otw.ServerStatsBO_OTW;
import com.purefun.fstp.core.constant.ServiceStatusConstant;
import com.purefun.fstp.core.bo.tool.BoFactory;

@Deprecated
public class HBClient{
	static Logger log = null;
	Session session = null;
	String msgdef = null;
	Destination destination = null;
	TemporaryQueue responseQueue = null;
    MessageConsumer messageConsumer = null;
    MessageProducer messageProducer = null;
    String serverName = null;
	
	public HBClient(Logger log,Session session,String topic,String servername) {
		this.log = log;
		this.session = session;
		this.msgdef = topic;
		this.serverName = servername;
		
		if(session == null) {
			log.error("There is no useful connect to broker");
			System.exit(1);
		}else {
			try {
				destination = session.createTopic(msgdef);
				responseQueue = session.createTemporaryQueue();
				messageConsumer = session.createConsumer(responseQueue);
				messageProducer = session.createProducer(destination);	
				
				serviceExitTest untitled11 = new serviceExitTest(this.session, servername);
	    		untitled11.thread.start();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}
	
	public void publish(ICommom_OTW bo) {
		if(session == null) {
			log.error("There is no useful connect to broker");
			return;
		}			
		try {		
			BytesMessage message = session.createBytesMessage();
        	message.writeBytes(bo.getBuilder().build().toByteArray());
        	message.setJMSReplyTo(responseQueue);
        	
        	messageProducer.send(message);
        	
        	TextMessage responseMessage = (TextMessage) messageConsumer.receive(2000);
            if (responseMessage != null) {
            	if (responseMessage.getText().equalsIgnoreCase("0")) {
            		log.info("connect to monitor successful" );
            	}
            } else {
            	log.info("can't connet to MonitorServer,server {} will exit",serverName);
            	System.exit(1);
            }       	        
	        } catch (Exception exp) {
	           System.out.println("[CLIENT] Caught exception, exiting.");
	           exp.printStackTrace(System.out);
	       }          
	}
	
	public class serviceExitTest {
		ExitThread thread = new ExitThread();
		public serviceExitTest(Session session,String serverName) {
			doShutDownWork(session,serverName);
		}

		public class ExitThread extends Thread {
		    public void run() {
		        try {
		            System.in.read();
		        } catch (IOException e) {  
		            e.printStackTrace();
		        }  
		        System.exit(0);  
		    }
		}
		
		private void doShutDownWork(Session session,String serverName) {
			Runtime.getRuntime().addShutdownHook(new Thread() {
				  public void run(){
					  ServerStatsBO_OTW bo = (ServerStatsBO_OTW)BoFactory.createBo(ServerStatsBO.class);
					  bo.setServername(serverName);
					  bo.setStatus(ServiceStatusConstant.OFFLINE_SERVER);
					  Destination destination;
					  try {
						  destination = session.createTopic("HBTopic");
						  TemporaryQueue responseQueue = session.createTemporaryQueue();
						  MessageConsumer messageConsumer = session.createConsumer(responseQueue);
						  MessageProducer messageProducer = session.createProducer(destination);
	
						  BytesMessage message = session.createBytesMessage();
						  message.writeBytes(bo.getBuilder().build().toByteArray());
//					      message.setObject((Serializable) bo); 
					      message.setJMSReplyTo(responseQueue);
					        	
					      messageProducer.send(message);
					      log.info("service exit");
					  } catch (JMSException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
					  }
				  }	
				       
			});
		}	
	}
}
