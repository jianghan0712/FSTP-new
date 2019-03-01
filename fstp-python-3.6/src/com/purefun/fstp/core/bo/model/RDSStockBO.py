import uuid

class RDSStockBO(object):

    def __init__(self):
        self.product_id = ''
        self.secu_name_cn = ''
        self.secu_name_en = ''
        self.exch_type = ''
        self.secu_type = ''
        self.secu_sub_type = ''
        self.currency = ''
        self.list_date = ''
        self.buy_unit = 0
        self.sell_unit = 0
        self.trade_low_limit = 0
        self.trade_high_limit = 0
        self.pre_close_price = 0.0
        self.tick_price = 0.0
        self.price_high_limit = 0.0
        self.price_low_limit = 0.0
        self.ex_right_ratio = 0.0
        self.dividend_price = 0.0
        self.financing_flag = ''
        self.margin_flag = ''
        self.secu_status = ''
        self.memo = ''
        self.update_time = ''
        self.uuid = str(uuid.uuid1())
        self.boid = 1001
        self.destination = "fstp.ace.rds.server.stock"
