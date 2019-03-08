from src.core.common.ICommon_OTW import ICommon_OTW
from src.com.purefun.fstp.example.bo.pro import ExampleBO_pb2
from src.com.purefun.fstp.example.bo.model.ExampleBO import ExampleBO

class ExampleBO_OTW(ICommon_OTW):

    def __init__(self, byteMsg = None):
        self._bo_pro = ExampleBO_pb2.ExampleBO()
        self._bo = ExampleBO()

        if byteMsg is not None:
            self._bo_pro.ParseFromString(byteMsg)
            self.__setDataFromPB()
        else:
            self.__setDataFromBO()

    def __setDataFromBO(self):
        self._bo_pro.name = self._bo.name
        self._bo_pro.age = self._bo.age
        self._bo_pro.uuid = self._bo.uuid
        self._bo_pro.boid = self._bo.boid
        self._bo_pro.destination = self._bo.destination

    def __setDataFromPB(self):
        self._bo.name = self._bo_pro.name
        self._bo.age = self._bo_pro.age
        self._bo.uuid = self._bo_pro.uuid
        self._bo.boid = self._bo_pro.boid
        self._bo.destination = self._bo_pro.destination

    def getBO(self):
        return self._bo

    def getProBO(self):
        return self._bo_pro

    def getName(self):
        return self._bo.name

    def setName(self, name):
        self._bo.name = name
        self._bo_pro.name = name

    def getAge(self):
        return self._bo.age

    def setAge(self, age):
        self._bo.age = age
        self._bo_pro.age = age

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
        return "ExampleBO_OTW ["+"name = " + self.getName() +"," +"age = " + str(self.getAge()) +"," +"uuid = " + self.getUuid() +"," +"boid = " + str(self.getBoid()) +"," +"destination = " + self.getDestination() +"," +"]"
