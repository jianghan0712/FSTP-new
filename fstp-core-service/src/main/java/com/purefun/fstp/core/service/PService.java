package com.purefun.fstp.core.service;

import com.purefun.fstp.core.configuration.FstpConfiguration;

public class PService extends PCommonService  {
	
	public PService(FstpConfiguration config) {
		super();
		property = PProperty.property;
		this.serverName = property.fullName;	
		this.isServer = config.getIsServer();
		this.config = config;		
	}
	
	public void init() {
		super.init();
	}
	
	public void start() {
		super.start();
	}	
}
