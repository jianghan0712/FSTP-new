import log4py
import sys

class PyPLogger:

    def __init__(self, clazz):
        self.log4py = log4py.Logger().get_instance(self)
        self.clazz = clazz
        self.__setting()
     
    def info(self, *msg):
        self.log4py.info(*msg)
    
    def warm(self, *msg):
        self.log4py.warm(*msg)
        
    def error(self, *msg):
        self.log4py.error(*msg)
        
    def debug(self, *msg):
        self.log4py.debug(*msg)
        
    def __setting(self):
        self.log4py.set_target(sys.stdout)

        name =  self.clazz.__name__[self.clazz.__name__.rfind('.')+1:]
        self.log4py.add_target("../../logging/" + name +".log")
        self.log4py.set_time_format("%Y-%m-%d %H:%M:%S")
        self.log4py.set_formatstring("[%L] [%T][" + name + "] - %M")
        self.log4py.set_rotation(1)
        