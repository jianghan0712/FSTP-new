package com.purefun.fstp.core.bo.otw;

import com.purefun.fstp.core.bo.QueryRequestBO;
import com.purefun.fstp.core.bo.pro.QueryRequestBO_PRO;
import com.google.protobuf.InvalidProtocolBufferException;
import com.purefun.fstp.core.bo.commom.ICommom_OTW;
import com.google.protobuf.Any;

public class QueryRequestBO_OTW implements ICommom_OTW {
    QueryRequestBO_PRO.QueryRequestBO.Builder builder = null;
    QueryRequestBO bo = null;

    public QueryRequestBO_OTW() {
        builder = QueryRequestBO_PRO.QueryRequestBO.newBuilder();
        bo= new QueryRequestBO();
        builder.setUuid(bo.uuid);
        builder.setBoid(bo.boid);
        builder.setDestination(bo.destination);
    }

    public QueryRequestBO_OTW(byte[] message) throws InvalidProtocolBufferException {
        builder = QueryRequestBO_PRO.QueryRequestBO.newBuilder();
        bo= new QueryRequestBO();
        QueryRequestBO_PRO.QueryRequestBO receive = QueryRequestBO_PRO.QueryRequestBO.parseFrom(message);
        setRequestServiceName(receive.getRequestServiceName());
        setRespondServiceName(receive.getRespondServiceName());
        setQuerytopic(receive.getQuerytopic());
        setTempTopic(receive.getTempTopic());
        setUuid(receive.getUuid());
        setBoid(receive.getBoid());
        setDestination(receive.getDestination());
    }

    public QueryRequestBO_OTW(QueryRequestBO bofrom){
        builder = QueryRequestBO_PRO.QueryRequestBO.newBuilder();
        bo= new QueryRequestBO();
        setRequestServiceName(bofrom.requestServiceName);
        setRespondServiceName(bofrom.respondServiceName);
        setQuerytopic(bofrom.querytopic);
        setTempTopic(bofrom.tempTopic);
        setUuid(bofrom.uuid);
        setBoid(bofrom.boid);
        setDestination(bofrom.destination);
    }

    public byte[] serial() {
        return builder.build().toByteArray();
    }

    @Override
    public com.google.protobuf.GeneratedMessageV3.Builder getBuilder() { 
        return builder;
    }

    @Override
    public QueryRequestBO getBo() { 
        return bo;
    }

    public java.lang.String getRequestServiceName() {
        return builder.getRequestServiceName();
    }

    public void setRequestServiceName(java.lang.String requestServiceName) {
        bo.requestServiceName = requestServiceName;
        builder.setRequestServiceName(requestServiceName);
    }

    public java.lang.String getRespondServiceName() {
        return builder.getRespondServiceName();
    }

    public void setRespondServiceName(java.lang.String respondServiceName) {
        bo.respondServiceName = respondServiceName;
        builder.setRespondServiceName(respondServiceName);
    }

    public java.lang.String getQuerytopic() {
        return builder.getQuerytopic();
    }

    public void setQuerytopic(java.lang.String querytopic) {
        bo.querytopic = querytopic;
        builder.setQuerytopic(querytopic);
    }

    public java.lang.String getTempTopic() {
        return builder.getTempTopic();
    }

    public void setTempTopic(java.lang.String tempTopic) {
        bo.tempTopic = tempTopic;
        builder.setTempTopic(tempTopic);
    }

    public java.lang.String getUuid() {
        return builder.getUuid();
    }

    public void setUuid(java.lang.String uuid) {
        bo.uuid = uuid;
        builder.setUuid(uuid);
    }

    public long getBoid() {
        return builder.getBoid();
    }

    public void setBoid(long boid) {
        bo.boid = boid;
        builder.setBoid(boid);
    }

    public java.lang.String getDestination() {
        return builder.getDestination();
    }

    public void setDestination(java.lang.String destination) {
        bo.destination = destination;
        builder.setDestination(destination);
    }

    public String toString() {
        return "QueryRequestBO_OTW ["+
            "uuid = " + getUuid() +"," +
            "boid = " + getBoid() +"," +
            "destination = " + getDestination() +"," +
            "requestServiceName = " + getRequestServiceName() +"," +
            "respondServiceName = " + getRespondServiceName() +"," +
            "querytopic = " + getQuerytopic() +"," +
            "tempTopic = " + getTempTopic() +"," +
         "]";
    }
}
