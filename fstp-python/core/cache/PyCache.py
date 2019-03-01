import redis

class PyCache(object):

    def __init__(self, ip='localhost', port=11211, db=0, log = None):
        self.redis = redis.StrictRedis(host=ip, port=port, db=db)
        self.log = log
           
    def put(self, cacheName = None, key = None ,bo = None):
        ret = False
        if cacheName is None or bo is None:
            self.log.info("cacheName or bo can't None")
            return ret
        
        if key is None:           
            ret = self.redis.hset(cacheName, bo.getBO().getUuid(), bo.getProBO().SerializeToString())
        else:
            ret = self.redis.hset(cacheName, key, bo.getProBO().SerializeToString())
        
        return ret
        
    def get(self, cacheName = None, key = None):
        ret = None
        if cacheName is None :
            self.log.info("cacheName  can't None")
            return ret
        
        if key is None:
            ret = self.redis.hgetall(cacheName)
        else:
            ret = self.redis.hget(cacheName, key)
        return ret
    
    
    def getAll(self, cacheName = None):
        if cacheName is None :
            self.log.info("cacheName  can't None")
            return None
        return self.get(cacheName = cacheName)
    
    def delCache(self, cacheName = None):
        ret = False
        if cacheName is None :
#             self.log.info("cacheName  can't None")
            ret = True
        else:
            ret = self.redis.delete(cacheName)
        return ret