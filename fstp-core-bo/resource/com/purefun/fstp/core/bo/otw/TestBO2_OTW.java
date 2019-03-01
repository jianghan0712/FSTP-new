package com.purefun.fstp.core.bo.otw;

import com.purefun.fstp.core.bo.TestBO2;
import com.purefun.fstp.core.bo.pro.TestBO2_PRO;
import com.google.protobuf.InvalidProtocolBufferException;
import com.purefun.fstp.core.bo.commom.ICommom_OTW;
import com.google.protobuf.Any;

public class TestBO2_OTW implements ICommom_OTW {
    TestBO2_PRO.TestBO2.Builder builder = null;
    TestBO2 bo = null;

    public TestBO2_OTW() {
        builder = TestBO2_PRO.TestBO2.newBuilder();
        bo= new TestBO2();
        builder.setUuid(bo.uuid);
        builder.setBoid(bo.boid);
        builder.setDestination(bo.destination);
    }

    public TestBO2_OTW(byte[] message) throws InvalidProtocolBufferException {
        builder = TestBO2_PRO.TestBO2.newBuilder();
        bo= new TestBO2();
        TestBO2_PRO.TestBO2 receive = TestBO2_PRO.TestBO2.parseFrom(message);
        setServername(receive.getServername());
        setMsg(receive.getMsg());
        setUuid(receive.getUuid());
        setBoid(receive.getBoid());
        setDestination(receive.getDestination());
    }

    public TestBO2_OTW(TestBO2 bofrom){
        builder = TestBO2_PRO.TestBO2.newBuilder();
        bo= new TestBO2();
        setServername(bofrom.servername);
        setMsg(bofrom.msg);
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
    public TestBO2 getBo() { 
        return bo;
    }

    public java.lang.String getServername() {
        return builder.getServername();
    }

    public void setServername(java.lang.String servername) {
        bo.servername = servername;
        builder.setServername(servername);
    }

    public java.lang.String getMsg() {
        return builder.getMsg();
    }

    public void setMsg(java.lang.String msg) {
        bo.msg = msg;
        builder.setMsg(msg);
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
        return "TestBO2_OTW ["+
            "uuid = " + getUuid() +"," +
            "boid = " + getBoid() +"," +
            "destination = " + getDestination() +"," +
            "servername = " + getServername() +"," +
            "msg = " + getMsg() +"," +
         "]";
    }
}
