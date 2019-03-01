package com.purefun.fstp.core.bo;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import com.purefun.fstp.core.bo.tool.fstpbo;

@Entity  
@Table(name="TestBO") 
@fstpbo(boid = 2L, destination = "fstp.core.rpc.testone")
public class TestBO extends BaseBO {
	@QuerySqlField
	public String servername = "";
	@QuerySqlField
	public String msg = "";	
}
