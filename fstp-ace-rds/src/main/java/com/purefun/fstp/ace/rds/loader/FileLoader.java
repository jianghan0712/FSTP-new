package com.purefun.fstp.ace.rds.loader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

public abstract class FileLoader {
	String filePath = null;
	String fileName = null;
	String splitStr = null;
	Logger log = null;
	
	public abstract <K,V> Map<K,V> load() throws IOException;
}
