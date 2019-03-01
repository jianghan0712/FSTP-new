import uuid

class TestBO(object):

    def __init__(self):
        self.servername = ''
        self.msg = ''
        self.uuid = str(uuid.uuid1())
        self.boid = 2
        self.destination = "fstp.core.rpc.testone"
