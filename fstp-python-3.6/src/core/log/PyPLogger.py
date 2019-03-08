# coding=utf-8

import os
import logbook
from logbook import Logger,TimedRotatingFileHandler
from logbook.more import ColorizedStderrHandler,StderrHandler

log_dir = os.path.join("../../logging/")
           
class PyPLogger(object):

    def __init__(self, clazz):
        logbook.set_datetime_format("local")
        self.serverName =  clazz.__name__[clazz.__name__.rfind('.')+1:]
        if not os.path.exists(log_dir):
            os.makedirs(log_dir)
        self.log_file = TimedRotatingFileHandler(
            os.path.join(log_dir, '%s.log' % self.serverName),date_format='%Y-%m-%d', bubble=True, encoding='utf-8')
        self.log_std = ColorizedStderrHandler(bubble=True)
#         self.log_std = StderrHandler(bubble=True)
        
        self.log = Logger(self.serverName)
        self.__init_logger()
        self.__setting()
        

    def log_type(self, record, handler):
#         log = "[{date}]-[{level}]-[" + self.serverName + "] - {msg}".format(
        log = "["+self.serverName + "]" +"-[{date}]-[{level}] - {msg}".format(
            date = record.time,                              
            level = record.level_name,                    
#             filename = os.path.split(record.filename)[-1],  
#             func_name = record.func_name,
#             lineno = record.lineno,
            msg = record.message                             
        )
        return log
    
    def __init_logger(self):
        logbook.set_datetime_format("local")
        self.log.handlers = []
        self.log.handlers.append(self.log_file)
        self.log.handlers.append(self.log_std)
    
    def __setting(self):       
        self.log_std.formatter = self.log_type
        self.log_file.formatter = self.log_type
    
    def info(self, *args, **kwargs):
        self.log.info(*args, **kwargs)
    
    def warn(self, *args, **kwargs):
        self.log.warn(*args, **kwargs)
    
    def error(self, *args, **kwargs):
        self.log.error(*args, **kwargs)

        