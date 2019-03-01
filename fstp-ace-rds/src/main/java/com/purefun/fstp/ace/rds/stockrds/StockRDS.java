package com.purefun.fstp.ace.rds.stockrds;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import com.google.protobuf.InvalidProtocolBufferException;
import com.purefun.fstp.ace.rds.RDSSubMessageListener;
import com.purefun.fstp.ace.rds.RDService;
import com.purefun.fstp.core.bo.RDSStockBO;
import com.purefun.fstp.core.bo.SourceStockBO;
import com.purefun.fstp.core.bo.otw.RDSStockBO_OTW;
import com.purefun.fstp.core.bo.otw.SourceStockBO_OTW;
import com.purefun.fstp.core.bo.tool.BoFactory;
import com.purefun.fstp.core.cache.ignite.IgniteCacheImp;
import com.purefun.fstp.core.cache.redis.RedisCacheImp;
import com.purefun.fstp.core.configuration.FstpConfiguration;
import com.purefun.fstp.core.logging.PLogger;
import com.purefun.fstp.core.mq.ipc.IpcFactory;

public class StockRDS extends RDService<SourceStockBO_OTW, RDSStockBO>{

	StockRDSSubListener listener = null;
	
	public StockRDS (FstpConfiguration config) {
		super(config);
		log = PLogger.getLogger(StockRDS.class);
	}
	
	public void init() {
		super.init();
		sub = IpcFactory.createSubscriberJMS();
		listener = new StockRDSSubListener();
		sub.subscribe(rdsConfig.getSourceQns(),  listener);
		
		//publish rdsBO
		pub = IpcFactory.createPublisherJMS();
	}
	
	public void start() {
		super.start();
//		Map a = IgniteCache.getAllEntrys(RDSStockBO.class);
//		a.forEach((k,v)->System.out.println(k));

	}
	
	@Override
	protected void loadDBdata2CacheImp(List<RDSStockBO> list) {
		// TODO Auto-generated method stub
		int count = 0;
    	for(RDSStockBO each : (List<RDSStockBO>)list) {
    		IgniteCache.put(RDSStockBO.class, each.product_id, each);
//    		if(IgniteCacheImp.class.isInstance(Icache)) {
//    			
//    		}else if(RedisCacheImp.class.isInstance(Icache)) {
//    			RDSStockBO_OTW bo = new RDSStockBO_OTW(each);
//    			Icache.put(null, each.getClass().getName(), bo.getBuilder().build().toByteArray());
//    		}
    			
    		log.info("load bo :product_id={}",each.product_id);
    		count++;
    	}
    	log.info("load data from DB to cache successful!!");
    	log.info("Count:{}",count);

	}
	
	class StockRDSSubListener extends RDSSubMessageListener{

		public StockRDSSubListener() {
			super();
		}

		@Override
		protected void doRdsTask(byte[] bobyte) {
			// TODO Auto-generated method stub
			try {
				SourceStockBO_OTW sourcebo = new SourceStockBO_OTW(bobyte);
				RDSStockBO_OTW rdsbo = null;
				RDSStockBO cacheBo = null;
				List list = (List) IgniteCache.get(RDSStockBO.class, sourcebo.getSecu_id()); 
				if(list!=null) {
					cacheBo = (RDSStockBO)list.get(0);
				}
				 
				
				if(cacheBo == null) {
					rdsbo = (RDSStockBO_OTW) BoFactory.createBo(RDSStockBO.class);	
					log.info("There is no product_id={} in cache, we will insert it.",sourcebo.getBo().secu_id);
					insertOrUpdateCacheAndDB(null, sourcebo, false, rdsbo);
				}else {
					rdsbo = new RDSStockBO_OTW(cacheBo);
					String[] ret = ifDupBO(rdsbo, sourcebo);
					if(ret[0]==null) {
						log.info("This is a dup BO:product_id={}, we will ignore it!",sourcebo.getSecu_id());
					}else {
						log.info("Need update,product_id={},fild={},source value={},rds value={}",rdsbo.getProduct_id(), ret[0],ret[1],ret[2]);					
						RDSStockBO_OTW rdsboNew = new RDSStockBO_OTW();
						insertOrUpdateCacheAndDB(rdsbo, sourcebo, true, rdsboNew);
					}					
				}
			
			} catch (InvalidProtocolBufferException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}						
		}

		@SuppressWarnings("unchecked")
		private void insertOrUpdateCacheAndDB(RDSStockBO_OTW rdsboOld, SourceStockBO_OTW sourcebo,boolean isUpdate, RDSStockBO_OTW rdsboNew) {
			// TODO Auto-generated method stub
			for(Map.Entry<String, String> eachMap:source2rdsMap.entrySet()) {
				setMethod(rdsboNew, sourcebo, eachMap);
			}
			if(isUpdate) {				
				repo.deleteById(rdsboOld.getUuid());
				repo.save(rdsboNew.getBo());
				IgniteCache.update(RDSStockBO.class, rdsboNew.getProduct_id(), rdsboNew.getBo());
				log.info("update successful,product_id={}",rdsboNew.getProduct_id());
			}else {
				repo.save(rdsboNew.getBo());
				IgniteCache.put(RDSStockBO.class, rdsboNew.getProduct_id(), rdsboNew.getBo());
				log.info("insert successful,product_id={}",rdsboNew.getProduct_id());
			}		
		}

		private String[] ifDupBO(RDSStockBO_OTW rdsbo, SourceStockBO_OTW sourcebo) {
			// TODO Auto-generated method stub
			String[] ret = {null,null,null};
			for(Map.Entry<String, String> eachMap:source2rdsMap.entrySet()) {				
				String getSouceMethodName = new StringBuffer("get").append(eachMap.getValue().substring(0, 1).toUpperCase()).append(eachMap.getValue().substring(1)).toString();
				String getRdsMethodName = new StringBuffer("get").append(eachMap.getKey().substring(0, 1).toUpperCase()).append(eachMap.getKey().substring(1)).toString();

				Method getSourceMethod;
				try {
					getSourceMethod = sourcebo.getClass().getMethod(getSouceMethodName);
					Class returnType =  getSourceMethod.getReturnType();
					Method getRdsMethod = rdsbo.getClass().getMethod(getRdsMethodName);

					if(!getSourceMethod.invoke(sourcebo).equals(getRdsMethod.invoke(rdsbo))) {
						ret[0] = eachMap.getKey();
						ret[1] = String.valueOf(getSourceMethod.invoke(sourcebo));
						ret[2] = String.valueOf(getRdsMethod.invoke(rdsbo));						
						break;
					}	
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
													
			}
			return ret;		
		}
		
		public void setMethod(RDSStockBO_OTW rdsbo, SourceStockBO_OTW sourcebo, Map.Entry<String, String>eachMap) {
			String getMethodName = new StringBuffer("get").append(eachMap.getValue().substring(0, 1).toUpperCase()).append(eachMap.getValue().substring(1)).toString();
			String setMethodName = new StringBuffer("set").append(eachMap.getKey().substring(0, 1).toUpperCase()).append(eachMap.getKey().substring(1)).toString();

			Method getSourceMethod;
			try {
				getSourceMethod = sourcebo.getClass().getMethod(getMethodName);
				Class returnType =  getSourceMethod.getReturnType();
				Method setRDSMethod = rdsbo.getClass().getMethod(setMethodName, returnType);
				setRDSMethod.invoke(rdsbo, getSourceMethod.invoke(sourcebo));
			} catch (NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}
	

}
