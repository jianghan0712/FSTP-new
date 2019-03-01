#coding=utf-8
from core.log import PyPLogger
from core.ipc.qpid import PyQpidConnect
import ConfigParser
from core.cache.PyCache import PyCache
from com.purefun.fstp.core.bo.otw.QueryRequestBO_OTW import QueryRequestBO_OTW
from core.ipc.sub.PySubscriber import PySubscriber,SubListener
from core.ipc.query.PyQueryService import PyQueryService,QueryServiceListener
from core.ipc.pub.PyPublisher import PyPublisher



class PyPService(object):
    ''' 主服务类   '''
   
    def __init__(self, serviceName, env, instance):
        self.serviceName = serviceName
        self.property = PyProperty(serviceName, env, instance)
        self.log = None        
        self.confDic = dict()  #用户自定义的配置字典
        
        self.session = None        
        self.pub = None
        self.cache = None
        self.bomap = None
        self.queryService = None
        
        self.__initConfig()  #初始化配置文件       
      
    def __initConfig(self):
        path = "../../config/" + self.serviceName + "/" + self.property.env + "/" + self.property.instance + "/"
        mainCof = ConfigParser.SafeConfigParser()
        mainCof.read(path + "config.conf")
        self.confDic['main'] = mainCof
        if mainCof.has_section('conf'):
            for eachConf in mainCof.options('conf'):                
                self.__loadConfigs(path, eachConf, mainCof.get('conf', eachConf))
            
    def __loadConfigs(self, confPath, moduName, fileName):
        subConf = ConfigParser.SafeConfigParser()
        if  fileName.find('/') == -1:
            subConf.read(confPath + fileName)           
        else :
            subConf.read(fileName)
        
        if subConf is None:
            self.log.error("load config ",fileName," failed")
        else:
            self.confDic[moduName] = subConf    
     
    def getConfigBean(self, module = 'main', section = None, option = None):  
        ret = None
        if self.confDic.get(module) is None:
            self.log.error("config module ", module, " is not exist!") 
            return None
 
        if  section is None:
            self.log.error("section connot NULL")
            return None
        
        if  option is None:
            return self.confDic.get(module).items(section)
        
        if self.confDic.get(module).has_section(section) :
            if self.confDic.get(module).has_option(section, option):
                ret = self.confDic.get(module).get(section, option)
            else:
                self.log.error("get config module=", module, " ,section=", section, " ,option", option, " failed") 
        else:
            self.log.error("get config module=", module, " ,section=", section, " ,option=", option, " failed")
        
        return ret

        
    def initService(self):        
        self.qpid = PyQpidConnect.PyQpidConnect(self.log) #config/qpid.conf
        self.session = self.qpid.createSession()       #初始化Qpid
        self.cache = PyCache(ip='localhost', port=6379, log=self.log)
        self.bomap = self.getConfigBean(section="bomap")
        self.pub = PyPublisher(self.session,self.log,self.cache)
        self.log.info("initService successful")
#         self.querylistener = QueryServiceListener(self.bomap, self.session, self.cache, self.log)        
      
    def startService(self):
        self.log.info("startService successful")


class PyProperty(object):
    serviceName = None
    env = None
    instance = None
    
    def __init__(self, serviceName, env, instance):
        self.serviceName = serviceName
        self.env = env
        self.instance = instance  

