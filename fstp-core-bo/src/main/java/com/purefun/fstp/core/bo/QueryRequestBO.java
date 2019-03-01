package com.purefun.fstp.core.bo;

import com.purefun.fstp.core.bo.tool.fstpbo;

@fstpbo(boid = 5L, destination = "fstp.core.manager.queryrequest")
public class QueryRequestBO extends BaseBO  {
	
	public String requestServiceName = "";
	
	public String respondServiceName = "";
	
	public String querytopic = "";
	
	public String tempTopic = "";	
}
