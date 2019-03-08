package com.purefun.fstp.core.bo;

import com.purefun.fstp.core.bo.tool.fstpbo;

@fstpbo(boid = 999L, destination = "fstp.core.manager.command")
public class CommandBO extends BaseBO{
	public String targetService = "";
	public String parm0 = "";
	public String parm1 = "";
	public String parm2 = "";
	public String parm3 = "";
	public String parm4 = "";	
}
