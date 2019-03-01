package com.purefun.fstp.core.mq.ipc.listener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.slf4j.Logger;

import com.google.protobuf.InvalidProtocolBufferException;
import com.purefun.fstp.core.bo.tool.RPCTool;
import com.purefun.fstp.core.logging.PLogger;

public abstract class QnsMessageListener implements IQueryListener,ISubListener {
	public Logger log = PLogger.getLogger(QnsMessageListener.class);
	public Map<String, List> queryMap = null;
	QueryMessageListener qListener = null;
	SubMessageListener sListener=null;
	
	public QnsMessageListener() {
		
	}

	//如果query和sub处理逻辑不同，目前只好分别处理
	public QnsMessageListener(QueryMessageListener qListener, SubMessageListener sListener) {
		this.qListener = qListener;
		this.sListener = sListener;
	}
	
	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		if(qListener!=null && sListener!=null) {
			//已在qns端处理
		}else {
			BytesMessage objMsg = (BytesMessage) message;
			try {
				byte[] byteArray = new byte[1024];
	        	int len = -1;
	        	len = objMsg.readBytes(byteArray);
	        	if(len == -1){ 
	        		return;
	        	}
	        	doSubTask(RPCTool.subBytes(byteArray, 0, len));
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}		
	}
	
	public abstract void doSubTask(byte[] objMsg);
	
	public abstract void doQueryTask(byte[] objMsg);

	public QueryMessageListener getqListener() {
		return qListener;
	}

	public void setqListener(QueryMessageListener qListener) {
		this.qListener = qListener;
	}

	public SubMessageListener getsListener() {
		return sListener;
	}

	public void setsListener(SubMessageListener sListener) {
		this.sListener = sListener;
	}

}
