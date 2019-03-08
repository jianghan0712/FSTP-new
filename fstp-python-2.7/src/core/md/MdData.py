#coding=UTF-8

class MdData(object):

    def __init__(self):
        '''
        Constructor
        '''

class MdBarData(MdData):
    """日线数据"""

    #----------------------------------------------------------------------
    def __init__(self):
        """Constructor"""
        super(MdBarData, self).__init__()
        self.date = ''                   
        self.open = 0.0             # OHLC
        self.high = 0.0
        self.low = 0.0
        self.close = 0.0        

        self.volume = 0         # 成交量
        self.change = 0.0       # 持仓量            