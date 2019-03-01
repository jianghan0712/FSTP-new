
import sys
from src.core.PService.PyService import PyService
from src.core.log.PyPLogger import PyPLogger
from src.strategy.StrategyContainer import StrategyContainer

class StrategyService(PyService):
    '''
    classdocs
    '''
    def __init__(self, serviceName, env, instance):
        super(StrategyService, self).__init__(serviceName, env, instance)
        self.log = PyPLogger(StrategyService)
        self.strategy = None
        
        
        
    def initService(self):
        super(StrategyService, self).initService()  
        self.configs = super(StrategyService, self).getConfigBean('main', 'Strategy')
        self.__init_StrategyContainer(self.configs)     
          
    def startService(self):
        super(StrategyService, self).startService()


    def __init_StrategyContainer(self, configs):
        self.strategy = StrategyContainer(self.log, **dict(configs))
        


service = StrategyService("StrategyService","DEV","1")
service.initService()
service.startService()



