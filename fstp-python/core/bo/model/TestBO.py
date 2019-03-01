import uuid

class TestBO(object):
    
    def __init__(self):
        self.uuid = str(uuid.uuid1())
        self.boid = 2L
        self.destination = "fstp.core.rpc.testone"
        self.servername = ''
        self.msg = ''
        