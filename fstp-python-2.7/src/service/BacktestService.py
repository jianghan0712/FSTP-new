#encoding=utf-8
import sys
import pandas as pd

from src.core.PService.PyService import PyService
from src.core.log.PyPLogger import PyPLogger
from src.service.strategy.StrategyContainer import StrategyContainer,Listener
from src.service.datacontainer.MdContainer import MdContainer
from src.service.engine.EventEnginer import EventEnginer
from src.service.engine.EventType import *
from src.service.engine.Event import Event
from src.core.ipc.qpid.PyQpidConnect import PySubscriber, SubListener,\
    PyPublisher
from src.com.purefun.fstp.core.bo.otw.MarketBarBO_OTW import MarketBarBO_OTW
from src.com.purefun.fstp.core.bo.otw.CommandBO_OTW import CommandBO_OTW


pd.set_option('display.max_columns',500)
pd.set_option('display.width',1000)


class BacktestService(PyService):
    '''
    classdocs
    '''
    def __init__(self, serviceName, env, instance):
        super(BacktestService, self).__init__(serviceName, env, instance)
        self.log = PyPLogger(BacktestService)
        self.strategy = None
        self.datacontainer = None
                
    #----------------------------------------------------------------------    
    def initService(self):
        super(BacktestService, self).initService()  
        self.configs = super(BacktestService, self).getConfigBean('main', 'Strategy') 
        
    #----------------------------------------------------------------------      
    def startService(self):
        super(BacktestService, self).startService()   
        subLis = mySubListener(self.log)
        sub = PySubscriber(self.session,  subLis, self.log)    
        
        bo = CommandBO_OTW()
        bo.setParm0('fstp_stock_bar_中成药')
        bo.setParm1('000423')
        pub = PyPublisher(self.session, self.log, None)
        pub.publish(bo)
        sub.subscribe('amq.topic/fstp.md.bar')


class mySubListener(SubListener):
    def __init__(self, log):
        super(mySubListener, self).__init__(log)
        
    def doTask(self, bytebo):
        bo_otw = MarketBarBO_OTW(bytebo) 
        self.log.info("Receive BO:{}",bo_otw.toString())
        

service = BacktestService('BacktestService',"DEV","1")
service.initService()
service.startService()



