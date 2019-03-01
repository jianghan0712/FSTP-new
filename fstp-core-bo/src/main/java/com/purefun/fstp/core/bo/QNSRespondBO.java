package com.purefun.fstp.core.bo;

import com.purefun.fstp.core.bo.tool.fstpbo;

@fstpbo(boid = 4L, destination = "fstp.core.manager.qnsrespond")
public class QNSRespondBO extends BaseBO  {	
	
	public String servername = "";	
	
	public String respond = "";
}
