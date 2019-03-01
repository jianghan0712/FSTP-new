package com.purefun.fstp.core.ignite.cfg;

import java.util.List;

import org.apache.ignite.configuration.CacheConfiguration;

public class IgniteCacheCfg{
	List<CacheConfiguration> cacheConfigurationList = null;
	
	public IgniteCacheCfg() {
		// TODO Auto-generated constructor stub
		
	}

	public List<CacheConfiguration> getCacheConfigurationList() {
		return cacheConfigurationList;
	}

	public void setCacheConfigurationList(List<CacheConfiguration> cacheConfigurationList) {
		this.cacheConfigurationList = cacheConfigurationList;
	}
	
}
