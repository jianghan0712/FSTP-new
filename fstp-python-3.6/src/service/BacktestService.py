#encoding=utf-8
import sys
import pandas as pd

from src.core.PService.PyService import PyService
from src.core.log.PyPLogger import PyPLogger
from src.service.strategy.StrategyContainer import StrategyContainer
from src.service.datacontainer.MdContainer import MdContainer
from src.service.engine.EventEnginer import EventEnginer
from src.service.engine.EventType import *
from src.service.engine.Event import Event


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
        self.enginer = None
        
                
    #----------------------------------------------------------------------    
    def initService(self):
        super(BacktestService, self).initService()  
        self.configs = dict(super(BacktestService, self).getConfigBean('main', 'Strategy')) 
          
        self.__init_EventEnginer()                      #初始化事件引擎
        self.__init_MdContainer(self.configs)           #初始化数据容器
        self.__init_StrategyContainer(self.configs)     #初始化策略容器
        
        self.enginer.AddEventListener(EVENT_MD_BAR, self.strategy.computeBar)
        self.enginer.AddEventListener(EVENT_COMMAND_NEXT_STOCK, self.datacontainer.receiveCommand)
        self.enginer.Start()
        
    #----------------------------------------------------------------------      
    def startService(self):
        super(BacktestService, self).startService()      
#         self.datacontainer.runHistoryBar()  
        self.strategy.runHistoryBar()

    #----------------------------------------------------------------------
    def __init_StrategyContainer(self, configs):
        '''
        @note: 根据config,加载指定的strategy
        '''     
        modul = getattr(__import__('strategy.' + self.configs['name']),self.configs['name'])
        c = getattr(modul,self.configs['name'])
        self.strategy = c(self.log, self.enginer, **dict(configs))
    
    #----------------------------------------------------------------------    
    def __init_MdContainer(self, configs):
        self.datacontainer = MdContainer(self.log, self.enginer, **dict(configs))

    #----------------------------------------------------------------------    
    def __init_EventEnginer(self):
        self.enginer = EventEnginer(self.log)
        

service = BacktestService('BacktestService',"DEV","1")
service.initService()
service.startService()
