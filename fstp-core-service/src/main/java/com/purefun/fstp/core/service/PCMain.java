package com.purefun.fstp.core.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.apache.ignite.springdata.repository.config.EnableIgniteRepositories;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaClient
@EnableIgniteRepositories
@ImportResource("classpath:config/${ServiceName}/${Env}/${Instance}/config.xml")
public class PCMain {
	
	public static void main(String[] args) {
		PProperty.getProperty();
		SpringApplication.run(PCMain.class, args);
					
		ApplicationContext beanFactory = SpringUtil.applicationContext;
		PClient server = (PClient)beanFactory.getBean("MainServer");
		server.setBeanFactory(beanFactory);
		
		server.init();
		server.start();
		
	}
}
