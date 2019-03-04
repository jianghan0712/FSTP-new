#coding=utf-8
import copy

from src.core.log.PyPLogger import PyPLogger
import pymongo as mgdb

class StrategyContainer(object):

    DEFAULT_CONFIG = {
        'name' : 'anonymous',
        'author' : 'FSTP',
        'bartype' : 'dayBar',
        'timebegin' : '20180101',
        'timeend' : 'lastday',
        'industry' : ['all'],
        'stockcode': [],
        'dbtype' : 'mongodb',
        'dbhost' : 'local',
        'dbport' : ''
    }
    
    
    def __init__(self, log , **configs ):
        self.log = log
        extra_configs = set(configs).difference(self.DEFAULT_CONFIG)
        
        if extra_configs:
            self.log.error("Unrecognized configs: %s" % (extra_configs,))
        
        self.config = copy.copy(self.DEFAULT_CONFIG)
        self.config.update(configs)
        
        
        log.info('load strategy configs:')
        for (k,v) in self.config.items():
            self.log.info('    {} : {}',k,v)


            