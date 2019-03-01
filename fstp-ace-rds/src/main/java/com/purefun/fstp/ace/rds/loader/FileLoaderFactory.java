package com.purefun.fstp.ace.rds.loader;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.purefun.fstp.ace.rds.loader.bean.Cpxxbean;

public class FileLoaderFactory {
	Map<String,String> fileMap = null;
	String exchFilesPath = null;
	static Logger log = null;
	Map<String,Map> allMap = new HashMap<String, Map>();

	protected void init(Logger log) {
		this.log = log;
	}
	
	protected String getDate() {
		Date date = new Date();
		SimpleDateFormat dateFormat= new SimpleDateFormat("yyyyMMdd");
		return dateFormat.format(date);		
	}
	
	protected void loadFiles() {
		String yyyymmdd = getDate();
		String mmdd = yyyymmdd.substring(4);
		
		if(fileMap==null)
			return;
						
		for(Map.Entry<String, String> each:fileMap.entrySet()) {
			String fileName = null;
			String[] value = each.getValue().split(",");//0:cpxx,1:.txt
			Map eachMap = null;
			
			if(value[0].equalsIgnoreCase("mmdd")) {
				fileName = exchFilesPath + each.getKey() + mmdd + "." + value[1];
			}else if(value[0].equalsIgnoreCase("yyyymmdd")) {
				fileName = exchFilesPath + each.getKey() + yyyymmdd + "." + value[1];
			}
			
			eachMap = chooseLoader(fileName,each.getKey(),value[2]);
			allMap.put(each.getKey(), eachMap);
		}
	}

	public Map chooseLoader(String path,String fileType,String spiltStr) {
		FileLoader loader = null;
		Map result = null;
		
		if(fileType.equalsIgnoreCase("cpxx")) {
			loader = new CpxxLoader(path,log,fileType,spiltStr);
		}else {
			
		}

		try {
			result = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	
	public Map<String, String> getFileMap() {
		return fileMap;
	}

	public void setFileMap(Map<String, String> fileMap) {
		this.fileMap = fileMap;
	}

	public String getExchFilesPath() {
		return exchFilesPath;
	}

	public void setExchFilesPath(String exchFilesPath) {
		this.exchFilesPath = exchFilesPath;
	}

	public Map<String, Map> getAllMap() {
		return allMap;
	}

	public void setAllMap(Map<String, Map> allMap) {
		this.allMap = allMap;
	}
}
