package com.purefun.fstp.core.mq;

import java.net.MalformedURLException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;

import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.apache.qpid.amqp_1_0.jms.impl.ConnectionFactoryImpl;

import com.purefun.fstp.core.logging.PLogger;
/**
 * MQ工厂，目前支持ActiveMQ和Qpid，均使用AMQP协议。在{@link FstpConfiguration}的MQtype进行配置；
 * 或者在serice对应的config.xml中进行配置。
 * <p>不建议用户自行使用MQConnectFactory来做自定义的连接MQ的操作，FSTP已经集成了
 * 
 * @author Jianghan
 */

public class MQConnectFactory {
	Logger log = PLogger.getLogger(MQConnectFactory.class);
	Connection connection = null;
	Session session = null;
	String MQtype = null;
	
	public MQConnectFactory(String MQtype) {
		this.MQtype = MQtype;
	}
	
	public Session connect() {
		Session session = null;
		ConnectionFactory connectionFactory = null;
		
		try {
			ApplicationContext beanFactory=new ClassPathXmlApplicationContext("comConf/mq/MQConnect.xml");
			
			if(MQtype.equalsIgnoreCase("ActiveMQ")) {
//				org.apache.qpid.client.AMQConnectionFactory ActiveMqConnectionFactory = beanFactory.getBean(org.apache.qpid.client.AMQConnectionFactory.class);
//				connectionFactory = ActiveMqConnectionFactory;
				ConnectionFactoryImpl ActiveMqConnectionFactory = ConnectionFactoryImpl.createFromURL("amqp://localhost:5672");
				connectionFactory = ActiveMqConnectionFactory;
			}else if(MQtype.equalsIgnoreCase("Qpid")) {
				org.apache.qpid.client.AMQConnectionFactory QpidConnectionFactory = beanFactory.getBean(org.apache.qpid.client.AMQConnectionFactory.class);
				connectionFactory = QpidConnectionFactory;
			}
			
			if(connectionFactory==null) {
				log.error("no {} config here ", MQtype);
			}else{
				connection = connectionFactory.createConnection();
				if(connection==null) {
					log.error("no {} broker here ", MQtype);
				}else {
					connection.start();          
					session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
				}	       
			}
					
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.session = session;
		
		return this.session;
	}
	
	public void disconnect() {
		try {
			connection.close();
			session.close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
