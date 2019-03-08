import uuid

class MarketBarBO(object):

    def __init__(self):
        self.product_id = ''
        self.exch = ''
        self.date = ''
        self.high = 0.0
        self.low = 0.0
        self.open = 0.0
        self.close = 0.0
        self.change = 0.0
        self.volume = 0.0
        self.uuid = str(uuid.uuid1())
        self.boid = 10
        self.destination = "fstp.md.bar"
