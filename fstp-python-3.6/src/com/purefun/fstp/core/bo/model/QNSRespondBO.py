import uuid

class QNSRespondBO(object):

    def __init__(self):
        self.servername = ''
        self.respond = ''
        self.uuid = str(uuid.uuid1())
        self.boid = 4
        self.destination = "fstp.core.manager.qnsrespond"
