package com.purefun.fstp.example.bo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import com.purefun.fstp.core.bo.BaseBO;
import com.purefun.fstp.core.bo.tool.fstpbo;

@MappedSuperclass
@fstpbo(boid = 6013L, destination = "fstp.example.bo.test2")
public class ExampleBO extends BaseBO  {	
	
	@QuerySqlField
	public String name = "";
	@QuerySqlField
	public int age = 0;
	
}
