package com.purefun.fstp.core.service.testservice;

import com.google.protobuf.InvalidProtocolBufferException;
import com.purefun.fstp.core.bo.CommandBO;
import com.purefun.fstp.core.bo.TestBO;
import com.purefun.fstp.core.bo.TestBO2;
import com.purefun.fstp.core.bo.commom.ICommom_OTW;
import com.purefun.fstp.core.bo.otw.BaseBO_OTW;
import com.purefun.fstp.core.bo.otw.CommandBO_OTW;
import com.purefun.fstp.core.bo.otw.RDSStockBO_OTW;
import com.purefun.fstp.core.bo.otw.TestBO2_OTW;
import com.purefun.fstp.core.bo.otw.TestBO_OTW;
import com.purefun.fstp.core.bo.tool.BoFactory;
import com.purefun.fstp.core.configuration.FstpConfiguration;
import com.purefun.fstp.core.constant.PublishMode;
import com.purefun.fstp.core.logging.PLogger;
import com.purefun.fstp.core.mq.ipc.listener.QnsMessageListener;
import com.purefun.fstp.core.mq.ipc.listener.QueryMessageListener;
import com.purefun.fstp.core.mq.ipc.listener.SubMessageListener;
import com.purefun.fstp.core.mq.ipc.pub.Publisher;
import com.purefun.fstp.core.mq.ipc.query.Query;
import com.purefun.fstp.core.service.PClient;

public class TestService extends PClient{
	
	
	public TestService(FstpConfiguration config) {
		super(config);
		// TODO Auto-generated constructor stub
		log = PLogger.getLogger(TestService.class);
	}
	
	public void start() {
//		Subscriber a = this.rpcfactory.createSubscriber();
//		a.QNS(listener);
//		a.subscribe("fstp.core.rpc.*", new ExampleSubListener(log));
		Publisher a = this.ipcfactory.createPublisherJMS();
		CommandBO_OTW bo = (CommandBO_OTW)BoFactory.createBo(CommandBO.class);
		bo.setParm0("fstp_stock_bar_中成药");
		bo.setParm1("000423");
		a.publish(bo, PublishMode.PUBLISH_ONLY);
//		a.publish((TestBO_OTW)BoFactory.createBo(TestBO.class), PublishMode.PUBLISH_AND_DUR);
//		a.publish((TestBO2_OTW)BoFactory.createBo(TestBO2.class), PublishMode.PUBLISH_AND_DUR);
//		QNSubscriber B = this.ipcfactory.createQNSubscriber();
//		B.QNSubscribe("fstp.core.rpc.*", new ExampleQNSListener(new ExampleQueryListener(),new ExampleSubListener()));
//		Query q = new Query(session);
//		q.query("fstp.ace.rds.server.stock", new ExampleQueryListener());
	}
	
	public class ExampleSubListener extends SubMessageListener{

		public ExampleSubListener() {
			super();
			// TODO Auto-generated constructor stub		
		}

		@Override
		public void doSubTask(byte[] objMsg) {
			// TODO Auto-generated method stub
			try {
				ICommom_OTW r = new BaseBO_OTW(objMsg);
				RDSStockBO_OTW e = new RDSStockBO_OTW(objMsg);
				log.info(e.getSecu_name_cn());
//				if(r.getDestination().equalsIgnoreCase("fstp.core.rpc.testone")) {
//					log.info("this is a TestBO from sub");
//					TestBO_OTW e = new TestBO_OTW(objMsg);				
//					log.info(e.getDestination());
//				}else if(r.getDestination().equalsIgnoreCase("fstp.core.rpc.testtwo")) {
//					TestBO2_OTW e = new TestBO2_OTW(objMsg);
//					log.info("this is a TestBO2 from sub");
//					log.info(e.getDestination());
//				}
			} catch (InvalidProtocolBufferException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
		}		
	}
	public class ExampleQueryListener extends QueryMessageListener{
		public ExampleQueryListener() {
			super();
			// TODO Auto-generated constructor stub		
		}

		@Override
		public void doQueryTask(byte[] objMsg) {
			// TODO Auto-generated method stub
			try {
				ICommom_OTW r = new BaseBO_OTW(objMsg);
				RDSStockBO_OTW e = new RDSStockBO_OTW(objMsg);
				log.info(e.getSecu_name_cn());
				
//				if(r.getDestination().equalsIgnoreCase("fstp.core.rpc.testone")) {
//					log.info("this is a TestBO from query");
//					TestBO_OTW e = new TestBO_OTW(objMsg);				
//					log.info(e.getDestination());
//				}else if(r.getDestination().equalsIgnoreCase("fstp.core.rpc.testtwo")) {
//					TestBO2_OTW e = new TestBO2_OTW(objMsg);
//					log.info("this is a TestBO2 from query");
//					log.info(e.getDestination());
//				}
			} catch (InvalidProtocolBufferException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
	}
	
	public class ExampleQNSListener extends QnsMessageListener{
		public ExampleQNSListener() {
			super();
			// TODO Auto-generated constructor stub		
		}
		public ExampleQNSListener(QueryMessageListener qListener,SubMessageListener sListener) {
			super(qListener, sListener);
			// TODO Auto-generated constructor stub		
		}

		@Override
		public void doQueryTask(byte[] objMsg) {
			// TODO Auto-generated method stub
			try {
				ICommom_OTW r = new BaseBO_OTW(objMsg);
				if(r.getDestination().equalsIgnoreCase("fstp.core.rpc.testone")) {
					log.info("this is a TestBO from query");
					TestBO_OTW e = new TestBO_OTW(objMsg);				
					log.info(e.getDestination());
				}else if(r.getDestination().equalsIgnoreCase("fstp.core.rpc.testtwo")) {
					TestBO2_OTW e = new TestBO2_OTW(objMsg);
					log.info("this is a TestBO2 from query");
					log.info(e.getDestination());
				}
			} catch (InvalidProtocolBufferException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		}

		@Override
		public void doSubTask(byte[] objMsg) {
			// TODO Auto-generated method stub
			try {
				ICommom_OTW r = new BaseBO_OTW(objMsg);
				if(r.getDestination().equalsIgnoreCase("fstp.core.rpc.testone")) {
					log.info("this is a TestBO from sub");
					TestBO_OTW e = new TestBO_OTW(objMsg);				
					log.info(e.getDestination());
				}else if(r.getDestination().equalsIgnoreCase("fstp.core.rpc.testtwo")) {
					TestBO2_OTW e = new TestBO2_OTW(objMsg);
					log.info("this is a TestBO2 from sub");
					log.info(e.getDestination());
				}
			} catch (InvalidProtocolBufferException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
