from src.core.common.ICommon_OTW import ICommon_OTW
from src.com.purefun.fstp.core.bo.pro import ServerStatsBO_pb2
from src.com.purefun.fstp.core.bo.model.ServerStatsBO import ServerStatsBO

class ServerStatsBO_OTW(ICommon_OTW):

    def __init__(self, byteMsg = None):
        self._bo_pro = ServerStatsBO_pb2.ServerStatsBO()
        self._bo = ServerStatsBO()

        if byteMsg is not None:
            self._bo_pro.ParseFromString(byteMsg)
            self.__setDataFromPB()
        else:
            self.__setDataFromBO()

    def __setDataFromBO(self):
        self._bo_pro.servername = self._bo.servername
        self._bo_pro.status = self._bo.status
        self._bo_pro.uuid = self._bo.uuid
        self._bo_pro.boid = self._bo.boid
        self._bo_pro.destination = self._bo.destination

    def __setDataFromPB(self):
        self._bo.servername = self._bo_pro.servername
        self._bo.status = self._bo_pro.status
        self._bo.uuid = self._bo_pro.uuid
        self._bo.boid = self._bo_pro.boid
        self._bo.destination = self._bo_pro.destination

    def getBO(self):
        return self._bo

    def getProBO(self):
        return self._bo_pro

    def getServername(self):
        return self._bo.servername

    def setServername(self, servername):
        self._bo.servername = servername
        self._bo_pro.servername = servername

    def getStatus(self):
        return self._bo.status

    def setStatus(self, status):
        self._bo.status = status
        self._bo_pro.status = status

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
        return "ServerStatsBO_OTW ["+"servername = " + self.getServername() +"," +"status = " + str(self.getStatus()) +"," +"uuid = " + self.getUuid() +"," +"boid = " + str(self.getBoid()) +"," +"destination = " + self.getDestination() +"," +"]"
