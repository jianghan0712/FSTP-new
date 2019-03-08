import uuid

class ExampleBO(object):

    def __init__(self):
        self.name = ''
        self.age = 0
        self.uuid = str(uuid.uuid1())
        self.boid = 6013
        self.destination = "fstp.example.bo.test2"
