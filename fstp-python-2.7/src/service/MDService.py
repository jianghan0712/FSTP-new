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


pd.set_option('display.max_columns',500)
pd.set_option('display.width',1000)


class MDService(PyService):
    '''
    classdocs
    '''
    def __init__(self, serviceName, env, instance):
        super(MDService, self).__init__(serviceName, env, instance)
        self.log = PyPLogger(MDService)
        self.strategy = None
        self.datacontainer = None
                
    #----------------------------------------------------------------------    
    def initService(self):
        super(MDService, self).initService()  
        self.configs = super(MDService, self).getConfigBean('main', 'Strategy') 
          
        self.__init_MdContainer(self.configs)           #初始化数据容器
        
        
    #----------------------------------------------------------------------      
    def startService(self):
        super(MDService, self).startService()       
        self.datacontainer.runHistoryBar()  #
    
    #----------------------------------------------------------------------    
    def __init_MdContainer(self, configs):
        self.datacontainer = MdContainer(self.log, self.enginer, **dict(configs))


        

service = MDService('StrategyService',"DEV","1")
service.initService()
service.startService()