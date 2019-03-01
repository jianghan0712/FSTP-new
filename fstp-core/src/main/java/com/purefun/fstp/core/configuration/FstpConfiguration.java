package com.purefun.fstp.core.configuration;

import java.util.Map;

import org.springframework.context.annotation.Configuration;

public class FstpConfiguration {
	public String serviceName;	
	public boolean isServer;
	public String cacheType;	
	public String MQType;	
	public boolean kafkaFlag;	
	public Map<String, String> managerBOMap;
	
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public boolean getIsServer() {
		return isServer;
	}
	public void setIsServer(boolean isServer) {
		this.isServer = isServer;
	}
	public String getCacheType() {
		return cacheType;
	}
	public void setCacheType(String cacheType) {
		this.cacheType = cacheType;
	}
	public String getMQType() {
		return MQType;
	}
	public void setMQType(String mQType) {
		MQType = mQType;
	}
	public Map<String, String> getManagerBOMap() {
		return managerBOMap;
	}
	public void setManagerBOMap(Map<String, String> managerBOMap) {
		this.managerBOMap = managerBOMap;
	}
	public boolean isKafkaFlag() {
		return kafkaFlag;
	}
	public void setKafkaFlag(boolean kafkaFlag) {
		this.kafkaFlag = kafkaFlag;
	}
	
}
