#coding=utf-8
'''
Created on 2018年5月2日

@author: Jianghan
'''
from qpid.messaging import *

class PyPublisher(object):
    '''
    classdocs
    '''
    def __init__(self, session, log, cache):
        '''
        Constructor
        '''
        self.session = session
        self.log = log
        self.cache = cache
        
    
    def publish(self, bo, durFlag = False, topicFormat = "amq.topic/",key = None):
        if self.session is not None :
            sender = self.session.sender(topicFormat + str(bo.getDestination())) 
            
        data = bo.getProBO().SerializeToString()
        sender.send(Message(data))
        self.log.info("Publish BO:", bo.toString())
        if durFlag is True:
            self.durable(bo,key)
    
    def durable(self, bo = None, key = None):
        if self.cache is None:
            self.log.info("cache is not init")
        insertSuccess = False
            
        if not key is None :
            methodName = 'get' + key
            if getattr(bo,methodName):
                c = getattr(bo,methodName)
                if self.cache.put(bo.getBO().__class__.__name__, c(), bo):
                    insertSuccess = True                    
                else:
                    self.log.warning('get',key,'() method is not find,use Uuid as key')
        
        if not insertSuccess:
            self.cache.put(bo.getBO().__class__.__name__, bo.getUuid(), bo)
            

            
        
        