#encoding=utf-8
import pandas as pd
from src.service.strategy.StrategyContainer import StrategyContainer

class DoubleMA(StrategyContainer):
    def __init__(self, log, eventEnginer, **configs ):
        super(DoubleMA, self).__init__(log, eventEnginer, **configs)
    
    def computeBar(self, event):
        industry = event.key['industry']
        code = event.key['stockcode']
        stock_data = event.data
        
        ####    计算指标,由继承的类实现
        #----------------------------------------------------------------------    
        stock_data['ma_short'] = stock_data['close'].rolling(window=12).mean()
        stock_data['ma_long'] = stock_data['close'].rolling(window=26).mean()
        stock_data.ix[0,'position'] = 0
        
        stock_data.ix[(stock_data['ma_short'].shift(2) < stock_data['ma_long'].shift(2)) &
                      (stock_data['ma_short'].shift(1) > stock_data['ma_long'].shift(1)) &
                      (stock_data['open'] != stock_data['close']) , 'position'] = 1
        stock_data.ix[(stock_data['ma_short'].shift(2) > stock_data['ma_long'].shift(2)) &
                      (stock_data['ma_short'].shift(1) < stock_data['ma_long'].shift(1)) &
                      (stock_data['open'] != stock_data['close']) , 'position'] = 0
                      
        stock_data['position'].fillna(method='ffill', inplace=True)
        stock_data['position'].fillna(0, inplace=True)
            
        ####     统计交易明细，即计算各类收益指标
        #----------------------------------------------------------------------
        StrategyContainer.tradeSim(self, industry, code, stock_data)
       
