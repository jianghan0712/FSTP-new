package com.purefun.fstp.ace.rds.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.purefun.fstp.ace.rds.loader.bean.Cpxxbean;

public class CpxxLoader extends FileLoader{
	public CpxxLoader(String filepath,Logger log,String fileName,String spiltStr) {
		this.filePath = filepath;
		this.log = log;
		this.fileName = fileName;
		this.splitStr = spiltStr;
	}

	@Override
	public Map<String,Map<String,Cpxxbean>> load() throws IOException {
		// TODO Auto-generated method stub
		Map<String,Cpxxbean> stock_map = new HashMap<String,Cpxxbean>();
		Map<String,Cpxxbean> fund_map = new HashMap<String,Cpxxbean>();
		Map<String,Cpxxbean> bond_map = new HashMap<String,Cpxxbean>();
		Map<String,Cpxxbean> warrant_map = new HashMap<String,Cpxxbean>();
		Map<String,Cpxxbean> future_map = new HashMap<String,Cpxxbean>();
		Map<String,Cpxxbean> other_map = new HashMap<String,Cpxxbean>();
		
		Map<String,Map<String,Cpxxbean>> cpxxMap = new HashMap<String,Map<String,Cpxxbean>>();
		String line = null;
		
		InputStream input = new FileInputStream(filePath);
		InputStreamReader inp = new InputStreamReader(input,"GBK");
		BufferedReader br = new BufferedReader (inp);
		
		int count = 0;
		while((line = br.readLine())!=null) {
			String[] lineValues = line.split(splitStr);
			Cpxxbean bean = new Cpxxbean();
			int i = 0;
			bean.setSecu_id(lineValues[i++].trim() + ".SH");
			bean.setIsin(lineValues[i++].trim());
			bean.setRecord_update_time(lineValues[i++].trim());
			bean.setSecu_chinese_name(lineValues[i++].trim());
			bean.setSecu_english_name(lineValues[i++].trim());
			bean.setSecu_base_id(lineValues[i++].trim() + ".SH");
			bean.setExch_type(lineValues[i++].trim());		
			bean.setSecu_type(lineValues[i++].trim());
			bean.setSecu_sub_type(lineValues[i++].trim());
			bean.setCurrency(lineValues[i++].trim());
			bean.setBond_par_value(lineValues[i++].trim());
			bean.setNot_list_stkqty(lineValues[i++].trim());
			bean.setLast_trade_date(lineValues[i++].trim());
			bean.setList_date(lineValues[i++].trim());
			bean.setProduct_set_id(lineValues[i++].trim());
			bean.setBuy_unit(lineValues[i++].trim());
			bean.setSell_unit(lineValues[i++].trim());
			bean.setTrade_low_limit(lineValues[i++].trim());
			bean.setTrade_high_limit(lineValues[i++].trim());
			bean.setPre_close_price(lineValues[i++].trim());
			bean.setTick_price(lineValues[i++].trim());
			bean.setPrice_limit_type(lineValues[i++].trim());
			bean.setPrice_high_limit(lineValues[i++].trim());
			bean.setPrice_low_limit(lineValues[i++].trim());
			bean.setEx_right_ratio(lineValues[i++].trim());
			bean.setDividend_price(lineValues[i++].trim());
			bean.setFinancing_flag(lineValues[i++].trim());
			bean.setMargin_flag(lineValues[i++].trim());
			bean.setSecu_status(lineValues[i++]);
			bean.setMemo(lineValues[i++].trim());		
			
			String secu_type = bean.getSecu_type();
			if(secu_type.equalsIgnoreCase("ES")) {
				stock_map.put(bean.getSecu_id(), bean);
			}else if(secu_type.equalsIgnoreCase("EU")){
				fund_map.put(bean.getSecu_id(), bean);
			}else if(secu_type.equalsIgnoreCase("D")){
				bond_map.put(bean.getSecu_id(), bean);
			}else if(secu_type.equalsIgnoreCase("RWS")){
				warrant_map.put(bean.getSecu_id(), bean);
			}else if(secu_type.equalsIgnoreCase("FF")){
				future_map.put(bean.getSecu_id(), bean);
			}else
				other_map.put(bean.getSecu_id(), bean);
			
			log.info(bean.toString());
			count++;
		}

		cpxxMap.put("STOCK", stock_map);
		cpxxMap.put("FUND", fund_map);
		cpxxMap.put("BOND", bond_map);
		cpxxMap.put("WARRANT", warrant_map);
		cpxxMap.put("FUTURE", future_map);
		cpxxMap.put("OTHER", other_map);		
		
		log.info("load {} data from {}",count,fileName);
		return cpxxMap;
	}
}
