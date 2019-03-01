import uuid

class TestBO2(object):

    def __init__(self):
        self.servername = ''
        self.msg = ''
        self.uuid = str(uuid.uuid1())
        self.boid = 3
        self.destination = "fstp.core.rpc.testtwo"
