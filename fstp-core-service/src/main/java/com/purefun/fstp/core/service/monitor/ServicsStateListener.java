package com.purefun.fstp.core.service.monitor;

import org.slf4j.Logger;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceCanceledEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRegisteredEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRenewedEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaRegistryAvailableEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.netflix.appinfo.InstanceInfo;
import com.purefun.fstp.core.bo.model.BOinstance;
import com.purefun.fstp.core.cache.redis.RedisCacheImp;
import com.purefun.fstp.core.constant.ServiceStatusConstant;
import com.purefun.fstp.core.logging.PLogger;

@Component
public class ServicsStateListener {
	private Logger log = PLogger.getLogger(ServicsStateListener.class);
	public MonitorService service = null;
	
	@EventListener
    public void listen(EurekaInstanceCanceledEvent event) {
    	String serverFullName = event.getAppName();
    	String serverName = event.getServerId().substring(event.getServerId().indexOf(":"), event.getServerId().lastIndexOf(":"));
    	
    	service.getOnlineServerMap().remove(serverFullName);
		boolean deleteFlag = true;
		//如果该service的所有实例都已经down了，需要把cache中对应的历史bo删除掉
		for(String key : service.getOnlineServerMap().keySet()) {
			String name = key.substring(0,key.indexOf("_"));
			if(name.equalsIgnoreCase(serverName)) {
				deleteFlag = false;
				continue;
			}
		}
		if(deleteFlag) {
			BOinstance bo = service.getServiceBOMap().get(serverName);
			if(bo != null && RedisCacheImp.class.isInstance(service.Icache)) {
				log.info("clean cache :{}",bo.getBoEntry());        				
				((RedisCacheImp)service.Icache).delObjct(bo.getBoEntry());
			}        			
		}
    	log.info("[HB] service {} status change to offline", serverName);		    	
	}
	 
    @EventListener
    public void listen(EurekaInstanceRegisteredEvent event) {
        InstanceInfo instanceInfo = event.getInstanceInfo();
        String serverFullName = instanceInfo.getAppName();
        log.info("[HB] Received Registered Event from service: {} ", serverFullName + ":" +  instanceInfo.getPort());
        if(service.getOnlineServerMap().putIfAbsent(serverFullName, ServiceStatusConstant.ONLINE_SERVER)==null) {
			log.info("server {} online ", serverFullName);	        		
		}else {
			log.info("server {} online failure", serverFullName + ":" +  instanceInfo.getPort());
		}		                	
    }
 
    @EventListener
    public void listen(EurekaInstanceRenewedEvent event) {
    	log.info("[HB] Received HB from service: {}", event.getServerId());
    }
 
    @EventListener
    public void listen(EurekaRegistryAvailableEvent event) {
    	log.info("[HB] Registered Center Startup :{}", event.getSource());
    }

	public MonitorService getService() {
		return service;
	}

	public void setService(MonitorService service) {
		this.service = service;
	}
    
    
}
