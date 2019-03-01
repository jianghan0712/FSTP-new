
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