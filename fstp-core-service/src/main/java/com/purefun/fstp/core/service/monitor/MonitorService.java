package com.purefun.fstp.core.service.monitor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.purefun.fstp.core.bo.model.BOinstance;
import com.purefun.fstp.core.configuration.FstpConfiguration;
import com.purefun.fstp.core.logging.PLogger;
import com.purefun.fstp.core.service.PService;
import com.purefun.fstp.core.service.SpringUtil;

public class MonitorService extends PService{
	public ScheduledExecutorService scheduledQNSThread = Executors.newScheduledThreadPool(1); //QNS解析服务	
	public Map<String,Integer> onlineServerMap = new HashMap<String,Integer>();    //在线service列表
	public Map<String,BOinstance> serviceBOMap = new HashMap<String,BOinstance>(); //<serviceName,BOinstance>
	
	@Deprecated
	public ScheduledExecutorService scheduledHBThread = Executors.newScheduledThreadPool(1);  //心跳服务
	
	public ServicsStateListener stateListener = null;
	
	public MonitorService(FstpConfiguration config) {
		super(config);
		// TODO Auto-generated constructor stub
		log = PLogger.getLogger(MonitorService.class);
		
	}

	public void init() {
		super.init();
		startHBService();	
		stateListener = SpringUtil.getBean(ServicsStateListener.class);
		stateListener.setService(this);
	}
	
	
	public void start() {
		super.start();
	}
	
	@Deprecated
	public class HBThread implements Runnable{		
		@Override
		public void run() {
//			HBServer hb = new HBServer(session, IgniteCache, onlineServerMap,serviceBOMap,"HBTopic");
//			hb.publish();
		}	
	}
	@Deprecated
	private void startHBService() {
		// TODO Auto-generated method stub
		scheduledHBThread.schedule(new HBThread(), 0, TimeUnit.SECONDS);		
	}

	public Map<String, Integer> getOnlineServerMap() {
		return onlineServerMap;
	}

	public Map<String, BOinstance> getServiceBOMap() {
		return serviceBOMap;
	}
			
}
