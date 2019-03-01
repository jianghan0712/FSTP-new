package com.purefun.fstp.core.bo.model;

import java.util.List;

/**
 * 用于MonitorService管理当前连接到FSTP系统上BO的情况。用户不需要关心
 * 
 * @author Jianghan
 *
 */
public class BOinstance {
	public int boid = -1;
	public String name = null;
	public String destination = null;
	public String boEntry = null;
	public List<String> serverList = null;
	
	public int getBoid() {
		return boid;
	}
	public void setBoid(int boid) {
		this.boid = boid;
	}	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getBoEntry() {
		return boEntry;
	}
	public void setBoEntry(String boEntry) {
		this.boEntry = boEntry;
	}
	public List<String> getServerList() {
		return serverList;
	}
	public void setServerList(List<String> serverList) {
		this.serverList = serverList;
	}
	
	
}
