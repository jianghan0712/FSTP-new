package com.purefun.fstp.web.ace.rds.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.purefun.fstp.core.bo.RDSStockBO;
import com.purefun.fstp.web.PWebService;
import com.purefun.fstp.web.ace.rds.service.StockService;

@CrossOrigin
@RestController
@RequestMapping("/ace/rds")
public class StockRDSController {
	
	@Autowired
	PWebService service;
	
	@Autowired
	StockService stockRepo;

	@RequestMapping(value = "/stock", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String,Map<String, RDSStockBO>> getUser(@RequestBody String request) {
		Map maps = (Map)JSON.parse(request);
		String product_id = (String)maps.get("product_id");
		String exch_type = (String)maps.get("exch_type");
		
		Map<String, RDSStockBO> repoMap = null;
		Map<String,Map<String, RDSStockBO>> ret = new HashMap<String,Map<String, RDSStockBO>>();
		if (product_id.equalsIgnoreCase("") && exch_type.equalsIgnoreCase("")) {
			repoMap = stockRepo.getRepo();
		}else if(product_id.equalsIgnoreCase("") && !exch_type.equalsIgnoreCase("")) {
			repoMap = new HashMap<String, RDSStockBO>();
			for(RDSStockBO e: stockRepo.getRepo().values()) {
				if(e.exch_type.equalsIgnoreCase(exch_type)) {
					repoMap.put(e.product_id, e);
				}
			}
		}else if(!product_id.equalsIgnoreCase("") && exch_type.equalsIgnoreCase("")) {
			repoMap = new HashMap<String, RDSStockBO>();
			repoMap.put(product_id, stockRepo.getRepo().get(product_id));
		}else {
			repoMap = new HashMap<String, RDSStockBO>();
			RDSStockBO bo = stockRepo.getRepo().get(product_id);
			if(bo!=null && bo.exch_type.equalsIgnoreCase(exch_type)) {
				repoMap.put(product_id, bo);
			}
		}
		
		ret.put("stockinfo", repoMap);				
		
		return ret;
	}
	
	
}
