package com.purefun.fstp.web;

import org.apache.ignite.springdata.repository.config.EnableIgniteRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.purefun.fstp.core.bo.TestBO;
import com.purefun.fstp.core.bo.otw.TestBO_OTW;
import com.purefun.fstp.core.bo.tool.BoFactory;
import com.purefun.fstp.core.configuration.FstpConfiguration;
import com.purefun.fstp.core.constant.PublishMode;
import com.purefun.fstp.core.logging.PLogger;
import com.purefun.fstp.core.mq.ipc.pub.Publisher;
import com.purefun.fstp.core.service.PCommonService;
import com.purefun.fstp.core.service.PProperty;
import com.purefun.fstp.web.ace.rds.service.StockService;
import com.purefun.fstp.web.comm.SpringUtil;

@SpringBootApplication
@EnableEurekaClient
@EnableIgniteRepositories
@ImportResource("classpath:config/${ServiceName}/${Env}/${Instance}/config.xml")
public class PWebService extends PCommonService implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>  {
	@Autowired
	StockService stock;
	
	public PWebService() {		
	}
	
	public PWebService(FstpConfiguration config) {	
		super(config);
		property = PProperty.property;
		this.serverName = property.fullName;	
		this.isServer = config.getIsServer();
		this.config = config;		
	}

	protected void loadConfiguration(FstpConfiguration config) {
		property = PProperty.property;
		this.serverName = property.fullName;	
		this.isServer = config.getIsServer();
		this.config = config;
		log = PLogger.getLogger(PWebService.class);
	}
	
	public void init() {
		super.init();
		TestBO_OTW bo = (TestBO_OTW) BoFactory.createBo(TestBO.class);
		bo.setMsg("3333");
		Publisher pub = ipcfactory.createPublisherKafka();
		pub.publish(bo, PublishMode.PUBLISH_AND_DUR);
	}
	
	public void start() {
		super.start();
		log.info("web server start ok");
		stock.start();
	}

	public static void main(String[] args) {
		PProperty.getProperty();
		SpringApplication.run(PWebService.class, args);
		
		PWebService server = SpringUtil.applicationContext.getBean(PWebService.class);
		server.setBeanFactory(SpringUtil.applicationContext);	
		
		FstpConfiguration configuration = server.getBeanFactory().getBean(FstpConfiguration.class);
		server.loadConfiguration(configuration);		
		
		server.init();
		server.start();
		
	}


	@Override
	public void customize(ConfigurableServletWebServerFactory factory) {
		// TODO Auto-generated method stub
//		factory.setPort(8903);
//		factory.setContextPath("/demo");		
	}
	
}
