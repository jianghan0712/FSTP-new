package com.purefun.fstp.example.bo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import com.purefun.fstp.core.bo.tool.fstpbo;

@Entity  
@Table(name="ExampleQns") 
@fstpbo(boid=6015L,destination = "fstp.example.bo.test")
public class ExampleQnsBO extends ExampleBO  {					
	@QuerySqlField
	public String company = "";
}
