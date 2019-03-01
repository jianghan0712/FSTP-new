package com.purefun.fstp.core.mq.ipc.listener;

import javax.jms.MessageListener;

public interface IQueryListener extends MessageListener{
	public void doQueryTask(byte[] objMsg);
	
}
