#encoding=utf-8
from threading import *

import copy
import pymongo as mgdb
import pandas as pd

from src.service.engine.Event import Event
from src.service.engine.EventType import *

DB_prefix = 'fstp_stock_bar_'


class MdContainer(object):
    DEFAULT_CONFIG = {
        'bartype' : 'dayBar',
        'dbtype' : 'mongodb',
        'timebegin' : '20180101',
        'timeend' : 'lastday',
        'industry' : ['all'],
        'stockcode': [],
        'dbhost' : 'localhost',
        'dbport' : 27017
    }

    #----------------------------------------------------------------------    
    def __init__(self, log, eventEnginer, **configs ):
        self.log = log
        self.dbclient = None
        self.__eventEnginer = eventEnginer       
        
        extra_configs = set(configs).difference(self.DEFAULT_CONFIG)
        
        if extra_configs:
            self.log.error("Unrecognized configs: %s" % (extra_configs,))
        
        self.config = copy.copy(self.DEFAULT_CONFIG)
        self.config.update(configs)
        
        if self.config['dbtype'] == 'mongodb':
            self.dbclient = mgdb.MongoClient(self.config['dbhost'],
                                        int(self.config['dbport']))
            self.log.info('init mongodb client sucssful: dnhost={},dbport={}',self.config['dbhost'],self.config['dbport'])
        else :
            pass
        

    #----------------------------------------------------------------------    
    def getSingaleBarData(self, name, symbol):
        collection = self.dbclient[name][symbol]
        data = collection.find({},{'_id':0})    
        return pd.DataFrame(list(data))

    #----------------------------------------------------------------------    
    def runHistoryBar(self):
        if self.stock_pool is not None:
            for k,v in self.stock_pool.items(): 
                for s in v:
                    timer = Timer(0, self.__pubBar(s, self.getSingaleBarData(DB_prefix + k, s))) 
                    timer.start()   
    
    #----------------------------------------------------------------------    
    def receiveCommand(self, event):
        key = event.key
        self.__pubBar(key, self.getSingaleBarData(DB_prefix + key['industry'], key['stockcode']))
        
    #----------------------------------------------------------------------        
    def __pubBar(self, key, pdData):
        event = Event(type_=EVENT_MD_BAR, key=key, data=pdData)
        self.__eventEnginer.SendEvent(event)
    

     
                  

 
                        
        
         
        