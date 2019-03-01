package com.purefun.fstp.core.bo.commom;

import com.purefun.fstp.core.bo.BaseBO;

public interface ICommom_OTW {
	public String getDestination();
	
	public Object getBo();
	
	public com.google.protobuf.GeneratedMessageV3.Builder getBuilder();
	
	public String getUuid();
}
