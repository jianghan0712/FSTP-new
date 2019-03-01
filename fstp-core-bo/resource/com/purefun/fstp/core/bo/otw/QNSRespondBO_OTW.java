package com.purefun.fstp.core.bo.otw;

import com.purefun.fstp.core.bo.QNSRespondBO;
import com.purefun.fstp.core.bo.pro.QNSRespondBO_PRO;
import com.google.protobuf.InvalidProtocolBufferException;
import com.purefun.fstp.core.bo.commom.ICommom_OTW;
import com.google.protobuf.Any;

public class QNSRespondBO_OTW implements ICommom_OTW {
    QNSRespondBO_PRO.QNSRespondBO.Builder builder = null;
    QNSRespondBO bo = null;

    public QNSRespondBO_OTW() {
        builder = QNSRespondBO_PRO.QNSRespondBO.newBuilder();
        bo= new QNSRespondBO();
        builder.setUuid(bo.uuid);
        builder.setBoid(bo.boid);
        builder.setDestination(bo.destination);
    }

    public QNSRespondBO_OTW(byte[] message) throws InvalidProtocolBufferException {
        builder = QNSRespondBO_PRO.QNSRespondBO.newBuilder();
        bo= new QNSRespondBO();
        QNSRespondBO_PRO.QNSRespondBO receive = QNSRespondBO_PRO.QNSRespondBO.parseFrom(message);
        setServername(receive.getServername());
        setRespond(receive.getRespond());
        setUuid(receive.getUuid());
        setBoid(receive.getBoid());
        setDestination(receive.getDestination());
    }

    public QNSRespondBO_OTW(QNSRespondBO bofrom){
        builder = QNSRespondBO_PRO.QNSRespondBO.newBuilder();
        bo= new QNSRespondBO();
        setServername(bofrom.servername);
        setRespond(bofrom.respond);
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
    public QNSRespondBO getBo() { 
        return bo;
    }

    public java.lang.String getServername() {
        return builder.getServername();
    }

    public void setServername(java.lang.String servername) {
        bo.servername = servername;
        builder.setServername(servername);
    }

    public java.lang.String getRespond() {
        return builder.getRespond();
    }

    public void setRespond(java.lang.String respond) {
        bo.respond = respond;
        builder.setRespond(respond);
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
        return "QNSRespondBO_OTW ["+
            "uuid = " + getUuid() +"," +
            "boid = " + getBoid() +"," +
            "destination = " + getDestination() +"," +
            "servername = " + getServername() +"," +
            "respond = " + getRespond() +"," +
         "]";
    }
}
