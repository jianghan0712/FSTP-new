from core.MainService.PyPService import PyPService
from core.log import PyPLogger
from core.ipc.sub.PySubscriber import PySubscriber,SubListener
from com.purefun.fstp.core.bo.otw.TestBO_OTW import TestBO_OTW
from core.ipc.pub.PyPublisher import PyPublisher
import redis
from core.ipc.query.PyQuery import PyQuery,QueryListener
from com.purefun.fstp.example.bo.otw.ExampleBO_OTW import ExampleBO_OTW
from core.ipc.sub.PySubscriber import PySubscriber,SubListener
from core.ipc.query.PyQueryService import PyQueryService,QueryServiceListener


class PyTestService(PyPService):
    '''
    classdocs
    '''
    def __init__(self, serviceName, env, instance):
        super(PyTestService, self).__init__(serviceName, env, instance)
        self.log = PyPLogger.PyPLogger(PyTestService) 
        
    def initService(self):
        super(PyTestService, self).initService()
        
#         self.querylistener = QueryServiceListener(self.session, self.cache, self.bomap, self.log)
#         self.queryService = PyQueryService(self.bomap, self.session, self.cache, self.log, self.querylistener)
#         self.queryService.createQueryService()
#         listner = mySubListener(self.log)
#         sub = PySubscriber(self.session, listner, self.log)
#         sub.subscribe("amq.topic/fstp.core.rpc.testone")
        
        
    
    def startService(self):
        super(PyTestService, self).startService()

#         1.sub
#         listner = mySubListener(self.log)
#         sub = PySubscriber(self.session, listner, self.log)
#         sub.subscribe("amq.topic/fstp.core.rpc.testone")
   
#         2.cache
#         t = self.cache.getAll(cacheName = "TestBO")
#         for v in t.values():
#             k = TestBO_OTW(v)
#             self.log.info( k.getUuid())
          
#         3.pub 
#         bo = TestBO_OTW() 
        
#         self.pub.publish(bo,durFlag=True)

#         4.query
        listner = myQueryListener(self.log)
        query = PyQuery(self.session, listner, self.log)
        query.query("fstp.example.bo.test2", "fstp.respond")

          

class mySubListener(SubListener):
    def __init__(self, log):
        super(mySubListener, self).__init__(log)
        
    def doTask(self, bytebo):
        bo_otw = TestBO_OTW(bytebo) 
        self.log.info("Receive BO:",bo_otw.toString())



class myQueryListener(QueryListener):
    def __init__(self, log):
        super(myQueryListener, self).__init__(log)
        
    def doTask(self, bytebo):
        bo_otw = ExampleBO_OTW(bytebo) 
        self.log.info("Receive BO:",bo_otw.toString())
            

service = PyTestService("pyTestService","DEV","1")
service.initService()
service.startService()

raw_input() 