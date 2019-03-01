import uuid

class ServerStatsBO(object):

    def __init__(self):
        self.servername = ''
        self.status = 0
        self.uuid = str(uuid.uuid1())
        self.boid = 1
        self.destination = "fstp.core.manager.serverstatus"
