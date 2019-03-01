#coding=UTF-8
import os
import sys
import datetime
# from datetime import datetime

import tushare as ts
import pandas as pd
import pymongo as mgdb

from core.MainService.PyPService import PyPService
from com.purefun.fstp.tushare.bo.otw.StockBasicInfoBO_OTW import StockBasicInfoBO_OTW
from core.log import PyPLogger
from core.mddate.MdData import MdBarData


class TuShareDataService(PyPService):
    '''
    classdocs
    '''

    def __init__(self, url = None, serviceName=None, env=None, instance=None):
        '''
        Constructor
        '''
        super(TuShareDataService, self).__init__(serviceName, env, instance)
        self.url = self.getConfigBean('main', 'envinfo', 'url')
        self.__dataUrl = url+'/data/' 
        self.__configUrl = url+'/config/'
        self.__resultUrl = url+'/result/'
        self.__basicData = self.getConfigBean('main', 'envinfo', 'basicData')
        
        self.__mongoClient = None
        self.log = PyPLogger.PyPLogger(TuShareDataService) 
        
    def initService(self, isInit = False):
        super(TuShareDataService, self).initService()
        self.__mongoClient = mgdb.MongoClient(self.getConfigBean('db', 'mongodb', 'mongoHost'),
                                              int(self.getConfigBean('db', 'mongodb', 'mongoPort')))
        if isInit:
            self.__initEnv()
        
    def startService(self):
        super(TuShareDataService, self).startService()
    
    def __initEnv(self):
        """
        :note: 初始化系统环境
        """
        self.__initConfig()
        self.__initDataAndResult()
    
    def __initConfig(self):
        """
        :note: 初始化config环境，包括获取所有股票列表等
        """
        if not os.path.exists(self.__configUrl):
            os.mkdir(self.__configUrl)
        if not os.path.exists(self.__configUrl + 'stocklist.csv'):
            self.getAllStockInfo(True) 
        if not os.path.exists(self.__configUrl + 'industry.csv'):
            self.getIndustryInfo(True)        
     
    def __initDataAndResult(self):
        """
        :note: 初始化data和result环境，包括获取指定股票的历史数据
        """
        if not os.path.exists(self.__dataUrl):
            os.mkdir(self.__dataUrl)
        
        if not os.path.exists(self.__resultUrl):
            os.mkdir(self.__resultUrl)
            
        if not os.path.exists(self.__configUrl + 'stocklist.csv'):
            stock_list = self.getAllStockInfo(True)
        else:   
            stock_list = self.getAllStockInfo(False)      #获取所有股票列表
        stock_list = stock_list.groupby('industry')
        
        for name,group in stock_list:        #  对每一个industry分别进行统计
            if not os.path.exists(self.__dataUrl + name):
                os.mkdir(self.__dataUrl +  name)
                
            if not os.path.exists(self.__resultUrl + name):
                os.mkdir(self.__resultUrl + name)
       
    
    def getAllStockInfo(self, isUpdate = False, source = 'csv'):
        """
        :note: 获取所有股票的基础信息,如果是更新，会从ts获取（首次初始化也从ts获取）get_stock_basics；
                                            否则会从本地获取.              
        :param isUpdate: 如果是更新，会从ts获取（首次初始化也从ts获取）；否则会从本地获取
        :param source: 该字段只在isUpdate=false起作用。csv:从系统csv获取；mongodb：从mongodb获取  
        :return: 所有股票的基础数据信息 datafram
        """
        stock_list = None
        fileName = '/stocklist.csv'
        
        if isUpdate:    #  全量更新，从ts获取数据，并publish到系统，并更新cache内数据             
            stock_list = ts.get_stock_basics()
            for i in stock_list.index:   
                temp = str(stock_list.loc[i,'timeToMarket'])
                if len(temp) >2:
                    year = temp[0:4]
                    month = temp[4:6]
                    day = temp[6:8]
                    listDay = year +'-' + month + '-' + day      
                    stock_list.ix[i,'timeToMarket'] = listDay
                else:
                    pass
            stock_list.to_csv(self.__configUrl + fileName, index=True,encoding='gbk')
            if self.cache.getAll('StockBasicInfoBO') is not None:
                self.cache.delCache('StockBasicInfoBO')
            
            stock_list = pd.read_csv(self.__configUrl + fileName, encoding = 'gbk')    
            for name,item in stock_list.iterrows():
                bo = StockBasicInfoBO_OTW()
                bo.setStockcode(str(item['code']).zfill(6))
                bo.setStockname(item['name'].encode('utf-8'))
                bo.setIndustry(item['industry'].encode('utf-8'))
                bo.setArea(item['area'].encode('utf-8'))
                bo.setPe(item['pe'])
                bo.setOffer_shares(item['outstanding'])
                bo.setTotal_shares(item['totals'])
                bo.setTotalAssets(item['totalAssets'])
                bo.setLiquidAssets(item['liquidAssets'])
                bo.setFixedAssets(item['fixedAssets'])                
                bo.setReserved(item['reserved'])
                bo.setReservedPerShare(item['reservedPerShare'])
                bo.setEsp(item['esp'])
                bo.setBvps(item['bvps'])
                bo.setPb(item['pb'])                
                bo.setList_date(item['timeToMarket'].encode('utf-8'))
                bo.setNo_dividend(item['undp'])
                bo.setPerundp(item['perundp'])
                bo.setRev_per(item['rev'])
                bo.setProfit(item['profit'])                
                bo.setGross_profit(item['gpr'])               
                bo.setNet_profit(item['npr'])
                bo.setHolder(int(item['holders']))
                self.pub.publish(bo, durFlag = True, key = 'Stockcode')
        else :
            if source is 'csv':
                stock_list = pd.read_csv(self.__configUrl + fileName, encoding = 'gbk')
            else:
                stock_list = self.__getAllStockInfoFromMongDB()
       
        return stock_list
    
    def getIndustryInfo(self, isUpdate = False):
        """
        :note: 获取所有股票所属行业，没有直接用ts的接口
        :param isUpdate: 如果是更新，会从ts获取（首次初始化也从ts获取）；否则会从本地获取
        :return: 所有股票的基础数据信息 datafram
        :
        """
        stock_list = None
        fileName = '/industry.csv'
        
        if isUpdate:            
            stock_list = self.getAllStockInfo(False)
            stock_list = stock_list.groupby('industry')

            tempList = []
            industryConfig = pd.DataFrame()
            for name,group in stock_list:
                tempList.append(name)
            industryConfig = pd.DataFrame({'industry':pd.Series(tempList), 'if_read':0})
            industryConfig = industryConfig.set_index('industry')
            industryConfig.to_csv(self.__configUrl + fileName, index=True, encoding='gbk')          
        else :           
            industryConfig = pd.read_csv(self.__configUrl + fileName, encoding = 'gbk')           
        
        return industryConfig  
        
    def getAllStockHis(self, start = '', end = '', isUpdate = False, breakStockCode = None):
        """
        :note: 获取股票历史数据，通过ts的get_k_data接口.根据config/industry里if_read=1的行业进行获取。startdate如果没有
                                            设置，使用basicDate = ‘2012-01-01’，结束日期默认为当日
        :param code: stock code
        :param start: start date
        :param end: end date
        :param isUpdate: 是更新数据取出本地数据
        :param breakStockCode:因为某种原因，下载中断的stock code，再次执行时，从此断点开始更新。要与industry.csv的配置一致
        :return: 股票历史数据，当isUpdate=false时，优先去本地数据，如果本地没有，从ts获取
        """   
        stockMap = self.cache.getAll('StockBasicInfoBO') 
        tempList = []
        for v in stockMap.values():
            e = StockBasicInfoBO_OTW(v)
            tempList.append([e.getStockcode(), e.getStockname(), e.getIndustry(), e.getList_date()])             
        stock_list = pd.DataFrame(tempList, columns=['code','name','industry','listdate'])
        stock_list = stock_list.groupby('industry')
         
        industry_list = self.getIndustryInfo(False) 
        industry_list = industry_list[['industry', 'if_read']]
        industry_list = industry_list.set_index('industry') 
        for industry,group in stock_list:        #  对每一个industry分别进行统计
            if (industry_list.loc[industry].if_read == 0) :
                    continue
            group = group.set_index('code')
            for code in group.index:
                if not breakStockCode is None:                    
                    if code == breakStockCode:
                        breakStockCode = None
                    else:
                        self.log.info('jump stock ', code)
                        continue
                
                self.log.info('get code = ', code, ' history Bar')
                self.getSinagleStockHis(industry = industry, stockCode = code, startDate = start, endDate = end, isUpdate = isUpdate)
    
    def getSingleStockHis(self, industry = None, stockCode = None, startDate = '', endDate = '', isUpdate = False):
        """
        :note: 获取股票历史数据，通过ts的get_k_data接口;或者获取本地数据
        :param industry: 行业，取单独某一个数据时，不需要填写
        :param stockCode: 股票code
        :param startDate: 开始日期
        :param endDate: 结束日期
        :param isUpdate: 是否是更新数据，如果是更新数据，会在现有文件后添加新的数据。如果==false，会从本地获取数据文件
        :return: 指定的股票Datafram
        """
        if stockCode is None: 
            self.log.error('stockCode not empty')
            return None        
