package com.purefun.fstp.example.bo.otw;

import com.purefun.fstp.example.bo.ExampleQnsBO;
import com.purefun.fstp.example.bo.pro.ExampleQnsBO_PRO;
import com.google.protobuf.InvalidProtocolBufferException;
import com.purefun.fstp.core.bo.commom.ICommom_OTW;
import com.google.protobuf.Any;

public class ExampleQnsBO_OTW implements ICommom_OTW {
    ExampleQnsBO_PRO.ExampleQnsBO.Builder builder = null;
    ExampleQnsBO bo = null;

    public ExampleQnsBO_OTW() {
        builder = ExampleQnsBO_PRO.ExampleQnsBO.newBuilder();
        bo= new ExampleQnsBO();
        builder.setUuid(bo.uuid);
        builder.setBoid(bo.boid);
        builder.setDestination(bo.destination);
    }

    public ExampleQnsBO_OTW(byte[] message) throws InvalidProtocolBufferException {
        builder = ExampleQnsBO_PRO.ExampleQnsBO.newBuilder();
        bo= new ExampleQnsBO();
        ExampleQnsBO_PRO.ExampleQnsBO receive = ExampleQnsBO_PRO.ExampleQnsBO.parseFrom(message);
        setCompany(receive.getCompany());
        setName(receive.getName());
        setAge(receive.getAge());
        setUuid(receive.getUuid());
        setBoid(receive.getBoid());
        setDestination(receive.getDestination());
    }

    public ExampleQnsBO_OTW(ExampleQnsBO bofrom){
        builder = ExampleQnsBO_PRO.ExampleQnsBO.newBuilder();
        bo= new ExampleQnsBO();
        setCompany(bofrom.company);
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
    public ExampleQnsBO getBo() { 
        return bo;
    }

    public java.lang.String getCompany() {
        return builder.getCompany();
    }

    public void setCompany(java.lang.String company) {
        bo.company = company;
        builder.setCompany(company);
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
        return "ExampleQnsBO_OTW ["+
            "uuid = " + getUuid() +"," +
            "boid = " + getBoid() +"," +
            "destination = " + getDestination() +"," +
            "company = " + getCompany() +"," +
            "name = " + getName() +"," +
            "age = " + getAge() +"," +
         "]";
    }
}
