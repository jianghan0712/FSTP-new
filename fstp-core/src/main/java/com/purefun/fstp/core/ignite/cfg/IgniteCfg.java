package com.purefun.fstp.core.ignite.cfg;

import org.apache.ignite.IgniteLogger;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.logger.slf4j.Slf4jLogger;

public class IgniteCfg {
	IgniteConfiguration cfg = null;
	
	public void init(org.slf4j.Logger log) {
		IgniteLogger logger = new Slf4jLogger(log);
		cfg.setGridLogger(logger);
	}

	public IgniteConfiguration getCfg() {
		return cfg;
	}

	public void setCfg(IgniteConfiguration cfg) {
		this.cfg = cfg;
	}
	
	
}
