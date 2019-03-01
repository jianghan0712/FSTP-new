package com.purefun.fstp.core.mq.ipc.query;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.jms.BytesMessage;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import org.slf4j.Logger;

import com.purefun.fstp.core.bo.otw.QueryRequestBO_OTW;
import com.purefun.fstp.core.cache.ICommonCache;
import com.purefun.fstp.core.cache.ignite.IgniteCacheImp;
import com.purefun.fstp.core.cache.redis.RedisCacheImp;
import com.purefun.fstp.core.logging.PLogger;
import com.purefun.fstp.core.bo.tool.RPCTool;

public class QueryService implements Runnable{
	protected Logger log = PLogger.getLogger(QueryService.class);
	Session session = null;
//	protected ICommonCache cache = null;	
	protected String serviceName = null;
	public Map<String, String> selfTopic = null;//<topic,boname>  config-->managerBOMap
	final String QUERY_TOPIC = "QueryTopic"; 
	protected IgniteCacheImp cache = null;
	
	public QueryService(Session session, IgniteCacheImp cache, String serviceName, Map<String, String> selfTopic) {
		this.session = session;
		this.serviceName = serviceName;
		this.cache = cache;
		this.selfTopic = selfTopic;
	}
		
	@Override
	public void run() {
		if(session == null) {
			log.error("There is no useful connect to broker");
			return;
		}			
		try {
			Destination destination = session.createTopic(QUERY_TOPIC);
			MessageConsumer messageConsumer = session.createConsumer(destination);
	        MessageProducer messageProducer = session.createProducer(null);
			
	        while (true) {
	        	BytesMessage message = (BytesMessage) messageConsumer.receive();
	        	try {
	    			byte[] byteArray = new byte[1024];
	            	int len = -1;
	            	len = message.readBytes(byteArray);
	            	if(len == -1){ 
	            		return;
	            	}
	            	
					QueryRequestBO_OTW queryBO = new QueryRequestBO_OTW(RPCTool.subBytes(byteArray, 0, len));
					String des = queryBO.getQuerytopic();
					List<String> desList = analysis(des);
					
					for(String e:desList) {
						List<byte[]> queResult = queryFromCache(e);
						Destination replyto = null;
						if(queryBO.getTempTopic().equalsIgnoreCase("")) {
							replyto = message.getJMSReplyTo();
						}else {
							replyto = session.createTopic(queryBO.getTempTopic());
						}
		            	if(queResult != null) {
		            		log.info("receive QueryBO topic: {}.",e);
		            		for(byte[] eachbo:queResult) {
			            		BytesMessage responseMessage = session.createBytesMessage();
				            	responseMessage.writeBytes(eachbo);
					        	messageProducer.send(replyto, responseMessage, DeliveryMode.NON_PERSISTENT, Message.DEFAULT_PRIORITY, Message.DEFAULT_TIME_TO_LIVE);	        
			            	}
		            		log.info("publish {} bo.",queResult.size());
		            	}	            	            	
					}				
	    		} catch (JMSException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}	        		        		        		        	 
	        }
	   } catch (Exception exp) {
	           System.out.println("[SERVER] Caught exception, exiting.");
	           exp.printStackTrace(System.out);
	   }          
	}
	
	private List<byte[]> queryFromCache(String query) {
		// TODO Auto-generated method stub
		List<byte[]> ret = null;
		if(selfTopic==null || selfTopic.isEmpty())
			return null;
		for(Map.Entry<String, String> e:selfTopic.entrySet()) {
			if (e.getValue().equalsIgnoreCase(query)) {
				ret = queryFromIgniteCache(e.getKey());
//				if(RedisCacheImp.class.isInstance(cache)) {
//					ret = (List<byte[]>)cache.get(e.getValue(), null);
//				}else if(IgniteCacheImp.class.isInstance(cache)) {					
//					ret = queryFromIgniteCache(e.getKey());
//				}
			}		
		}
		return ret;
	}

	private List<byte[]> queryFromIgniteCache(String boname) {//com.purefun.fstp.core.bo.TestBO
		// TODO Auto-generated method stub
		String[] spiltTemp = boname.split("\\.");
		int len = spiltTemp.length;
		StringBuffer otwBoName = new StringBuffer();
		List<byte[]> ret = new ArrayList<byte[]>(); 
		List tempList = (List) cache.get(boname, null);
		if(tempList == null) {
			return ret;
		}
		
		for(int i = 0;i<len-1;i++)
			otwBoName.append(spiltTemp[i]).append(".");
		
		otwBoName.append("otw.").append(spiltTemp[len-1]).append("_OTW");
		try {
			Class<?> c1 = Class.forName(boname);		
			Class<?> c2 = Class.forName(otwBoName.toString());
			Constructor<?> c = c2.getDeclaredConstructor(c1);
			Method m = c2.getMethod("serial");
			
			for(int i = 0;i<tempList.size();i++) {
				if(c1.isInstance(tempList.get(i))) {			
					byte[] k = (byte[]) m.invoke(c.newInstance(tempList.get(i)));
					ret.add(k);
				}
			}		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return ret;
	}
	
	private List<String> analysis(String qns) {
		int index = qns.indexOf(".*");
		List<String> result = new ArrayList<String>();
		
		if(index == -1) {
			for(Map.Entry<String, String> each:selfTopic.entrySet()) {
				String destination = each.getValue();
				if(destination.equalsIgnoreCase(qns))
					result.add(each.getValue());		
			}
			return result;
		}
				
		String start = qns.substring(0,index);
		for(Map.Entry<String, String> each:selfTopic.entrySet()) {
			String destination = each.getValue();
			if(destination.startsWith(start)) {
				result.add(each.getValue());				
			}
		}
		
		return result;	
	}
	
		
}
