#encoding=utf-8
import copy

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
      
    def __init__(self, log, datacontainer, **configs ):
        self.log = log
        self.data = datacontainer
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
    
    def start(self):
        stock_pool = []
        if self.config['industry'] == 'all':
            pass
        elif self.config['industry'] == '' and self.config['stockcode'] != '':
            stock_pool = self.config['stockcode'].split(',')
        
        print(stock_pool)
            
            
        
            