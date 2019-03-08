package com.purefun.fstp.ace.md;

import com.google.protobuf.InvalidProtocolBufferException;
import com.purefun.fstp.ace.md.container.MDContainer;
import com.purefun.fstp.core.bo.otw.CommandBO_OTW;
import com.purefun.fstp.core.configuration.FstpConfiguration;
import com.purefun.fstp.core.logging.PLogger;
import com.purefun.fstp.core.mq.ipc.listener.SubMessageListener;
import com.purefun.fstp.core.mq.ipc.sub.Subscriber;
import com.purefun.fstp.core.service.PClient;

public class MDService extends PClient{
	String MD_mode;
	MDContainer mdContainer;
	
	public MDService (FstpConfiguration config) {
		super(config);
		log = PLogger.getLogger(MDService.class);
	}
	
	public void init() {
		super.init();
		mdContainer = new MDContainer(MD_mode, ipcfactory.createPublisherJMS());
		if(MD_mode.equalsIgnoreCase("backtest")) {
			Subscriber comSub = ipcfactory.createSubscriberJMS();
			comSub.subscribe("fstp.core.manager.command", new MDListener());
		}
	}
	
	public void start() {
		super.start();
	}

	public String getMD_mode() {
		return MD_mode;
	}

	public void setMD_mode(String mD_mode) {
		MD_mode = mD_mode;
	}
	
	class MDListener extends SubMessageListener{

		@Override
		public void doSubTask(byte[] objMsg) {
			// TODO Auto-generated method stub
			try {
				CommandBO_OTW bo = new CommandBO_OTW(objMsg);
				mdContainer.dealCommand(bo);
			} catch (InvalidProtocolBufferException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
}
