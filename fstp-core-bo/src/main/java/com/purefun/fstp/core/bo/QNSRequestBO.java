package com.purefun.fstp.core.bo;

import com.purefun.fstp.core.bo.tool.fstpbo;

@fstpbo(boid = 4L, destination = "fstp.core.manager.qnsrequest")
public class QNSRequestBO extends BaseBO  {	
	
	public String servername = "";	
	
	public String request = "";
}
