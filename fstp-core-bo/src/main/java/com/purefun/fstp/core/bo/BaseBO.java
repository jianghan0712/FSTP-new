package com.purefun.fstp.core.bo;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

@MappedSuperclass
public class BaseBO implements Serializable{
	@QuerySqlField(index=true)
	@Id
	public String uuid;					
	@QuerySqlField
	public long boid ;					
	@QuerySqlField	
	public String destination;
}
