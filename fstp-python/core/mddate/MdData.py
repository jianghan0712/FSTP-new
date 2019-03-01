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
        
#         self.code = ''        # vt系统代码
#         self.symbol = EMPTY_STRING          # 代码
#         self.exchange = EMPTY_STRING        # 交易所
    
        self.open = 0.0             # OHLC
        self.high = 0.0
        self.low = 0.0
        self.close = 0.0
        
        self.date = ''            # bar开始的时间，日期
#         self.time = EMPTY_STRING            # 时间
#         self.datetime = None                # python的datetime时间对象
        
        self.volume = 0             # 成交量
        self.change = 0.0       # 持仓量            