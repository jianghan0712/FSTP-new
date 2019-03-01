package com.purefun.fstp.core.bo;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import com.purefun.fstp.core.bo.tool.fstpbo;

@Entity  
@Table(name="RDS_STOCK") 
@fstpbo(boid = 1001L, destination = "fstp.ace.rds.server.stock")
public class RDSStockBO extends BaseBO {	
	@QuerySqlField(index=true)
	public String product_id = "";
	@QuerySqlField
	public String secu_name_cn = "";
	@QuerySqlField
	public String secu_name_en = "";
	@QuerySqlField
	public String exch_type = "";
	@QuerySqlField
	public String secu_type = "";
	@QuerySqlField
	public String secu_sub_type = "";
	@QuerySqlField
	public String currency = "";
	@QuerySqlField
	public String list_date = "";
	@QuerySqlField
	public int buy_unit = 0;
	@QuerySqlField
	public int sell_unit = 0;
	@QuerySqlField
	public int trade_low_limit = 0;
	@QuerySqlField
	public int trade_high_limit = 0;
	@QuerySqlField
	public double pre_close_price = 0.0;
	@QuerySqlField
	public double tick_price = 0.0;
	@QuerySqlField
	public double price_high_limit = 0.0;
	@QuerySqlField
	public double price_low_limit = 0.0;
	@QuerySqlField
	public double ex_right_ratio = 0.0;
	@QuerySqlField
	public double dividend_price = 0.0;
	@QuerySqlField
	public String financing_flag = "";
	@QuerySqlField
	public String margin_flag = "";
	@QuerySqlField
	public String secu_status = "";
	@QuerySqlField
	public String memo = "";
	@QuerySqlField
	public String update_time = "";	
}
