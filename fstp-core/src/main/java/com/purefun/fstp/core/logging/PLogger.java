package com.purefun.fstp.core.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/*
 *	FSTP的日志模块，每一个服务启动后，默认位置：logging/{serverName}_{env}_{instance}.log
 *	使用方法：PLogger.getLogger(this.class)
 * 
 */
public class PLogger{
	private Logger logger;
	
	public PLogger(Class<?> server) {
	}
	
	public static Logger getLogger(Class<?> classz) {
		String className = classz.getName();
		className = className.substring(className.lastIndexOf(".")+1);
		
		return LoggerFactory.getLogger(classz);
	}
	public org.slf4j.Logger getLogger() {
		return logger;
	}
	
}
