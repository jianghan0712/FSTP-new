import uuid

class StockBasicInfoBO(object):

    def __init__(self):
        self.stockcode = ''
        self.stockname = ''
        self.industry = ''
        self.area = ''
        self.pe = 0.0
        self.offer_shares = 0.0
        self.total_shares = 0.0
        self.totalAssets = 0.0
        self.liquidAssets = 0.0
        self.fixedAssets = 0.0
        self.reserved = 0.0
        self.reservedPerShare = 0.0
        self.esp = 0.0
        self.bvps = 0.0
        self.pb = 0.0
        self.list_date = ''
        self.no_dividend = 0.0
        self.perundp = 0.0
        self.rev_per = 0.0
        self.profit = 0.0
        self.gross_profit = 0.0
        self.net_profit = 0.0
        self.holder = 0
        self.uuid = str(uuid.uuid1())
        self.boid = 2001
        self.destination = "fstp.ts.stockbasicinfo"
