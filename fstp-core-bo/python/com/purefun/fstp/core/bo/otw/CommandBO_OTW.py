from src.core.common.ICommon_OTW import ICommon_OTW
from src.com.purefun.fstp.core.bo.pro import CommandBO_pb2
from src.com.purefun.fstp.core.bo.model.CommandBO import CommandBO

class CommandBO_OTW(ICommon_OTW):

    def __init__(self, byteMsg = None):
        self._bo_pro = CommandBO_pb2.CommandBO()
        self._bo = CommandBO()

        if byteMsg is not None:
            self._bo_pro.ParseFromString(byteMsg)
            self.__setDataFromPB()
        else:
            self.__setDataFromBO()

    def __setDataFromBO(self):
        self._bo_pro.targetService = self._bo.targetService
        self._bo_pro.parm0 = self._bo.parm0
        self._bo_pro.parm1 = self._bo.parm1
        self._bo_pro.parm2 = self._bo.parm2
        self._bo_pro.parm3 = self._bo.parm3
        self._bo_pro.parm4 = self._bo.parm4
        self._bo_pro.uuid = self._bo.uuid
        self._bo_pro.boid = self._bo.boid
        self._bo_pro.destination = self._bo.destination

    def __setDataFromPB(self):
        self._bo.targetService = self._bo_pro.targetService
        self._bo.parm0 = self._bo_pro.parm0
        self._bo.parm1 = self._bo_pro.parm1
        self._bo.parm2 = self._bo_pro.parm2
        self._bo.parm3 = self._bo_pro.parm3
        self._bo.parm4 = self._bo_pro.parm4
        self._bo.uuid = self._bo_pro.uuid
        self._bo.boid = self._bo_pro.boid
        self._bo.destination = self._bo_pro.destination

    def getBO(self):
        return self._bo

    def getProBO(self):
        return self._bo_pro

    def getTargetService(self):
        return self._bo.targetService

    def setTargetService(self, targetService):
        self._bo.targetService = targetService
        self._bo_pro.targetService = targetService

    def getParm0(self):
        return self._bo.parm0

    def setParm0(self, parm0):
        self._bo.parm0 = parm0
        self._bo_pro.parm0 = parm0

    def getParm1(self):
        return self._bo.parm1

    def setParm1(self, parm1):
        self._bo.parm1 = parm1
        self._bo_pro.parm1 = parm1

    def getParm2(self):
        return self._bo.parm2

    def setParm2(self, parm2):
        self._bo.parm2 = parm2
        self._bo_pro.parm2 = parm2

    def getParm3(self):
        return self._bo.parm3

    def setParm3(self, parm3):
        self._bo.parm3 = parm3
        self._bo_pro.parm3 = parm3

    def getParm4(self):
        return self._bo.parm4

    def setParm4(self, parm4):
        self._bo.parm4 = parm4
        self._bo_pro.parm4 = parm4

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
        return "CommandBO_OTW ["+"targetService = " + self.getTargetService() +"," +"parm0 = " + self.getParm0() +"," +"parm1 = " + self.getParm1() +"," +"parm2 = " + self.getParm2() +"," +"parm3 = " + self.getParm3() +"," +"parm4 = " + self.getParm4() +"," +"uuid = " + self.getUuid() +"," +"boid = " + str(self.getBoid()) +"," +"destination = " + self.getDestination() +"," +"]"
