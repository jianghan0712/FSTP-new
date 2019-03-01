package com.purefun.fstp.core.mq.ipc.listener;

import javax.jms.MessageListener;

public interface ISubListener extends MessageListener{
	public void doSubTask(byte[] objMsg);
	
}
