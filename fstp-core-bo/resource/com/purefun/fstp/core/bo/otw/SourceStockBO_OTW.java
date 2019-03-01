package com.purefun.fstp.core.bo.otw;

import com.purefun.fstp.core.bo.SourceStockBO;
import com.purefun.fstp.core.bo.pro.SourceStockBO_PRO;
import com.google.protobuf.InvalidProtocolBufferException;
import com.purefun.fstp.core.bo.commom.ICommom_OTW;
import com.google.protobuf.Any;

public class SourceStockBO_OTW implements ICommom_OTW {
    SourceStockBO_PRO.SourceStockBO.Builder builder = null;
    SourceStockBO bo = null;

    public SourceStockBO_OTW() {
        builder = SourceStockBO_PRO.SourceStockBO.newBuilder();
        bo= new SourceStockBO();
        builder.setUuid(bo.uuid);
        builder.setBoid(bo.boid);
        builder.setDestination(bo.destination);
    }

    public SourceStockBO_OTW(byte[] message) throws InvalidProtocolBufferException {
        builder = SourceStockBO_PRO.SourceStockBO.newBuilder();
        bo= new SourceStockBO();
        SourceStockBO_PRO.SourceStockBO receive = SourceStockBO_PRO.SourceStockBO.parseFrom(message);
        setSecu_id(receive.getSecuId());
        setIsin(receive.getIsin());
        setRecord_update_time(receive.getRecordUpdateTime());
        setSecu_chinese_name(receive.getSecuChineseName());
        setSecu_english_name(receive.getSecuEnglishName());
        setSecu_base_id(receive.getSecuBaseId());
        setExch_type(receive.getExchType());
        setSecu_type(receive.getSecuType());
        setSecu_sub_type(receive.getSecuSubType());
        setCurrency(receive.getCurrency());
        setBond_par_value(receive.getBondParValue());
        setNot_list_stkqty(receive.getNotListStkqty());
        setLast_trade_date(receive.getLastTradeDate());
        setList_date(receive.getListDate());
        setProduct_set_id(receive.getProductSetId());
        setBuy_unit(receive.getBuyUnit());
        setSell_unit(receive.getSellUnit());
        setTrade_low_limit(receive.getTradeLowLimit());
        setTrade_high_limit(receive.getTradeHighLimit());
        setPre_close_price(receive.getPreClosePrice());
        setTick_price(receive.getTickPrice());
        setPrice_limit_type(receive.getPriceLimitType());
        setPrice_high_limit(receive.getPriceHighLimit());
        setPrice_low_limit(receive.getPriceLowLimit());
        setEx_right_ratio(receive.getExRightRatio());
        setDividend_price(receive.getDividendPrice());
        setFinancing_flag(receive.getFinancingFlag());
        setMargin_flag(receive.getMarginFlag());
        setSecu_status(receive.getSecuStatus());
        setMemo(receive.getMemo());
        setUuid(receive.getUuid());
        setBoid(receive.getBoid());
        setDestination(receive.getDestination());
    }

