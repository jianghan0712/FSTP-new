import uuid

class QueryRequestBO(object):

    def __init__(self):
        self.requestServiceName = ''
        self.respondServiceName = ''
        self.querytopic = ''
        self.tempTopic = ''
        self.uuid = str(uuid.uuid1())
        self.boid = 5
        self.destination = "fstp.core.manager.queryrequest"
