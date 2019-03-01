from qpid.messaging import *
from com.purefun.fstp.core.bo.otw.QueryRequestBO_OTW import QueryRequestBO_OTW

class PyQuery(object):
    '''
    classdocs
    '''

    def __init__(self, session, queryListener, log):
        '''
        Constructor
        '''
        self.session = session
        self.listener = queryListener
        self.log = log
    
    def query(self, queryTopic=None, respondTopic=None):
        if queryTopic is None or respondTopic is None:
            self.log.error("queryTopic or respondTopic connot NULL")
            return 
        bo = QueryRequestBO_OTW()
        bo.setQuerytopic(queryTopic)
        bo.setTempTopic(respondTopic)
        
        sender = self.session.sender("amq.topic/QueryTopic")
        sender.send(Message(content=bo.getProBO().SerializeToString()))
               
        receiver = self.session.receiver("amq.topic/" + respondTopic)
        self.listener.init(receiver)
        self.listener.onEvent()              
        self.session.acknowledge()

class QueryListener(object):
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