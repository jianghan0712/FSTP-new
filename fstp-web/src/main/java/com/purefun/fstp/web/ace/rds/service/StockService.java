package com.purefun.fstp.web.ace.rds.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.protobuf.InvalidProtocolBufferException;
import com.purefun.fstp.core.bo.RDSStockBO;
import com.purefun.fstp.core.bo.otw.RDSStockBO_OTW;
import com.purefun.fstp.core.mq.ipc.listener.QnsMessageListener;
import com.purefun.fstp.core.mq.ipc.qns.QNSubscriber;
import com.purefun.fstp.web.PWebService;

@Service
public class StockService {
	Map<String, RDSStockBO> repo = new HashMap<String, RDSStockBO>();
	
	@Autowired
	PWebService service;
		
	public Map<String, RDSStockBO> getRepo() {
		return repo;
	}

	public void setRepo(Map<String, RDSStockBO> repo) {
		this.repo = repo;
	}

	public void start() {
		QNSubscriber stockQns = service.getIpcfactory().createQNSubscriber();
		stockQns.QNSubscribe("fstp.ace.rds.server.stock", new StockQnsMessageListener());
	}
	
	class StockQnsMessageListener extends QnsMessageListener{
		Map<String, RDSStockBO> res = new HashMap<String, RDSStockBO>();
		
		@Override
		public void doSubTask(byte[] objMsg) {
			// TODO Auto-generated method stub
			try {
				RDSStockBO_OTW e = new RDSStockBO_OTW(objMsg);
				repo.put(e.getProduct_id(), e.getBo());
			} catch (InvalidProtocolBufferException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void doQueryTask(byte[] objMsg) {
			// TODO Auto-generated method stub
			try {
				RDSStockBO_OTW e = new RDSStockBO_OTW(objMsg);
				repo.put(e.getProduct_id(), e.getBo());
			} catch (InvalidProtocolBufferException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
