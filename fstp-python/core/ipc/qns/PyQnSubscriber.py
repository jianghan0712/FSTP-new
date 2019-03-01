
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
        