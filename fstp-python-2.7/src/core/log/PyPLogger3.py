import yaml
import logging.config
import os


class PyPLogger(object):
    def __init__(self, clazz):
        self.clazz = clazz
        self.logging = None
        os.chdir('/config/')
        self.setup_logging()
         
    def setup_logging(self, default_path = "logconfig.yaml",default_level = logging.INFO, env_key = "LOG_CFG"):
        path = default_path
        value = os.getenv(env_key,None)
        if value:
            path = value
        if os.path.exists(path):
            with open(path,"r") as f:
                config = yaml.load(f)
                logging.config.dictConfig(config)
        else:
            print(path)
            logging.basicConfig(level = default_level)
        
#         self.logging = logging.getLogger(name)
    
    def info(self, msg, *args, **kwargs):
        logging.info(msg, *args, **kwargs)

if __name__ == "__main__":
    a = PyPLogger(PyPLogger)
    a.setup_logging(default_path = "logconfig.yaml")
    a.func()
