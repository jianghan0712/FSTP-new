import sys
from qpid.messaging import *
import ConfigParser 
import uuid

from src.core.log import PyPLogger

class PyQpidConnect(object):
    '''
    classdocs
    '''
    def __init__(self,log):
        self._name = None
        self._pass = None
        self._ip = None
        self._port = None
        
        self.session = None
        self.connect = None
        self.log = log
        self._loadConf()
    
    def _loadConf(self):
        conf = ConfigParser.SafeConfigParser()
        conf.read('../../config/qpid.conf')
        self._name = conf.get('Qpid', 'user')
        self._pass = conf.get('Qpid', 'pass')
        self._ip = conf.get('Qpid', 'ip')
        self._port = conf.get('Qpid', 'port')
        
        url = self._ip + ':' + self._port
        self.connect = Connection(url)
        
    def createSession(self):
        try:
            self.connect.open()
            self.session = self.connect.session()
            self.log.info('create qpid session succesful')
            return self.session
        except MessagingError,m:
            print m

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


class PyQnSubscriber(object):
    '''
    classdocs
    '''
    def __init__(self, session, qnsListener, log, cache):
        '''
        Constructor
        '''
        self.session = session
        self.listener = qnsListener
        self.log = log
    
    def qns(self, topic):
        receiver = self.session.receiver(topic)
        self.listener.init(receiver)
        self.listener.onEvent()              
        self.session.acknowledge()
        

class QnsListener(object):
    '''
    classdocs
    '''
    def __init__(self, log):
        self.receiver = None
        self.log = log
        
    def onEvent(self):
        while self.receiver is not None:
            message = self.receiver.fetch()     
            self.doTask(message.content)
    
    def onQuery(self):
        pass
            
    def init(self, Receiver):
        self.receiver = Receiver
        
    def doTask(self, byteBo):  
        pass      

class PySubscriber(object):
    '''
    classdocs
    '''
    def __init__(self, session, subListener, log):
        '''
        Constructor
        '''
        self.session = session
        self.listener = subListener
        self.log = log
    
    def subscribe(self, topic):
        receiver = self.session.receiver(topic)
        self.listener.init(receiver)
        self.listener.onEvent()              
        self.session.acknowledge()

class SubListener(object):
    '''
    classdocs
    '''
    def __init__(self, log):
        self.receiver = None
        self.log = log
        
    def onEvent(self):
        while self.receiver is not None:
            message = self.receiver.fetch()     
            self.doTask(message.content)
                
    def init(self, Receiver):
        self.receiver = Receiver
        
    def doTask(self, byteBo):  
        pass      