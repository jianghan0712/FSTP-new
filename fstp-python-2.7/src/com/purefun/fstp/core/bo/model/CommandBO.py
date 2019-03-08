import uuid

class CommandBO(object):

    def __init__(self):
        self.targetService = ''
        self.parm0 = ''
        self.parm1 = ''
        self.parm2 = ''
        self.parm3 = ''
        self.parm4 = ''
        self.uuid = str(uuid.uuid1())
        self.boid = 999
        self.destination = "fstp.core.manager.command"
