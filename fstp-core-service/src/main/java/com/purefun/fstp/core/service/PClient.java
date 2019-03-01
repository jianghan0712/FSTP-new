package com.purefun.fstp.core.service;

import java.io.IOException;

import javax.jms.Session;

import com.purefun.fstp.core.configuration.FstpConfiguration;


public class PClient extends PCommonService{

	public PClient(FstpConfiguration config) {	
		super();
		property = PProperty.property;
		this.serverName = property.fullName;	
		this.isServer = config.getIsServer();
		this.config = config;
	}
	
	public void init() {
		super.init();
		serviceExitTest untitled11 = new serviceExitTest(this.session, this.serverName);
		untitled11.thread.start();
	}
	
	public void start() {
		super.start();
	}
	
	public class serviceExitTest {
		ExitThread thread = new ExitThread();
		public serviceExitTest(Session session,String serverName) {
			doShutDownWork(session,serverName);
		}

		public class ExitThread extends Thread {
		    public void run() {
		        try {
		            System.in.read();
		        } catch (IOException e) {  
		            e.printStackTrace();
		        }  
		        System.exit(0);  
		    }
		}
		
		private void doShutDownWork(Session session,String serverName) {
			Runtime.getRuntime().addShutdownHook(new Thread() {
				  public void run(){
					      log.info("service exit");
					  } 			  			       
			});
		}	
	}

}
