package com.purefun.fstp.ace.rds.loader;

import java.util.Map;

import com.purefun.fstp.ace.rds.dict.Dictionary;
import com.purefun.fstp.ace.rds.loader.bean.Cpxxbean;
import com.purefun.fstp.core.bo.SourceStockBO;
import com.purefun.fstp.core.bo.otw.SourceStockBO_OTW;
import com.purefun.fstp.core.bo.tool.BoFactory;
import com.purefun.fstp.core.configuration.FstpConfiguration;
import com.purefun.fstp.core.constant.PublishMode;
import com.purefun.fstp.core.logging.PLogger;
import com.purefun.fstp.core.mq.ipc.IpcFactory;
import com.purefun.fstp.core.mq.ipc.pub.Publisher;
import com.purefun.fstp.core.service.PClient;

public class ExchRDSLoader extends PClient{
	Map<String,String> secuMap = null;
	FileLoaderFactory filesFactory = null;
	Dictionary dict = null;
	String exchFilesPath = null;
	
	public ExchRDSLoader(FstpConfiguration config) {
		super(config);
		// TODO Auto-generated constructor stub
		log = PLogger.getLogger(ExchRDSLoader.class);
	}
	
	public void init() {
		super.init();		
	}
	
	public void start() {
		super.start();
		filesFactory.init(log);
		loadFiles();
		publish2RDS();
		
		System.exit(0);
	}

	private void loadFiles() {
		// TODO Auto-generated method stub
		filesFactory.loadFiles();
	}
	
	private void publish2RDS() {
		Map<String,Map> allMap = filesFactory.getAllMap();
		Publisher publisher = IpcFactory.createPublisherJMS();
		int count = 0;
		for(Map.Entry<String, Map> eachMap:allMap.entrySet()) {
			String fileNamp = eachMap.getKey();
			Map<String,Map<String,Cpxxbean>> infoMap = eachMap.getValue();
			Map<String,Cpxxbean> beanMap = infoMap.get("STOCK");
			for(Map.Entry<String, Cpxxbean> bean:beanMap.entrySet()) {
				SourceStockBO_OTW bo = (SourceStockBO_OTW) BoFactory.createBo(SourceStockBO.class);	
				Cpxxbean value = bean.getValue();
				bo.setSecu_id(value.getSecu_id());
				bo.setIsin(value.getIsin());
				bo.setRecord_update_time(value.getRecord_update_time());
				bo.setSecu_chinese_name(value.getSecu_chinese_name());
				bo.setSecu_english_name(value.getSecu_english_name());
				bo.setSecu_base_id(value.getSecu_base_id());
				bo.setExch_type(value.getExch_type());
				bo.setSecu_type(dict.getSecu_type().get(value.getSecu_type()));
				bo.setSecu_sub_type(dict.getSecu_subtype().get(value.getSecu_sub_type()));
				bo.setCurrency(value.getCurrency());
				bo.setBond_par_value(!value.getBond_par_value().equalsIgnoreCase("")?Double.valueOf(value.getBond_par_value()):0.0);
				bo.setNot_list_stkqty(value.getNot_list_stkqty());
				bo.setLast_trade_date(value.getLast_trade_date());
				bo.setList_date(value.getList_date());
				bo.setProduct_set_id(value.getProduct_set_id());
				bo.setBuy_unit(Integer.valueOf(value.getBuy_unit()));
				bo.setSell_unit(Integer.valueOf(value.getSell_unit()));
				bo.setTrade_low_limit(!value.getTrade_low_limit().equalsIgnoreCase("")?Integer.valueOf(value.getTrade_low_limit()):0);
				bo.setTrade_high_limit(!value.getTrade_high_limit().equalsIgnoreCase("")?Integer.valueOf(value.getTrade_high_limit()):0);
				bo.setPre_close_price(!value.getPre_close_price().equalsIgnoreCase("")?Double.valueOf(value.getPre_close_price()):0.0);
				bo.setTick_price(!value.getTick_price().equalsIgnoreCase("")?Double.valueOf(value.getTick_price()):0.0);
				bo.setPrice_limit_type(value.getPrice_limit_type());
				bo.setPrice_high_limit(!value.getPrice_high_limit().equalsIgnoreCase("")?Double.valueOf(value.getPrice_high_limit()):0.0);
				bo.setPrice_low_limit(!value.getPrice_low_limit().equalsIgnoreCase("")?Double.valueOf(value.getPrice_low_limit()):0.0);
				bo.setEx_right_ratio(!value.getEx_right_ratio().equalsIgnoreCase("")?Double.valueOf(value.getEx_right_ratio()):0.0);
				bo.setDividend_price(!value.getDividend_price().equalsIgnoreCase("")?Double.valueOf(value.getDividend_price()):0.0);
				bo.setFinancing_flag(value.getFinancing_flag());
				bo.setMargin_flag(value.getMargin_flag());
				bo.setSecu_status(value.getSecu_status());
				bo.setMemo(value.getMemo());				
				publisher.publish(bo, PublishMode.PUBLISH_ONLY);
				count++;
			}			
		}	
		log.info("publish total : {}",count);
	}

	public Map<String, String> getSecuMap() {
		return secuMap;
	}

	public void setSecuMap(Map<String, String> secuMap) {
		this.secuMap = secuMap;
	}

	public FileLoaderFactory getFilesFactory() {
		return filesFactory;
	}

	public void setFilesFactory(FileLoaderFactory filesFactory) {
		this.filesFactory = filesFactory;
	}

	public Dictionary getDict() {
		return dict;
	}

	public void setDict(Dictionary dict) {
		this.dict = dict;
	}
	
}