#         if industry is None:
        bo = StockBasicInfoBO_OTW(self.cache.get('StockBasicInfoBO', stockCode))
        industry = bo.getIndustry()
        
        if endDate == '':
            endDate = datetime.datetime.now().strftime('%Y-%m-%d')         
        if startDate == '':
            start = self.__basicData            
                     
        fileName = self.__dataUrl + industry + '/' + stockCode  + '.csv' 
        fileExists = os.path.exists(fileName)
        if isUpdate:
            if fileExists:
                temp = pd.read_csv(fileName,encoding = 'gbk')
                temp = temp.tail(1)
                delta = datetime.timedelta(days=1)
                temp1 = ''.join(temp.iloc[0:1,0].values)
                if temp1.find('/') != -1:
                        temp1 = temp1.replace('/','-')
                timeOld = datetime.datetime.strptime(temp1, '%Y-%m-%d')
                start = (delta + timeOld).strftime('%Y-%m-%d')
                finalStartDay = start
                       
        if isUpdate == False and fileExists == False :
            listdate = bo.getList_date()
            if len(listdate) < 2 :
#                 self.log.info(stockCode, ' ', bo.getStockname().encode('utf-8'), '  is a preIPO stock')
                return None
            
            listDay = datetime.datetime.strptime(listdate,'%Y-%m-%d')
            startDay = datetime.datetime.strptime(start,'%Y-%m-%d')

            if listDay < startDay:
                finalStartDay = startDay.strftime('%Y-%m-%d')
            else:
                finalStartDay = listDay.strftime('%Y-%m-%d')
                    
            data = ts.get_k_data(code=stockCode, start=finalStartDay, end=endDate)
            data = data[['date', 'open','close','high','low','volume']]
            data = data.set_index('date')
            data['change'] = (data['close'].shift(0) - data['close'].shift(1))/data['close'].shift(1)
            if isUpdate and fileExists:
                data.to_csv(fileName, index=True,encoding='gbk', mode='a', header=False)
            else:
                data.ix[0,'change'] =( data.ix[0,'close']-data.ix[0,'open'])/data.ix[0,'open']
                data.to_csv(fileName, index=True, encoding='gbk') 
            self.log.info('get or update ', stockCode, ' successful')             
        elif isUpdate == False and fileExists == True : #如果是取出数据，直接从系统中获取对应数据   
            data = pd.read_csv(fileName, encoding = 'gbk')
            data = data.set_index('date')
            head = None
            tail = None
            if not endDate == '':
                tail = endDate         
            if not startDate == '':
                head = startDate
                
            if head is None and  tail is None:         
                data = data[:]
            elif head is None and not tail is  None:
                data = data[:tail]
            elif not head is None and tail is None:
                data = data[head:]
            else:
                data = data[head:tail]
              
        return data    
    
    def __loadStockBarCSV2MGDB(self, industry = None, stockCode=None): 
        """
        :note: 加载csv的bar型数据到mongodb
        :param stockCode: 股票code
        :return: false、true
        """ 
        if stockCode is None:
            self.log.error('load ', stockCode ,' error...')
            return False
        data = service.getSingleStockHis(stockCode = stockCode, isUpdate = False)
        if data is None:
            self.log.info('jump ', stockCode ,' ...')
            return True

        collection = self.__mongoClient['fstp_stock_bar_' + industry][stockCode]
        collection.ensure_index([('date', mgdb.ASCENDING)], unique=True)
