package com.purefun.fstp.example.bo.otw;

import com.purefun.fstp.example.bo.ExampleBO;
import com.purefun.fstp.example.bo.pro.ExampleBO_PRO;
import com.google.protobuf.InvalidProtocolBufferException;
import com.purefun.fstp.core.bo.commom.ICommom_OTW;
import com.google.protobuf.Any;

public class ExampleBO_OTW implements ICommom_OTW {
    ExampleBO_PRO.ExampleBO.Builder builder = null;
    ExampleBO bo = null;

    public ExampleBO_OTW() {
        builder = ExampleBO_PRO.ExampleBO.newBuilder();
        bo= new ExampleBO();
        builder.setUuid(bo.uuid);
        builder.setBoid(bo.boid);
        builder.setDestination(bo.destination);
    }

    public ExampleBO_OTW(byte[] message) throws InvalidProtocolBufferException {
        builder = ExampleBO_PRO.ExampleBO.newBuilder();
        bo= new ExampleBO();
        ExampleBO_PRO.ExampleBO receive = ExampleBO_PRO.ExampleBO.parseFrom(message);
        setName(receive.getName());
        setAge(receive.getAge());
        setUuid(receive.getUuid());
        setBoid(receive.getBoid());
        setDestination(receive.getDestination());
    }

    public ExampleBO_OTW(ExampleBO bofrom){
        builder = ExampleBO_PRO.ExampleBO.newBuilder();
        bo= new ExampleBO();
        setName(bofrom.name);
        setAge(bofrom.age);
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
    public ExampleBO getBo() { 
        return bo;
    }

    public java.lang.String getName() {
        return builder.getName();
    }

    public void setName(java.lang.String name) {
        bo.name = name;
        builder.setName(name);
    }

    public int getAge() {
        return builder.getAge();
    }

    public void setAge(int age) {
        bo.age = age;
        builder.setAge(age);
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
        return "ExampleBO_OTW ["+
            "uuid = " + getUuid() +"," +
            "boid = " + getBoid() +"," +
            "destination = " + getDestination() +"," +
            "name = " + getName() +"," +
            "age = " + getAge() +"," +
         "]";
    }
}
