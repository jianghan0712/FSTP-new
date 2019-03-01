package com.purefun.fstp.core.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.jms.Session;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.slf4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.kafka.core.KafkaTemplate;

import com.purefun.fstp.core.bo.otw.ServerStatsBO_OTW;
import com.purefun.fstp.core.cache.ICommonCache;
import com.purefun.fstp.core.cache.ignite.IgniteCacheImp;
import com.purefun.fstp.core.cache.redis.RedisCacheImp;
import com.purefun.fstp.core.configuration.FstpConfiguration;
import com.purefun.fstp.core.constant.ServiceStatusConstant;
import com.purefun.fstp.core.ignite.cfg.IgniteCfg;
import com.purefun.fstp.core.mq.MQConnectFactory;
import com.purefun.fstp.core.mq.ipc.IpcFactory;
import com.purefun.fstp.core.mq.ipc.query.QueryService;
import com.purefun.fstp.core.service.heartbeat.HBClient;

public class PCommonService {
	public static PProperty property ;
	protected String serverName = null;
	public static Logger log = null;
	protected boolean isServer = true;
	protected BeanFactory beanFactory = null;
	protected IpcFactory ipcfactory = null;
	
	protected Session session = null;
	protected MQConnectFactory connection = null;

	@Deprecated
	protected HBClient hb = null;
	@Deprecated
	public ScheduledExecutorService HBThreadPool = Executors.newScheduledThreadPool(1); 
	
	public ScheduledExecutorService scheduledQueryThread = Executors.newScheduledThreadPool(1);  //查询请求服务
	public Map<String,String> ErrMap = null;
	public Map<String,String> managerBOMap = null;
	
	public Ignite ignite = null;
	//剥离redis,逐渐弃用下面的Icache统一接口
	public IgniteCacheImp IgniteCache = null;
	public ICommonCache Icache = null;
	
	protected FstpConfiguration config = null;
	
	public PCommonService() {		
	}
	
	public PCommonService(FstpConfiguration config) {		
	}
	
	public void init() {		
		log.info("------------------------------------------------------------------------");
		log.info("|                 This is Free & Super Trading Platform                |");
		log.info("------------------------------------------------------------------------");
		log.info("Create a new FSTP Server:{}",serverName);

		connection = new MQConnectFactory(config.getMQType());
		
		/**********		STEP 1: create ignite/redis 		***************/
		if(config.getCacheType().equalsIgnoreCase("ignite")) {
			IgniteCfg cfg = beanFactory.getBean(IgniteCfg.class);
			cfg.init(log);
			ignite = Ignition.start(cfg.getCfg());
			IgniteCache = new IgniteCacheImp(ignite, (List<CacheConfiguration>)beanFactory.getBean("cacheConfigurationList"));
//	ignite作为默认必须使用的，redis作为后续服务可以新增的
//			Icache = new IgniteCacheImp(ignite, (List<CacheConfiguration>)beanFactory.getBean("cacheConfigurationList"));
		}else if(config.getCacheType().equalsIgnoreCase("redis")) {
			Icache = new RedisCacheImp(log, serverName, beanFactory);
		}
		
		/**********	     STEP 2: Connet to Qpid/ActiveMQ broker	   	***************/
		if(connection!=null)
			session = connection.connect();
		
		/**********	     STEP 3: create rpc factory	   	***************/
		ipcfactory = new IpcFactory(session, IgniteCache, beanFactory.getBean(KafkaTemplate.class));
		
		/**********	     STEP 4: Register to monitor	   	***************/
		/*		Deprecated, use Spring-Cloud
		if(!property.serverName.equalsIgnoreCase("MonitorService")) {
			ServerStatsBO_OTW hbbo = (ServerStatsBO_OTW)BoFactory.createBo(ServerStatsBO.class);
			hbbo.setServername(serverName);
			hbbo.setStatus(ServiceStatusConstant.ONLINE_SERVER);
			hb = new HBClient(log, session, "HBTopic",serverName);
			hb.publish(hbbo);
			HBThreadPool.scheduleAtFixedRate(new HBThread((ServerStatsBO_OTW)BoFactory.createBo(ServerStatsBO.class)), 0, 10, TimeUnit.SECONDS);
		}*/
		/**********	     STEP 5: Query Thread	   	***************/
		scheduledQueryThread.schedule(new QueryService(session, IgniteCache, property.serverName, config.getManagerBOMap()), 0, TimeUnit.SECONDS);

	}

	public void start() {	
		log.info("<====================   [{}] active",property.fullName);
	}
	
	public void stop() {
		
	}	
	
	@Deprecated
	public class HBThread implements Runnable{
		ServerStatsBO_OTW bo = null;
		public HBThread(ServerStatsBO_OTW bo) {
			this.bo = bo;
		}
		
		@Override		
		public void run() {
			// TODO Auto-generated method stub	
			bo.setServername(serverName);
			bo.setStatus(ServiceStatusConstant.HEART_BEAT);
			hb.publish(bo);				
		}		
	}
		
	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	public IpcFactory getIpcfactory() {
		return ipcfactory;
	}

	public void setIpcfactory(IpcFactory ipcfactory) {
		this.ipcfactory = ipcfactory;
	}

	
}
