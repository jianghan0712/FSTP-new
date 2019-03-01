import uuid

class ExampleQnsBO(object):

    def __init__(self):
        self.company = ''
        self.name = ''
        self.age = 0
        self.uuid = str(uuid.uuid1())
        self.boid = 6015
        self.destination = "fstp.example.bo.test"
