package com.purefun.fstp.core.bo;

import com.purefun.fstp.core.bo.tool.fstpbo;

@fstpbo(boid = 1501L, destination = "fstp.ace.rds.source.stock")
public class SourceStockBO extends BaseBO {
	public String secu_id = "";
	public String isin = "";
	public String record_update_time = "";
	public String secu_chinese_name = "";
	public String secu_english_name = "";
	public String secu_base_id = "";
	public String exch_type = "";
	public String secu_type = "";
	public String secu_sub_type = "";
	public String currency = "";
	public double bond_par_value = 0.0;
	public String not_list_stkqty = "";
	public String last_trade_date = "";
	public String list_date = "";
	public String product_set_id = "";
	public int buy_unit = 0;
	public int sell_unit = 0;
	public int trade_low_limit = 0;
	public int trade_high_limit = 0;
	public double pre_close_price = 0.0;
	public double tick_price = 0.0;
	public String price_limit_type = "";
	public double price_high_limit = 0.0;
	public double price_low_limit = 0.0;
	public double ex_right_ratio = 0.0;
	public double dividend_price = 0.0;
	public String financing_flag = "";
	public String margin_flag = "";
	public String secu_status = "";
	public String memo = "";
}
