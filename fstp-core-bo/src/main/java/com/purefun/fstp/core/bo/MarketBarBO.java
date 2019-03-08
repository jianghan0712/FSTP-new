package com.purefun.fstp.core.bo;

import com.purefun.fstp.core.bo.tool.fstpbo;

@fstpbo(boid = 10L, destination = "fstp.md.bar")
public class MarketBarBO extends BaseBO{
	public String product_id = "";
	public String exch = "";
	public String date = "";
	public double high = 0.0;
	public double low = 0.0;
	public double open = 0.0;	
	public double close = 0.0;
	public double change = 0.0;
	public double volume = 0.0;
}
