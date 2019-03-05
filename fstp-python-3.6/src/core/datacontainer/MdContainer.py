#encoding=utf-8
import copy
import pymongo as mgdb
import pandas as pd

from src.core.log.PyPLogger import PyPLogger

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

    def __init__(self, log, **configs ):
        self.log = log
        self.dbclient = None
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
    
    def getSingaleBarData(self, name, symbol):
        collection = self.dbclient[name][symbol]
        data = collection.find({},{'_id':0})       
        return pd.DataFrame(list(data))
    
    
          
        