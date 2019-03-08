#encoding=utf-8
import copy

from src.service.engine.Event import Event
from src.service.engine.EventType import *

class StrategyContainer(object):
    DATATYPE_RELATION={
        'dayBar':'fstp_stock_bar_'
    }
    
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
        self.eventEnginer = eventEnginer
        extra_configs = set(configs).difference(self.DEFAULT_CONFIG)
        
        if extra_configs:
            self.log.error("Unrecognized configs: %s" % (extra_configs,))
        
        self.config = copy.copy(self.DEFAULT_CONFIG)
        self.config.update(configs)
               
        log.info('load strategy configs:')
        self.log.info('--------------------------')
        for (k,v) in self.config.items():           
            self.log.info('    {} : {}',k,v)
        self.log.info('--------------------------')

    #----------------------------------------------------------------------    
    def start(self):
        pass
        
#         print(stock_pool)


#监听器 订阅者
class Listener:
    def __init__(self, log, username):
        self.__username = username
        self.log = log

    #----------------------------------------------------------------------    
    def onMessage(self, event):
        self.log.info('{}',event.data)          
            
        
            