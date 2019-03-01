package com.purefun.fstp.core.bo;

import com.purefun.fstp.core.bo.tool.fstpbo;

@fstpbo(boid = 1L, destination = "fstp.core.manager.serverstatus")
public class ServerStatsBO extends BaseBO  {		
	
	public String servername = "";
	
	public int status = -1;		
}
