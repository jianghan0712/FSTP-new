package com.purefun.fstp.core.bo;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.purefun.fstp.core.bo.tool.fstpbo;

@Entity
@Table(name="TestBO")
@fstpbo(boid = 3L, destination = "fstp.core.rpc.testtwo")
public class TestBO2 extends BaseBO {
	public String servername = "";
	
	public String msg = "";
}
