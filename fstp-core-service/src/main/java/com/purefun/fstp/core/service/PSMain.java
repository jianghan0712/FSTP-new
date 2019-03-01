package com.purefun.fstp.core.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.apache.ignite.springdata.repository.config.EnableIgniteRepositories;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
@EnableIgniteRepositories
@ImportResource("classpath:config/${ServiceName}/${Env}/${Instance}/config.xml")
public class PSMain {
	
	public static void main(String[] args) {
		PProperty.getProperty();
		SpringApplication.run(PSMain.class, args);
					
		ApplicationContext beanFactory = SpringUtil.applicationContext;
		PService server = (PService)beanFactory.getBean("MainServer");
		server.setBeanFactory(beanFactory);
		
		server.init();
		server.start();
		
	}
}
