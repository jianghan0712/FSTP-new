package com.purefun.fstp.core.service;

public class PProperty {
	public static PProperty property;
	public String serverName;
	public String env;
	public String instance;
	public String fullName;
	public String develop;
	
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getEnv() {
		return env;
	}
	public void setEnv(String env) {
		this.env = env;
	}
	public String getInstance() {
		return instance;
	}
	public void setInstance(String instance) {
		this.instance = instance;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getDevelop() {
		return develop;
	}
	public void setDevelop(String develop) {
		this.develop = develop;
	}
	@Override
	public String toString() {
		return "PProperty [serverName=" + serverName + ", env=" + env + ", instance=" + instance + ", fullName="
				+ fullName + ", develop=" + develop + "]";
	}
	
	public static void getProperty() {
		// TODO Auto-generated method stub
		property = new PProperty();
		property.serverName = System.getProperty("ServiceName");
		property.env = System.getProperty("Env");
		property.instance =  System.getProperty("Instance");
		property.fullName = property.serverName + "_" + property.env + "_" + property.instance;
		property.develop = System.getProperty("Develop");
	}
	
}
