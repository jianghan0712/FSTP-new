#encoding=utf-8
import copy
import os

import pandas as pd

from src.service.engine.Event import Event
from src.service.engine.EventType import *

StockList_Path = '../../resource/stocklist_all.csv'
Save_Path = '../../result/'

class StrategyContainer(object):    
    DEFAULT_CONFIG = {
        'name' : 'anonymous',
        'author' : 'FSTP',
        'bartype' : 'dayBar',
        'timebegin' : '20180101',
        'timeend' : 'lastday',
        'industry' : [],
        'stockcode': [],
        'dbtype' : 'mongodb',
        'dbhost' : 'local',
        'dbport' : ''
    }
      
    def __init__(self, log, eventEnginer, **configs ):
        self.log = log
        self.__eventEnginer = eventEnginer
        self.stock_pool = {}
        self.savePath = None
        
        extra_configs = set(configs).difference(self.DEFAULT_CONFIG)
        
        if extra_configs:
            self.log.warn("Unrecognized configs: %s" % (extra_configs,))
        
        self.config = copy.copy(self.DEFAULT_CONFIG)
        self.config.update(configs)
               
        self.log.info('load strategy configs -------------------------->')
        for (k,v) in self.config.items():           
            self.log.info('    {} : {}',k,v)
        
        self.log.info('init stock list -------------------------->')
        self.__getStockList()
        self.__checkEvn()
     

    #----------------------------------------------------------------------    
    def computeBar(self, event, subclazz):
        '''
        @note: 入口，运行策略，在子类中实现
        '''
        pass
        
    #---------------------------------------------------------------------- 
    def Command(self, command:str,key=None,pdData=None):  
        event = Event(type_=command, key=key, data=pdData)
        self.__eventEnginer.SendEvent(event)
    
    #----------------------------------------------------------------------        
    def __getStockList(self): 
        '''
        @note: 根据config中的industry和stockcode来准备加载用的stocklist
                industry='all'时，加载所有股票
                industry=空时，按stockcode自定义加载股票
                industry=行业，stockcode可以为空也可以不为空,为空时加载该行业下所有股票，不为空时只加载指定股票
        '''
        if self.config['industry'] == '' and self.config['stockcode'] != '':
            self.stock_pool = {'user-define':self.config['stockcode'].split(',')}
            return
        
        s = pd.read_csv(StockList_Path,encoding = 'gbk')
        s = s[['code','industry']].groupby('industry')
        
        if self.config['industry'] == 'all':               
            for name,group in s:
                templist=[]
                for i in group['code'].values:  #补齐code为6位
                    i = str(i).zfill(6)
                    templist.append(i)
                self.stock_pool[name] = templist                                            
        elif self.config['industry'] != '' :
            tempInduList = self.config['industry'].split(',')
            if self.config['stockcode'] != '':
                tempStockList = self.config['stockcode'].split(',')
                for name,group in s:
                    templist=[]
                    if name in tempInduList:
                        for i in group['code'].values:  #补齐code为6位
                            i = str(i).zfill(6)
                            if i in tempStockList:
                                templist.append(i)                           
                        self.stock_pool[name] = templist
            else :    
                for name,group in s:
                    templist=[]
                    if name in tempInduList:
                        for i in group['code'].values:  #补齐code为6位
                            i = str(i).zfill(6)
                            templist.append(i)                           
                        self.stock_pool[name] = templist
        self.log.info('load stock pool succeful')
        for k,v in self.stock_pool.items():   
            self.log.info('    industry:{}',k)
            self.log.info('    stocklist:{}',v)        

    #----------------------------------------------------------------------         
    def __checkEvn(self, userPath = None):
        resultPath = ''
        if userPath is None:
            resultPath = Save_Path
        else:
            resultPath = userPath
            
        if not os.path.exists(resultPath):
            os.mkdir(resultPath)
        
        if self.stock_pool is None:
            return
        
        if not os.path.exists(resultPath + self.config['name'] + '/'):
            os.mkdir(resultPath + self.config['name'] + '/')
        
        straPath = resultPath + self.config['name'] + '/'
                   
        for industry,stocklist in self.stock_pool.items():
            if not os.path.exists(straPath + industry + '/'):
                os.mkdir(straPath + industry + '/')
            self.log.info('init result env: {}', industry)
            for e in stocklist:
                if not os.path.exists(straPath + industry + '/' +e + '/'):
                    os.mkdir(straPath + industry + '/' +e + '/')  
                                
        self.savePath = straPath
        self.log.info('init {} result compelete !', self.config['name'])
            
    #----------------------------------------------------------------------         
    def runHistoryBar(self):
        '''
        @note:上层服务调用回测服务的入口
        '''
        if self.stock_pool is None:
            return
        for k,v in self.stock_pool.items(): 
            for s in v:
                self.Command(command=EVENT_COMMAND_NEXT_STOCK,key={'industry':k,'stockcode':s})

    
    #----------------------------------------------------------------------             
    def tradeSim(self, industry, code, stock_data=None):
        if stock_data is None:
            return
        ####    计算每日盈亏数据
        #----------------------------------------------------------------------
        stock_data.ix[0, 'capital_perday'] = 0
        stock_data.ix[(stock_data['position']==1) & (stock_data['position'].shift(1)==0), 'capital_perday'] = (stock_data['close'] / stock_data['open'] - 1) 
        stock_data.ix[(stock_data['position']==0) & (stock_data['position'].shift(1)==1), 'capital_perday'] = (stock_data['open'] / stock_data['close'].shift(1) - 1)  
        stock_data.ix[stock_data['position'] == stock_data['position'].shift(1) , 'capital_perday'] = (stock_data['change']) * stock_data['position']
   
        stock_data['capital'] = (stock_data['capital_perday'] + 1).cumprod()   
        ####    计算每次交易盈亏率
        #----------------------------------------------------------------------
            #  买入或者加仓时的日期和初始资产
        stock_data.ix[stock_data['position'] > stock_data['position'].shift(1), 'start_date'] = stock_data['date']
        stock_data.ix[stock_data['position'] > stock_data['position'].shift(1), 'start_capital'] = stock_data['capital'].shift(1)
        stock_data.ix[stock_data['position'] > stock_data['position'].shift(1), 'start_stock'] = stock_data['open']
            # 记录卖出时的日期和当天的资产
        stock_data.ix[stock_data['position'] < stock_data['position'].shift(1), 'end_date'] = stock_data['date']
        stock_data.ix[stock_data['position'] < stock_data['position'].shift(1), 'end_capital'] = stock_data['capital']
        stock_data.ix[stock_data['position'] < stock_data['position'].shift(1), 'end_stock'] = stock_data['open']
        
        ####     将买卖当天的信息合并,以便后续导出交易明细
        #----------------------------------------------------------------------           
        df_temp = stock_data[stock_data['start_date'].notnull() | stock_data['end_date'].notnull()]   
        df_temp['end_date'] = df_temp['end_date'].shift(-1)
        df_temp['end_capital'] = df_temp['end_capital'].shift(-1)
        df_temp['end_stock'] = df_temp['end_stock'].shift(-1)
        
        # 构建账户交易情况dataframe：'hold_time'持有天数，'trade_return'该次交易盈亏,'stock_return'同期股票涨跌幅
        
        trade = df_temp.ix[df_temp['end_date'].notnull(), ['start_date', 'start_capital', 'start_stock',
                                                           'end_date', 'end_capital', 'end_stock']]       
        trade.reset_index(drop=True, inplace=True)
        
        if len(trade) == 0:
            trade.ix[0,'hold_time'] = 0
            trade.ix[0,'trade_return'] = 0
            trade.ix[0,'stock_return'] = 0
            trade.ix[0,'gain_time'] = 0
            trade.ix[0,'successive_gain'] = 0
            return trade
        trade['hold_time'] = (pd.to_datetime(trade['end_date']) - pd.to_datetime(trade['start_date'])).dt.days
        trade['trade_return'] = trade['end_capital'] / trade['start_capital'] - 1
        trade['stock_return'] = trade['end_stock'] / trade['start_stock'] - 1
    
        #计算指标
        trade_num = len(trade)  # 计算交易次数
        max_holdtime = trade['hold_time'].max()  # 计算最长持有天数
        average_change = trade['trade_return'].mean()  # 计算每次平均涨幅
        max_gain = trade['trade_return'].max()  # 计算单笔最大盈利
        max_loss = trade['trade_return'].min()  # 计算单笔最大亏损
        # 计算年均买卖次数
        trade_per_year = trade_num / ((pd.to_datetime(trade['end_date'].iloc[-1]) - pd.to_datetime(trade['start_date'].iloc[0])).days / 365)      
        # 计算连续盈利亏损的次数
        trade.ix[trade['trade_return'] > 0, 'gain'] = 1
        trade.ix[trade['trade_return'] < 0, 'gain'] = 0
        
        trade['gain'].fillna(method='ffill', inplace=True)
        
        # 根据gain这一列计算连续盈利亏损的次数
        rtn_list = list(trade['gain'])
        
        successive_gain_list = []
        num = 1
        for i in range(len(rtn_list)):
            if i == 0:
                successive_gain_list.append(num)
            else:
                if (rtn_list[i] == rtn_list[i - 1] == 1) or (rtn_list[i] == rtn_list[i - 1] == 0):
                    num += 1
                else:
                    num = 1
                successive_gain_list.append(num)
        
        # 连续盈利亏损的次数
        trade['successive_gain'] = successive_gain_list
        max_successive_gain = 0
        max_successive_loss = 0
        if trade[trade['gain'] == 1].size != 0:
            max_successive_gain = trade[trade['gain'] == 1].sort_values(by='successive_gain', ascending=False)['successive_gain'].iloc[0]
        if trade[trade['gain'] == 0].size != 0:
            max_successive_loss = trade[trade['gain'] == 0].sort_values(by='successive_gain', ascending=False)['successive_gain'].iloc[0]
            
        #  输出账户交易各项指标
        print( '-----------------------------------------------------------------------------------------------')
        print('\n|当前策略类型：',self.config['name']) 
        print('|回测股票行业和代码： ',industry, '  - ', code)
        print( '==============每笔交易明细===============')
        print( trade[['start_date',  'start_capital',  'start_stock',   'end_date',  'end_capital',  'end_stock']])
        print( '\n====================账户交易的各项指标=====================')
        print( '|交易次数为：%d   最长持有天数为：%d' % (trade_num, max_holdtime))
        print( '|每次平均涨幅为：%f' % average_change)
        print( '|单次最大盈利为：%f  单次最大亏损为：%f' % (max_gain, max_loss))
        print( '|年均买卖次数为：%f' % trade_per_year)
        print( '|最大连续盈利次数为：%d  最大连续亏损次数为：%d' % (max_successive_gain, max_successive_loss))
        
        #    保存交易信息
        self.saveResult(stock_data, trade, industry, code)        
 
 
    #----------------------------------------------------------------------             
    def saveResult(self, stock_data, trade, industry, code):
        path = self.savePath + industry +'/' + code +'/'
        stock_data.to_csv(path + code + '_' + self.config['name'] + '_detail.csv',encoding='gbk',index=False) 
        trade.to_csv(path + code + '_' + self.config['name'] + '_trade.csv',encoding='gbk',index=False)     
        
                