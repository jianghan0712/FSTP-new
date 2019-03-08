package com.purefun.fstp.ace.md.mongodb;

import com.mongodb.MongoClient;

public class MongoDBUtil {
	public static MongoClient mongoClient = null;
	
	public static MongoClient getClient() {
		if(mongoClient==null) {
			mongoClient =  new MongoClient("localhost", 27017);
		}		
		return mongoClient;
	}
	
}
