import uuid

class SourceStockBO(object):

    def __init__(self):
        self.secu_id = ''
        self.isin = ''
        self.record_update_time = ''
        self.secu_chinese_name = ''
        self.secu_english_name = ''
        self.secu_base_id = ''
        self.exch_type = ''
        self.secu_type = ''
        self.secu_sub_type = ''
        self.currency = ''
        self.bond_par_value = 0.0
        self.not_list_stkqty = ''
        self.last_trade_date = ''
        self.list_date = ''
        self.product_set_id = ''
        self.buy_unit = 0
        self.sell_unit = 0
        self.trade_low_limit = 0
        self.trade_high_limit = 0
        self.pre_close_price = 0.0
        self.tick_price = 0.0
        self.price_limit_type = ''
        self.price_high_limit = 0.0
        self.price_low_limit = 0.0
        self.ex_right_ratio = 0.0
        self.dividend_price = 0.0
        self.financing_flag = ''
        self.margin_flag = ''
        self.secu_status = ''
        self.memo = ''
        self.uuid = str(uuid.uuid1())
        self.boid = 1501
        self.destination = "fstp.ace.rds.source.stock"
