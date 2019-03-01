package com.purefun.fstp.core.bo.otw;

import com.purefun.fstp.core.bo.ServerStatsBO;
import com.purefun.fstp.core.bo.pro.ServerStatsBO_PRO;
import com.google.protobuf.InvalidProtocolBufferException;
import com.purefun.fstp.core.bo.commom.ICommom_OTW;
import com.google.protobuf.Any;

public class ServerStatsBO_OTW implements ICommom_OTW {
    ServerStatsBO_PRO.ServerStatsBO.Builder builder = null;
    ServerStatsBO bo = null;

    public ServerStatsBO_OTW() {
        builder = ServerStatsBO_PRO.ServerStatsBO.newBuilder();
        bo= new ServerStatsBO();
        builder.setUuid(bo.uuid);
        builder.setBoid(bo.boid);
        builder.setDestination(bo.destination);
    }

    public ServerStatsBO_OTW(byte[] message) throws InvalidProtocolBufferException {
        builder = ServerStatsBO_PRO.ServerStatsBO.newBuilder();
        bo= new ServerStatsBO();
        ServerStatsBO_PRO.ServerStatsBO receive = ServerStatsBO_PRO.ServerStatsBO.parseFrom(message);
        setServername(receive.getServername());
        setStatus(receive.getStatus());
        setUuid(receive.getUuid());
        setBoid(receive.getBoid());
        setDestination(receive.getDestination());
    }

    public ServerStatsBO_OTW(ServerStatsBO bofrom){
        builder = ServerStatsBO_PRO.ServerStatsBO.newBuilder();
        bo= new ServerStatsBO();
        setServername(bofrom.servername);
        setStatus(bofrom.status);
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
    public ServerStatsBO getBo() { 
        return bo;
    }

    public java.lang.String getServername() {
        return builder.getServername();
    }

    public void setServername(java.lang.String servername) {
        bo.servername = servername;
        builder.setServername(servername);
    }

    public int getStatus() {
        return builder.getStatus();
    }

    public void setStatus(int status) {
        bo.status = status;
        builder.setStatus(status);
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
        return "ServerStatsBO_OTW ["+
            "uuid = " + getUuid() +"," +
            "boid = " + getBoid() +"," +
            "destination = " + getDestination() +"," +
            "servername = " + getServername() +"," +
            "status = " + getStatus() +"," +
         "]";
    }
}
