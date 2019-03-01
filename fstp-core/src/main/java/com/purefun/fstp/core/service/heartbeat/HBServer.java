package com.purefun.fstp.core.service.heartbeat;

import java.util.Map;

import javax.jms.BytesMessage;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

//import org.apache.qpid.jms.Session;
import org.slf4j.Logger;

import com.purefun.fstp.core.bo.model.BOinstance;
import com.purefun.fstp.core.bo.otw.ServerStatsBO_OTW;
import com.purefun.fstp.core.cache.ICommonCache;
import com.purefun.fstp.core.cache.redis.RedisCacheImp;
import com.purefun.fstp.core.constant.ServiceStatusConstant;
import com.purefun.fstp.core.logging.PLogger;
//import com.purefun.fstp.core.service.MonitorService;
import com.purefun.fstp.core.bo.tool.RPCTool;

@Deprecated
public class HBServer{
	Logger log = PLogger.getLogger(HBServer.class);
	Session session = null;
	ICommonCache Icache = null;	
	String desname = null;
	Map<String,Integer> onlineServerMap = null;
	Map<String,BOinstance> serviceBOMap = null;
	
	public HBServer(Session session,ICommonCache Icache,Map<String,Integer> onlineServerMap,Map<String,BOinstance> serviceBOMap,String topic) {
		this.session = session;
		this.Icache = Icache;
//		this.monitor = server;
		this.onlineServerMap = onlineServerMap;
		this.serviceBOMap = serviceBOMap;
		this.desname = topic;
	}
	
	public void publish() {
		if(session == null) {
			log.error("There is no useful connect to broker");
			return;
		}
//		Map<String,Integer> onlineServerMap = monitor.getOnlineServerMap();//在线服务列表
//        Map<String,BOinstance> serviceBOMap = monitor.getServiceBOMap();
		try {
			Destination destination = session.createTopic(desname);
			MessageConsumer messageConsumer = session.createConsumer(destination);
	        MessageProducer messageProducer = session.createProducer(null);
	        			
	        while (true) {
	        	BytesMessage message = (BytesMessage)messageConsumer.receive();
	        	byte[] byteArray = new byte[1024];
	        	int len = -1;
	        	len=message.readBytes(byteArray);
	        	
	        	if(len == -1){ 
	        		continue;
	        	}
	        	ServerStatsBO_OTW receiveBO = new ServerStatsBO_OTW(RPCTool.subBytes(byteArray, 0, len));
				String serverFullName = receiveBO.getServername();
				String serverName = serverFullName.substring(0,serverFullName.indexOf("_"));
				int status = receiveBO.getStatus();				
				
				TextMessage responseMessage = null;
	        	if(status == ServiceStatusConstant.ONLINE_SERVER) {
	        		log.info("receive status bo：serverName:{},status:{}",serverFullName,status);
	    			
	    			if(onlineServerMap.putIfAbsent(serverFullName, status)==null) {
	    				log.info("server {} online ",serverFullName);
	    				responseMessage = session.createTextMessage("0");		        		
	    			}else {
	    				log.info("server {} online failure",serverName);
	    				responseMessage = session.createTextMessage("-1");
	    			}	        		        		
	        	}else if(status == ServiceStatusConstant.HEART_BEAT) {
	        		log.info("[HB] Received HB from service: {}", serverFullName);
	        		responseMessage = session.createTextMessage("2");
	        	}else if(status == ServiceStatusConstant.OFFLINE_SERVER) {
	        		onlineServerMap.remove(serverFullName);
	        		boolean deleteFlag = true;
	        		//如果该service的所有实例都已经down了，需要把cache中对应的历史bo删除掉
	        		for(String key : onlineServerMap.keySet()) {
	        			String name = key.substring(0,key.indexOf("_"));
	        			if(name.equalsIgnoreCase(serverName)) {
	        				deleteFlag = false;
	        				continue;
	        			}
	        		}
	        		if(deleteFlag) {
	        			BOinstance bo = serviceBOMap.get(serverName);
	        			if(bo != null && RedisCacheImp.class.isInstance(Icache)) {
	        				log.info("clean cache :{}",bo.getBoEntry());        				
	        				((RedisCacheImp)Icache).delObjct(bo.getBoEntry());
	        			}        			
	        		}
	        		log.info("[HB] service {} status change to offline", serverFullName);
	        		continue;
	        	}           	
	        	messageProducer.send(message.getJMSReplyTo(), responseMessage, DeliveryMode.NON_PERSISTENT, Message.DEFAULT_PRIORITY, Message.DEFAULT_TIME_TO_LIVE);
	       }

	   } catch (Exception exp) {
	           System.out.println("[SERVER] Caught exception, exiting.");
	           exp.printStackTrace(System.out);
	   }          
	}
		
}
