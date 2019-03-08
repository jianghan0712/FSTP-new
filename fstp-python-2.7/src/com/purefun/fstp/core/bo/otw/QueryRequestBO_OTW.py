from src.core.common.ICommon_OTW import ICommon_OTW
from src.com.purefun.fstp.core.bo.pro import QueryRequestBO_pb2
from src.com.purefun.fstp.core.bo.model.QueryRequestBO import QueryRequestBO

class QueryRequestBO_OTW(ICommon_OTW):

    def __init__(self, byteMsg = None):
        self._bo_pro = QueryRequestBO_pb2.QueryRequestBO()
        self._bo = QueryRequestBO()

        if byteMsg is not None:
            self._bo_pro.ParseFromString(byteMsg)
            self.__setDataFromPB()
        else:
            self.__setDataFromBO()

    def __setDataFromBO(self):
        self._bo_pro.requestServiceName = self._bo.requestServiceName
        self._bo_pro.respondServiceName = self._bo.respondServiceName
        self._bo_pro.querytopic = self._bo.querytopic
        self._bo_pro.tempTopic = self._bo.tempTopic
        self._bo_pro.uuid = self._bo.uuid
        self._bo_pro.boid = self._bo.boid
        self._bo_pro.destination = self._bo.destination

    def __setDataFromPB(self):
        self._bo.requestServiceName = self._bo_pro.requestServiceName
        self._bo.respondServiceName = self._bo_pro.respondServiceName
        self._bo.querytopic = self._bo_pro.querytopic
        self._bo.tempTopic = self._bo_pro.tempTopic
        self._bo.uuid = self._bo_pro.uuid
        self._bo.boid = self._bo_pro.boid
        self._bo.destination = self._bo_pro.destination

    def getBO(self):
        return self._bo

    def getProBO(self):
        return self._bo_pro

    def getRequestServiceName(self):
        return self._bo.requestServiceName

    def setRequestServiceName(self, requestServiceName):
        self._bo.requestServiceName = requestServiceName
        self._bo_pro.requestServiceName = requestServiceName

    def getRespondServiceName(self):
        return self._bo.respondServiceName

    def setRespondServiceName(self, respondServiceName):
        self._bo.respondServiceName = respondServiceName
        self._bo_pro.respondServiceName = respondServiceName

    def getQuerytopic(self):
        return self._bo.querytopic

    def setQuerytopic(self, querytopic):
        self._bo.querytopic = querytopic
        self._bo_pro.querytopic = querytopic

    def getTempTopic(self):
        return self._bo.tempTopic

    def setTempTopic(self, tempTopic):
        self._bo.tempTopic = tempTopic
        self._bo_pro.tempTopic = tempTopic

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
        return "QueryRequestBO_OTW ["+"requestServiceName = " + self.getRequestServiceName() +"," +"respondServiceName = " + self.getRespondServiceName() +"," +"querytopic = " + self.getQuerytopic() +"," +"tempTopic = " + self.getTempTopic() +"," +"uuid = " + self.getUuid() +"," +"boid = " + str(self.getBoid()) +"," +"destination = " + self.getDestination() +"," +"]"
