import uuid

class QNSRequestBO(object):

    def __init__(self):
        self.servername = ''
        self.request = ''
        self.uuid = str(uuid.uuid1())
        self.boid = 4
        self.destination = "fstp.core.manager.qnsrequest"
