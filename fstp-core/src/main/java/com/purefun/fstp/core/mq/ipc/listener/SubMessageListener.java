package com.purefun.fstp.core.mq.ipc.listener;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import org.slf4j.Logger;

import com.purefun.fstp.core.bo.tool.RPCTool;
import com.purefun.fstp.core.logging.PLogger;

public abstract class SubMessageListener implements ISubListener {
	public Logger log = PLogger.getLogger(SubMessageListener.class);
	
	public SubMessageListener() {
	}
	
	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub		
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

	public abstract void doSubTask(byte[] objMsg);
}
