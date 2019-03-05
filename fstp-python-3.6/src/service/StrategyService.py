#encoding=utf-8
import sys
import pandas as pd

from src.core.PService.PyService import PyService
from src.core.log.PyPLogger import PyPLogger
from src.strategy.StrategyContainer import StrategyContainer
from src.core.datacontainer.MdContainer import MdContainer


pd.set_option('display.max_columns',500)
pd.set_option('display.width',1000)


class StrategyService(PyService):
    '''
    classdocs
    '''
    def __init__(self, serviceName, env, instance):
        super(StrategyService, self).__init__(serviceName, env, instance)
        self.log = PyPLogger(StrategyService)
        self.strategy = None
        self.datacontainer = None
                
        
    def initService(self):
        super(StrategyService, self).initService()  
        self.configs = super(StrategyService, self).getConfigBean('main', 'Strategy')        
        self.__init_MdContainer(self.configs) 
        self.__init_StrategyContainer(self.configs) 
          
    def startService(self):
        super(StrategyService, self).startService()
        self.strategy.start()

    def __init_StrategyContainer(self, configs):
        self.strategy = StrategyContainer(self.log, self.datacontainer, **dict(configs))
        
    def __init_MdContainer(self, configs):
        self.datacontainer = MdContainer(self.log, **dict(configs))

service = StrategyService('StrategyService',"DEV","1")
service.initService()
service.startService()
# print(service.datacontainer.getBarData('fstp_stock_bar_中成药', '000423'))



