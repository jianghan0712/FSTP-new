package com.purefun.fstp.core.bo.otw;

import com.purefun.fstp.core.bo.QNSRequestBO;
import com.purefun.fstp.core.bo.pro.QNSRequestBO_PRO;
import com.google.protobuf.InvalidProtocolBufferException;
import com.purefun.fstp.core.bo.commom.ICommom_OTW;
import com.google.protobuf.Any;

public class QNSRequestBO_OTW implements ICommom_OTW {
    QNSRequestBO_PRO.QNSRequestBO.Builder builder = null;
    QNSRequestBO bo = null;

    public QNSRequestBO_OTW() {
        builder = QNSRequestBO_PRO.QNSRequestBO.newBuilder();
        bo= new QNSRequestBO();
        builder.setUuid(bo.uuid);
        builder.setBoid(bo.boid);
        builder.setDestination(bo.destination);
    }

    public QNSRequestBO_OTW(byte[] message) throws InvalidProtocolBufferException {
        builder = QNSRequestBO_PRO.QNSRequestBO.newBuilder();
        bo= new QNSRequestBO();
        QNSRequestBO_PRO.QNSRequestBO receive = QNSRequestBO_PRO.QNSRequestBO.parseFrom(message);
        setServername(receive.getServername());
        setRequest(receive.getRequest());
        setUuid(receive.getUuid());
        setBoid(receive.getBoid());
        setDestination(receive.getDestination());
    }

    public QNSRequestBO_OTW(QNSRequestBO bofrom){
        builder = QNSRequestBO_PRO.QNSRequestBO.newBuilder();
        bo= new QNSRequestBO();
        setServername(bofrom.servername);
        setRequest(bofrom.request);
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
    public QNSRequestBO getBo() { 
        return bo;
    }

    public java.lang.String getServername() {
        return builder.getServername();
    }

    public void setServername(java.lang.String servername) {
        bo.servername = servername;
        builder.setServername(servername);
    }

    public java.lang.String getRequest() {
        return builder.getRequest();
    }

    public void setRequest(java.lang.String request) {
        bo.request = request;
        builder.setRequest(request);
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
        return "QNSRequestBO_OTW ["+
            "uuid = " + getUuid() +"," +
            "boid = " + getBoid() +"," +
            "destination = " + getDestination() +"," +
            "servername = " + getServername() +"," +
            "request = " + getRequest() +"," +
         "]";
    }
}
