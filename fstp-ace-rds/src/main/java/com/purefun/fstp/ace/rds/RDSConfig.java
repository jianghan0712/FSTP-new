package com.purefun.fstp.ace.rds;

import java.util.Map;

public class RDSConfig {
	String tableName = null;
	String rdsCrud = null;
	Map<String,String> source2rdsMap = null;
	String sourceQns = null;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getRdsCrud() {
		return rdsCrud;
	}

	public void setRdsCrud(String rdsCrud) {
		this.rdsCrud = rdsCrud;
	}

	public Map<String, String> getSource2rdsMap() {
		return source2rdsMap;
	}

	public void setSource2rdsMap(Map<String, String> source2rdsMap) {
		this.source2rdsMap = source2rdsMap;
	}

	public String getSourceQns() {
		return sourceQns;
	}

	public void setSourceQns(String sourceQns) {
		this.sourceQns = sourceQns;
	}
	
}
