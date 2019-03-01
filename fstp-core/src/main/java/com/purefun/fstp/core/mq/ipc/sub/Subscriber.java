package com.purefun.fstp.core.mq.ipc.sub;

import com.purefun.fstp.core.mq.ipc.listener.ISubListener;

public interface Subscriber {
	void subscribe(String topic, ISubListener msglisteneer);
}
