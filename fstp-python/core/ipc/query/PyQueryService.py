from com.purefun.fstp.core.bo.otw.QueryRequestBO_OTW import QueryRequestBO_OTW

class PyQueryService(object):
    def __init__(self, bomap, session, cache, log, querylistener):
        self.bomap = bomap
        self.session = session
        self.cache = cache
        self.log = log
        self.listener = querylistener
        
    def createQueryService(self):
        receiver = self.session.receiver("amq.topic/QueryTopic")
        self.listener.init(receiver)
        self.listener.onEvent()
        self.session.acknowledge()    


class QueryServiceListener(object):
    '''
    classdocs
    '''
    def __init__(self, session, cache, bomap, log):
        self.receiver = None
        self.log = log
        self.bomap = bomap
        self.session = session
        self.cache = cache
        
        
    def init(self, Receiver):
        self.receiver = Receiver
        
            
    def onEvent(self):
        while self.receiver is not None:
            message = self.receiver.fetch() 
            bo = QueryRequestBO_OTW(message.content)
            for e in self.bomap.keys():
                if e == bo.getQuerytopic():
                    self.doTask(self.bomap[e])
                else :
                    pass                      
        
    def doTask(self, boname):  
        self.log.info("get ",boname,"from cache")      
        