package com.purefun.fstp.core.service;

import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.purefun.fstp.core.logging.PLogger;

@Component
public class SpringUtil implements ApplicationContextAware {
	private static Logger logger = PLogger.getLogger(SpringUtil.class);
	public static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if(SpringUtil.applicationContext == null) {
			SpringUtil.applicationContext = applicationContext;
		}
		logger.info("ApplicationContext配置成功,applicationContext对象："+SpringUtil.applicationContext);
//		String[] a = applicationContext.getBeanDefinitionNames();
//		for(String e:a) {
//			System.out.println(e);
//		}
	}
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	public static Object getBean(String name) {
		return getApplicationContext().getBean(name);
	}
	public static <T> T getBean(Class<T> clazz) {
		return getApplicationContext().getBean(clazz);
	}
	public static <T> T getBean(String name,Class<T> clazz) {
		return getApplicationContext().getBean(name,clazz);

	}
}
