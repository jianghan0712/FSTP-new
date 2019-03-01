package com.purefun.fstp.ace.rds.dict;

import java.util.Map;

public class Dictionary {
	Map<String,String> secu_type = null;
	Map<String,String> secu_subtype = null;
	public Map<String, String> getSecu_type() {
		return secu_type;
	}
	public void setSecu_type(Map<String, String> secu_type) {
		this.secu_type = secu_type;
	}
	public Map<String, String> getSecu_subtype() {
		return secu_subtype;
	}
	public void setSecu_subtype(Map<String, String> secu_subtype) {
		this.secu_subtype = secu_subtype;
	}
	
}
