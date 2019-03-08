package com.purefun.fstp.ace.md.container;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.purefun.fstp.ace.md.mongodb.MongoDBUtil;
import com.purefun.fstp.core.bo.MarketBarBO;
import com.purefun.fstp.core.bo.otw.CommandBO_OTW;
import com.purefun.fstp.core.bo.otw.MarketBarBO_OTW;
import com.purefun.fstp.core.bo.tool.BoFactory;
import com.purefun.fstp.core.constant.PublishMode;
import com.purefun.fstp.core.mq.ipc.pub.Publisher;

public class MDContainer {
	MongoClient mongoClient;
	String mode;
	Publisher pub;
	
	public MDContainer(String mode, Publisher pub) {
		this.mode = mode;
		this.mongoClient = MongoDBUtil.getClient();
		this.pub = pub;
	}
	
	public void dealCommand(CommandBO_OTW bo) {
		MongoDatabase mongoDatabase = mongoClient.getDatabase(bo.getParm0());
		MongoCollection<Document> collection = mongoDatabase.getCollection(bo.getParm1());
		MongoCursor cursor = collection.find().iterator();
		while(cursor.hasNext()) {
			Document e = (Document) cursor.next();
			MarketBarBO_OTW mdbo= (MarketBarBO_OTW)BoFactory.createBo(MarketBarBO.class);
			mdbo.setProduct_id(bo.getParm1());			
			mdbo.setDate(e.getString("date"));
			mdbo.setChange(e.getDouble("change"));
			mdbo.setClose(e.getDouble("close"));
			mdbo.setOpen(e.getDouble("open"));
			mdbo.setHigh(e.getDouble("high"));
			mdbo.setLow(e.getDouble("low"));
			mdbo.setOpen(e.getDouble("open"));
			mdbo.setVolume(e.getDouble("volume"));
			pub.publish(mdbo, PublishMode.PUBLISH_ONLY);
		}
	}
}