#         dbCursor = collection.find()
#         for d in dbCursor:
#             print d
        self.log.info('load ', stockCode ,' begin...')
        for i in range(len(data)):
            bar = MdBarData()  
            bar.date = data.iloc[i].name    
            bar.open = data.iloc[i].open
            bar.high = data.iloc[i].high
            bar.low = data.iloc[i].low
            bar.close = data.iloc[i].close
            bar.volume = data.iloc[i].volume
            bar.change = data.iloc[i].change        
            flt = {'date': bar.date} 
            collection.update_one(flt, {'$set':bar.__dict__}, upsert=True) 
         
            
    def saveBar2MGDB(self, breakStockCode=None):
        stockMap = self.cache.getAll('StockBasicInfoBO') 
            
        for v in stockMap.values():
            e = StockBasicInfoBO_OTW(v)
            if not breakStockCode is None:                    
                    if e.getStockcode() == breakStockCode:
                        breakStockCode = None
                    else:
                        self.log.info('jump stock ', e.getStockcode())
                        continue
            self.__loadStockBarCSV2MGDB(e.getIndustry(), e.getStockcode())


service = TuShareDataService(url='D:/tushareData', serviceName='TuShareDataService', env='DEV', instance='1')
service.initService(isInit = False)
service.startService()
# service.saveBar2MGDB(breakStockCode='300724')


# service.getAllStockHis(breakStockCode = '300462')
raw_input()     


        