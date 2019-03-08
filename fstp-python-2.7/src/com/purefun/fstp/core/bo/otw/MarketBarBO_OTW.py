from src.core.common.ICommon_OTW import ICommon_OTW
from src.com.purefun.fstp.core.bo.pro import MarketBarBO_pb2
from src.com.purefun.fstp.core.bo.model.MarketBarBO import MarketBarBO

class MarketBarBO_OTW(ICommon_OTW):

    def __init__(self, byteMsg = None):
        self._bo_pro = MarketBarBO_pb2.MarketBarBO()
        self._bo = MarketBarBO()

        if byteMsg is not None:
            self._bo_pro.ParseFromString(byteMsg)
            self.__setDataFromPB()
        else:
            self.__setDataFromBO()

    def __setDataFromBO(self):
        self._bo_pro.product_id = self._bo.product_id
        self._bo_pro.exch = self._bo.exch
        self._bo_pro.date = self._bo.date
        self._bo_pro.high = self._bo.high
        self._bo_pro.low = self._bo.low
        self._bo_pro.open = self._bo.open
        self._bo_pro.close = self._bo.close
        self._bo_pro.change = self._bo.change
        self._bo_pro.volume = self._bo.volume
        self._bo_pro.uuid = self._bo.uuid
        self._bo_pro.boid = self._bo.boid
        self._bo_pro.destination = self._bo.destination

    def __setDataFromPB(self):
        self._bo.product_id = self._bo_pro.product_id
        self._bo.exch = self._bo_pro.exch
        self._bo.date = self._bo_pro.date
        self._bo.high = self._bo_pro.high
        self._bo.low = self._bo_pro.low
        self._bo.open = self._bo_pro.open
        self._bo.close = self._bo_pro.close
        self._bo.change = self._bo_pro.change
        self._bo.volume = self._bo_pro.volume
        self._bo.uuid = self._bo_pro.uuid
        self._bo.boid = self._bo_pro.boid
        self._bo.destination = self._bo_pro.destination

    def getBO(self):
        return self._bo

    def getProBO(self):
        return self._bo_pro

    def getProduct_id(self):
        return self._bo.product_id

    def setProduct_id(self, product_id):
        self._bo.product_id = product_id
        self._bo_pro.product_id = product_id

    def getExch(self):
        return self._bo.exch

    def setExch(self, exch):
        self._bo.exch = exch
        self._bo_pro.exch = exch

    def getDate(self):
        return self._bo.date

    def setDate(self, date):
        self._bo.date = date
        self._bo_pro.date = date

    def getHigh(self):
        return self._bo.high

    def setHigh(self, high):
        self._bo.high = high
        self._bo_pro.high = high

    def getLow(self):
        return self._bo.low

    def setLow(self, low):
        self._bo.low = low
        self._bo_pro.low = low

    def getOpen(self):
        return self._bo.open

    def setOpen(self, open):
        self._bo.open = open
        self._bo_pro.open = open

    def getClose(self):
        return self._bo.close

    def setClose(self, close):
        self._bo.close = close
        self._bo_pro.close = close

    def getChange(self):
        return self._bo.change

    def setChange(self, change):
        self._bo.change = change
        self._bo_pro.change = change

    def getVolume(self):
        return self._bo.volume

    def setVolume(self, volume):
        self._bo.volume = volume
        self._bo_pro.volume = volume

    def getUuid(self):
        return self._bo.uuid

    def setUuid(self, uuid):
        self._bo.uuid = uuid
        self._bo_pro.uuid = uuid

    def getBoid(self):
        return self._bo.boid

    def setBoid(self, boid):
        self._bo.boid = boid
        self._bo_pro.boid = boid

    def getDestination(self):
        return self._bo.destination

    def setDestination(self, destination):
        self._bo.destination = destination
        self._bo_pro.destination = destination

    def toString(self):
        return "MarketBarBO_OTW ["+"product_id = " + self.getProduct_id() +"," +"exch = " + self.getExch() +"," +"date = " + self.getDate() +"," +"high = " + str(self.getHigh()) +"," +"low = " + str(self.getLow()) +"," +"open = " + str(self.getOpen()) +"," +"close = " + str(self.getClose()) +"," +"change = " + str(self.getChange()) +"," +"volume = " + str(self.getVolume()) +"," +"uuid = " + self.getUuid() +"," +"boid = " + str(self.getBoid()) +"," +"destination = " + self.getDestination() +"," +"]"
