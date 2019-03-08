#coding=utf-8

import configparser

class PyService(object):

    def __init__(self, serviceName, env, instance):
        self.serviceName = serviceName
        self.property = PyProperty(serviceName, env, instance)
        self.log = None        
        self.confDic = dict()  
        
    
    def __initConfig(self):
        """
        :note: 从config文件中加载主数据字典
        """
        path = "../../config/" + self.serviceName + "/" + self.property.env + "/" + self.property.instance + "/"
        mainCof = configparser.SafeConfigParser()
        mainCof.read(path + "config.conf", encoding='utf-8')
        self.log.info('load config {} .', path + "config.conf")
        
        self.confDic['main'] = mainCof
        if mainCof.has_section('conf'):
            for eachConf in mainCof.options('conf'):                
                self.__loadConfigs(path, eachConf, mainCof.get('conf', eachConf))
    
    
    def __loadConfigs(self, confPath, moduName, fileName):
        """
        :note: 从config的conf中加载导入的子文件
        """
        subConf = configparser.SafeConfigParser()
        if  fileName.find('/') == -1:
            subConf.read(confPath + fileName)           
        else :
            subConf.read(fileName)
        
        if subConf is None:
            self.log.error("load config {}",fileName," failed")
        else:
            self.confDic[moduName] = subConf
            self.log.info('load config {} .',moduName)   
             
                
    def getConfigBean(self, module = 'main', section = None, option = None):  
        """
        :note: 取出config文件中的配置
        :param module: 默认为从主配置文件；
        :param section: 不同config的section
        :param option: 字段名 key
        :return: 目标数据
        """
        ret = None
        if self.confDic.get(module) is None:
            self.log.error("config module {}", module, " is not exist!") 
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
                self.log.error("get config module={} ,section={}, option={}  failed", module, section, option) 
        else:
            self.log.error("get config module={} ,section={}, option={}  failed", module, section, option) 
        
        return ret
    
    def initService(self):
        self.__initConfig()
        self.log.info('init Service {} successful', self.serviceName)
    
    def startService(self):
        self.log.info('start Service {} successful', self.serviceName)


class PyProperty(object):
    serviceName = None
    env = None
    instance = None
    
    def __init__(self, serviceName, env, instance):
        self.serviceName = serviceName
        self.env = env
        self.instance = instance          