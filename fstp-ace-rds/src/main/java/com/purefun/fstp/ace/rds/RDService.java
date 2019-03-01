package com.purefun.fstp.ace.rds;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import com.purefun.fstp.core.configuration.FstpConfiguration;
import com.purefun.fstp.core.mq.ipc.pub.Publisher;
import com.purefun.fstp.core.mq.ipc.sub.Subscriber;
import com.purefun.fstp.core.service.PClient;

public abstract class RDService<S,R> extends PClient{
	protected Publisher pub = null;
	protected Subscriber sub = null;
	protected RDSConfig rdsConfig = null;
	
	@Autowired
	protected CrudRepository repo;
	protected Map<String,String> source2rdsMap = null;
	
	public RDService(FstpConfiguration config) {
		super(config);
		// TODO Auto-generated constructor stub
		
	}
	
	public void init() {
		super.init();
		rdsConfig = beanFactory.getBean(RDSConfig.class);		
		source2rdsMap = rdsConfig.getSource2rdsMap();
	}
	
	public void start() {
		super.start();
		
		if (rdsConfig==null) {
			log.error("There need a RDS config");		
		}else {
			loadDataFromDBToCache();
		}
			
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void loadDataFromDBToCache() {
		// TODO Auto-generated method stub
//		try {
//			Class rdsCrud = Class.forName(rdsConfig.getRdsCrud());
//			repo = (CrudRepository)beanFactory.getBean(rdsCrud);
			log.info("---------load all key from DB table:{}---------",rdsConfig.getTableName());
			List<R> list = (List<R>) repo.findAll();

			loadDBdata2CacheImp(list);	    	
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	abstract protected void loadDBdata2CacheImp(List<R> list);
	
	
}
