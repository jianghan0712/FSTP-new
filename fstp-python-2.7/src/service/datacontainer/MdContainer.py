#encoding=utf-8
from threading import *

import copy
import pymongo as mgdb
import pandas as pd

from src.core.log.PyPLogger import PyPLogger
from src.service.engine.Event import Event
from src.service.engine.EventType import *

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
        self.stock_pool = []
        
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
        
        
        if self.config['industry'] == 'all':
            pass
        elif self.config['industry'] == '' and self.config['stockcode'] != '':
            self.stock_pool = self.config['stockcode'].split(',')

    #----------------------------------------------------------------------    
    def getSingaleBarData(self, name, symbol):
        collection = self.dbclient[name][symbol]
        data = collection.find({},{'_id':0})    
        return pd.DataFrame(list(data))

    #----------------------------------------------------------------------    
    def runHistoryBar(self):

        timer = Timer(0, self.__pubBar(self.getSingaleBarData(u'fstp_stock_bar_中成药', '000423'))) 
        timer.start()                   
        
    
    def __pubBar(self, pdData):
        for index,row in pdData.iterrows():
            event = Event(type_=EVENT_MD_BAR, data=row) 
            self.__eventEnginer.SendEvent(event) 


          
        