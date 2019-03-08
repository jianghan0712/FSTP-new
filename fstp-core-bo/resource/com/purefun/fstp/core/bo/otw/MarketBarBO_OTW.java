package com.purefun.fstp.core.bo.otw;

import com.purefun.fstp.core.bo.MarketBarBO;
import com.purefun.fstp.core.bo.pro.MarketBarBO_PRO;
import com.google.protobuf.InvalidProtocolBufferException;
import com.purefun.fstp.core.bo.commom.ICommom_OTW;
import com.google.protobuf.Any;

public class MarketBarBO_OTW implements ICommom_OTW {
    MarketBarBO_PRO.MarketBarBO.Builder builder = null;
    MarketBarBO bo = null;

    public MarketBarBO_OTW() {
        builder = MarketBarBO_PRO.MarketBarBO.newBuilder();
        bo= new MarketBarBO();
        builder.setUuid(bo.uuid);
        builder.setBoid(bo.boid);
        builder.setDestination(bo.destination);
    }

    public MarketBarBO_OTW(byte[] message) throws InvalidProtocolBufferException {
        builder = MarketBarBO_PRO.MarketBarBO.newBuilder();
        bo= new MarketBarBO();
        MarketBarBO_PRO.MarketBarBO receive = MarketBarBO_PRO.MarketBarBO.parseFrom(message);
        setProduct_id(receive.getProductId());
        setExch(receive.getExch());
        setDate(receive.getDate());
        setHigh(receive.getHigh());
        setLow(receive.getLow());
        setOpen(receive.getOpen());
        setClose(receive.getClose());
        setChange(receive.getChange());
        setVolume(receive.getVolume());
        setUuid(receive.getUuid());
        setBoid(receive.getBoid());
        setDestination(receive.getDestination());
    }

    public MarketBarBO_OTW(MarketBarBO bofrom){
        builder = MarketBarBO_PRO.MarketBarBO.newBuilder();
        bo= new MarketBarBO();
        setProduct_id(bofrom.product_id);
        setExch(bofrom.exch);
        setDate(bofrom.date);
        setHigh(bofrom.high);
        setLow(bofrom.low);
        setOpen(bofrom.open);
        setClose(bofrom.close);
        setChange(bofrom.change);
        setVolume(bofrom.volume);
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
    public MarketBarBO getBo() { 
        return bo;
    }

    public java.lang.String getProduct_id() {
        return builder.getProductId();
    }

    public void setProduct_id(java.lang.String product_id) {
        bo.product_id = product_id;
        builder.setProductId(product_id);
    }

    public java.lang.String getExch() {
        return builder.getExch();
    }

    public void setExch(java.lang.String exch) {
        bo.exch = exch;
        builder.setExch(exch);
    }

    public java.lang.String getDate() {
        return builder.getDate();
    }

    public void setDate(java.lang.String date) {
        bo.date = date;
        builder.setDate(date);
    }

    public double getHigh() {
        return builder.getHigh();
    }

    public void setHigh(double high) {
        bo.high = high;
        builder.setHigh(high);
    }

    public double getLow() {
        return builder.getLow();
    }

    public void setLow(double low) {
        bo.low = low;
        builder.setLow(low);
    }

    public double getOpen() {
        return builder.getOpen();
    }

    public void setOpen(double open) {
        bo.open = open;
        builder.setOpen(open);
    }

    public double getClose() {
        return builder.getClose();
    }

    public void setClose(double close) {
        bo.close = close;
        builder.setClose(close);
    }

    public double getChange() {
        return builder.getChange();
    }

    public void setChange(double change) {
        bo.change = change;
        builder.setChange(change);
    }

    public double getVolume() {
        return builder.getVolume();
    }

    public void setVolume(double volume) {
        bo.volume = volume;
        builder.setVolume(volume);
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
        return "MarketBarBO_OTW ["+
            "uuid = " + getUuid() +"," +
            "boid = " + getBoid() +"," +
            "destination = " + getDestination() +"," +
            "product_id = " + getProduct_id() +"," +
            "exch = " + getExch() +"," +
            "date = " + getDate() +"," +
            "high = " + getHigh() +"," +
            "low = " + getLow() +"," +
            "open = " + getOpen() +"," +
            "close = " + getClose() +"," +
            "change = " + getChange() +"," +
            "volume = " + getVolume() +"," +
         "]";
    }
}
