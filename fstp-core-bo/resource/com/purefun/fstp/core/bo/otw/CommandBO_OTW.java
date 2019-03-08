package com.purefun.fstp.core.bo.otw;

import com.purefun.fstp.core.bo.CommandBO;
import com.purefun.fstp.core.bo.pro.CommandBO_PRO;
import com.google.protobuf.InvalidProtocolBufferException;
import com.purefun.fstp.core.bo.commom.ICommom_OTW;
import com.google.protobuf.Any;

public class CommandBO_OTW implements ICommom_OTW {
    CommandBO_PRO.CommandBO.Builder builder = null;
    CommandBO bo = null;

    public CommandBO_OTW() {
        builder = CommandBO_PRO.CommandBO.newBuilder();
        bo= new CommandBO();
        builder.setUuid(bo.uuid);
        builder.setBoid(bo.boid);
        builder.setDestination(bo.destination);
    }

    public CommandBO_OTW(byte[] message) throws InvalidProtocolBufferException {
        builder = CommandBO_PRO.CommandBO.newBuilder();
        bo= new CommandBO();
        CommandBO_PRO.CommandBO receive = CommandBO_PRO.CommandBO.parseFrom(message);
        setTargetService(receive.getTargetService());
        setParm0(receive.getParm0());
        setParm1(receive.getParm1());
        setParm2(receive.getParm2());
        setParm3(receive.getParm3());
        setParm4(receive.getParm4());
        setUuid(receive.getUuid());
        setBoid(receive.getBoid());
        setDestination(receive.getDestination());
    }

    public CommandBO_OTW(CommandBO bofrom){
        builder = CommandBO_PRO.CommandBO.newBuilder();
        bo= new CommandBO();
        setTargetService(bofrom.targetService);
        setParm0(bofrom.parm0);
        setParm1(bofrom.parm1);
        setParm2(bofrom.parm2);
        setParm3(bofrom.parm3);
        setParm4(bofrom.parm4);
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
    public CommandBO getBo() { 
        return bo;
    }

    public java.lang.String getTargetService() {
        return builder.getTargetService();
    }

    public void setTargetService(java.lang.String targetService) {
        bo.targetService = targetService;
        builder.setTargetService(targetService);
    }

    public java.lang.String getParm0() {
        return builder.getParm0();
    }

    public void setParm0(java.lang.String parm0) {
        bo.parm0 = parm0;
        builder.setParm0(parm0);
    }

    public java.lang.String getParm1() {
        return builder.getParm1();
    }

    public void setParm1(java.lang.String parm1) {
        bo.parm1 = parm1;
        builder.setParm1(parm1);
    }

    public java.lang.String getParm2() {
        return builder.getParm2();
    }

    public void setParm2(java.lang.String parm2) {
        bo.parm2 = parm2;
        builder.setParm2(parm2);
    }

    public java.lang.String getParm3() {
        return builder.getParm3();
    }

    public void setParm3(java.lang.String parm3) {
        bo.parm3 = parm3;
        builder.setParm3(parm3);
    }

    public java.lang.String getParm4() {
        return builder.getParm4();
    }

    public void setParm4(java.lang.String parm4) {
        bo.parm4 = parm4;
        builder.setParm4(parm4);
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
        return "CommandBO_OTW ["+
            "uuid = " + getUuid() +"," +
            "boid = " + getBoid() +"," +
            "destination = " + getDestination() +"," +
            "targetService = " + getTargetService() +"," +
            "parm0 = " + getParm0() +"," +
            "parm1 = " + getParm1() +"," +
            "parm2 = " + getParm2() +"," +
            "parm3 = " + getParm3() +"," +
            "parm4 = " + getParm4() +"," +
         "]";
    }
}