    public SourceStockBO_OTW(SourceStockBO bofrom){
        builder = SourceStockBO_PRO.SourceStockBO.newBuilder();
        bo= new SourceStockBO();
        setSecu_id(bofrom.secu_id);
        setIsin(bofrom.isin);
        setRecord_update_time(bofrom.record_update_time);
        setSecu_chinese_name(bofrom.secu_chinese_name);
        setSecu_english_name(bofrom.secu_english_name);
        setSecu_base_id(bofrom.secu_base_id);
        setExch_type(bofrom.exch_type);
        setSecu_type(bofrom.secu_type);
        setSecu_sub_type(bofrom.secu_sub_type);
        setCurrency(bofrom.currency);
        setBond_par_value(bofrom.bond_par_value);
        setNot_list_stkqty(bofrom.not_list_stkqty);
        setLast_trade_date(bofrom.last_trade_date);
        setList_date(bofrom.list_date);
        setProduct_set_id(bofrom.product_set_id);
        setBuy_unit(bofrom.buy_unit);
        setSell_unit(bofrom.sell_unit);
        setTrade_low_limit(bofrom.trade_low_limit);
        setTrade_high_limit(bofrom.trade_high_limit);
        setPre_close_price(bofrom.pre_close_price);
        setTick_price(bofrom.tick_price);
        setPrice_limit_type(bofrom.price_limit_type);
        setPrice_high_limit(bofrom.price_high_limit);
        setPrice_low_limit(bofrom.price_low_limit);
        setEx_right_ratio(bofrom.ex_right_ratio);
        setDividend_price(bofrom.dividend_price);
        setFinancing_flag(bofrom.financing_flag);
        setMargin_flag(bofrom.margin_flag);
        setSecu_status(bofrom.secu_status);
        setMemo(bofrom.memo);
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
    public SourceStockBO getBo() { 
        return bo;
    }

    public java.lang.String getSecu_id() {
        return builder.getSecuId();
    }

    public void setSecu_id(java.lang.String secu_id) {
        bo.secu_id = secu_id;
        builder.setSecuId(secu_id);
    }

    public java.lang.String getIsin() {
        return builder.getIsin();
    }

    public void setIsin(java.lang.String isin) {
        bo.isin = isin;
        builder.setIsin(isin);
    }

    public java.lang.String getRecord_update_time() {
        return builder.getRecordUpdateTime();
    }

    public void setRecord_update_time(java.lang.String record_update_time) {
        bo.record_update_time = record_update_time;
        builder.setRecordUpdateTime(record_update_time);
    }

    public java.lang.String getSecu_chinese_name() {
        return builder.getSecuChineseName();
    }

    public void setSecu_chinese_name(java.lang.String secu_chinese_name) {
        bo.secu_chinese_name = secu_chinese_name;
        builder.setSecuChineseName(secu_chinese_name);
    }

    public java.lang.String getSecu_english_name() {
        return builder.getSecuEnglishName();
    }

    public void setSecu_english_name(java.lang.String secu_english_name) {
        bo.secu_english_name = secu_english_name;
        builder.setSecuEnglishName(secu_english_name);
    }

    public java.lang.String getSecu_base_id() {
        return builder.getSecuBaseId();
    }

    public void setSecu_base_id(java.lang.String secu_base_id) {
        bo.secu_base_id = secu_base_id;
        builder.setSecuBaseId(secu_base_id);
    }

    public java.lang.String getExch_type() {
        return builder.getExchType();
    }

    public void setExch_type(java.lang.String exch_type) {
        bo.exch_type = exch_type;
        builder.setExchType(exch_type);
    }

    public java.lang.String getSecu_type() {
        return builder.getSecuType();
    }

    public void setSecu_type(java.lang.String secu_type) {
        bo.secu_type = secu_type;
        builder.setSecuType(secu_type);
    }

    public java.lang.String getSecu_sub_type() {
        return builder.getSecuSubType();
    }

    public void setSecu_sub_type(java.lang.String secu_sub_type) {
        bo.secu_sub_type = secu_sub_type;
        builder.setSecuSubType(secu_sub_type);
    }

    public java.lang.String getCurrency() {
        return builder.getCurrency();
    }

    public void setCurrency(java.lang.String currency) {
        bo.currency = currency;
        builder.setCurrency(currency);
    }

    public double getBond_par_value() {
        return builder.getBondParValue();
    }

    public void setBond_par_value(double bond_par_value) {
        bo.bond_par_value = bond_par_value;
        builder.setBondParValue(bond_par_value);
    }

    public java.lang.String getNot_list_stkqty() {
        return builder.getNotListStkqty();
    }

    public void setNot_list_stkqty(java.lang.String not_list_stkqty) {
        bo.not_list_stkqty = not_list_stkqty;
        builder.setNotListStkqty(not_list_stkqty);
    }

    public java.lang.String getLast_trade_date() {
        return builder.getLastTradeDate();
    }

    public void setLast_trade_date(java.lang.String last_trade_date) {
        bo.last_trade_date = last_trade_date;
        builder.setLastTradeDate(last_trade_date);
    }

    public java.lang.String getList_date() {
        return builder.getListDate();
    }

    public void setList_date(java.lang.String list_date) {
        bo.list_date = list_date;
        builder.setListDate(list_date);
    }

    public java.lang.String getProduct_set_id() {
        return builder.getProductSetId();
    }

    public void setProduct_set_id(java.lang.String product_set_id) {
        bo.product_set_id = product_set_id;
        builder.setProductSetId(product_set_id);
    }

    public int getBuy_unit() {
        return builder.getBuyUnit();
    }

    public void setBuy_unit(int buy_unit) {
        bo.buy_unit = buy_unit;
        builder.setBuyUnit(buy_unit);
    }

    public int getSell_unit() {
        return builder.getSellUnit();
    }

    public void setSell_unit(int sell_unit) {
        bo.sell_unit = sell_unit;
        builder.setSellUnit(sell_unit);
    }

    public int getTrade_low_limit() {
        return builder.getTradeLowLimit();
    }

    public void setTrade_low_limit(int trade_low_limit) {
        bo.trade_low_limit = trade_low_limit;
        builder.setTradeLowLimit(trade_low_limit);
    }

    public int getTrade_high_limit() {
        return builder.getTradeHighLimit();
    }

    public void setTrade_high_limit(int trade_high_limit) {
        bo.trade_high_limit = trade_high_limit;
        builder.setTradeHighLimit(trade_high_limit);
    }

    public double getPre_close_price() {
        return builder.getPreClosePrice();
    }

    public void setPre_close_price(double pre_close_price) {
        bo.pre_close_price = pre_close_price;
        builder.setPreClosePrice(pre_close_price);
    }

    public double getTick_price() {
        return builder.getTickPrice();
    }

    public void setTick_price(double tick_price) {
        bo.tick_price = tick_price;
        builder.setTickPrice(tick_price);
    }

    public java.lang.String getPrice_limit_type() {
        return builder.getPriceLimitType();
    }

    public void setPrice_limit_type(java.lang.String price_limit_type) {
        bo.price_limit_type = price_limit_type;
        builder.setPriceLimitType(price_limit_type);
    }

    public double getPrice_high_limit() {
        return builder.getPriceHighLimit();
    }

    public void setPrice_high_limit(double price_high_limit) {
        bo.price_high_limit = price_high_limit;
        builder.setPriceHighLimit(price_high_limit);
    }

    public double getPrice_low_limit() {
        return builder.getPriceLowLimit();
    }

    public void setPrice_low_limit(double price_low_limit) {
        bo.price_low_limit = price_low_limit;
        builder.setPriceLowLimit(price_low_limit);
    }

    public double getEx_right_ratio() {
        return builder.getExRightRatio();
    }

    public void setEx_right_ratio(double ex_right_ratio) {
        bo.ex_right_ratio = ex_right_ratio;
        builder.setExRightRatio(ex_right_ratio);
    }

    public double getDividend_price() {
        return builder.getDividendPrice();
    }

    public void setDividend_price(double dividend_price) {
        bo.dividend_price = dividend_price;
        builder.setDividendPrice(dividend_price);
    }

    public java.lang.String getFinancing_flag() {
        return builder.getFinancingFlag();
    }

    public void setFinancing_flag(java.lang.String financing_flag) {
        bo.financing_flag = financing_flag;
        builder.setFinancingFlag(financing_flag);
    }

    public java.lang.String getMargin_flag() {
        return builder.getMarginFlag();
    }

    public void setMargin_flag(java.lang.String margin_flag) {
        bo.margin_flag = margin_flag;
        builder.setMarginFlag(margin_flag);
    }

    public java.lang.String getSecu_status() {
        return builder.getSecuStatus();
    }

    public void setSecu_status(java.lang.String secu_status) {
        bo.secu_status = secu_status;
        builder.setSecuStatus(secu_status);
    }

    public java.lang.String getMemo() {
        return builder.getMemo();
    }

    public void setMemo(java.lang.String memo) {
        bo.memo = memo;
        builder.setMemo(memo);
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
        return "SourceStockBO_OTW ["+
            "uuid = " + getUuid() +"," +
            "boid = " + getBoid() +"," +
            "destination = " + getDestination() +"," +
            "secu_id = " + getSecu_id() +"," +
            "isin = " + getIsin() +"," +
            "record_update_time = " + getRecord_update_time() +"," +
            "secu_chinese_name = " + getSecu_chinese_name() +"," +
            "secu_english_name = " + getSecu_english_name() +"," +
            "secu_base_id = " + getSecu_base_id() +"," +
            "exch_type = " + getExch_type() +"," +
            "secu_type = " + getSecu_type() +"," +
            "secu_sub_type = " + getSecu_sub_type() +"," +
            "currency = " + getCurrency() +"," +
            "bond_par_value = " + getBond_par_value() +"," +
            "not_list_stkqty = " + getNot_list_stkqty() +"," +
            "last_trade_date = " + getLast_trade_date() +"," +
            "list_date = " + getList_date() +"," +
            "product_set_id = " + getProduct_set_id() +"," +
            "buy_unit = " + getBuy_unit() +"," +
            "sell_unit = " + getSell_unit() +"," +
            "trade_low_limit = " + getTrade_low_limit() +"," +
            "trade_high_limit = " + getTrade_high_limit() +"," +
            "pre_close_price = " + getPre_close_price() +"," +
            "tick_price = " + getTick_price() +"," +
            "price_limit_type = " + getPrice_limit_type() +"," +
            "price_high_limit = " + getPrice_high_limit() +"," +
            "price_low_limit = " + getPrice_low_limit() +"," +
            "ex_right_ratio = " + getEx_right_ratio() +"," +
            "dividend_price = " + getDividend_price() +"," +
            "financing_flag = " + getFinancing_flag() +"," +
            "margin_flag = " + getMargin_flag() +"," +
            "secu_status = " + getSecu_status() +"," +
            "memo = " + getMemo() +"," +
         "]";
    }
}
