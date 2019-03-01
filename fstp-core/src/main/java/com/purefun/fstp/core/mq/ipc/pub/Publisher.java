package com.purefun.fstp.core.mq.ipc.pub;

import com.purefun.fstp.core.bo.commom.ICommom_OTW;

public interface Publisher {
	public void publish(ICommom_OTW bo,int mode);
	
	public void durableInCache(ICommom_OTW bo);
}
