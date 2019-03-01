from core.common.ICommon_OTW import ICommon_OTW
from com.purefun.fstp.tushare.bo.pro import StockBasicInfoBO_pb2
from com.purefun.fstp.tushare.bo.model.StockBasicInfoBO import StockBasicInfoBO

class StockBasicInfoBO_OTW(ICommon_OTW):

    def __init__(self, byteMsg = None):
        self._bo_pro = StockBasicInfoBO_pb2.StockBasicInfoBO()
        self._bo = StockBasicInfoBO()

        if byteMsg is not None:
            self._bo_pro.ParseFromString(byteMsg)
            self.__setDataFromPB()
        else:
            self.__setDataFromBO()

    def __setDataFromBO(self):
        self._bo_pro.stockcode = self._bo.stockcode
        self._bo_pro.stockname = self._bo.stockname
        self._bo_pro.industry = self._bo.industry
        self._bo_pro.area = self._bo.area
        self._bo_pro.pe = self._bo.pe
        self._bo_pro.offer_shares = self._bo.offer_shares
        self._bo_pro.total_shares = self._bo.total_shares
        self._bo_pro.totalAssets = self._bo.totalAssets
        self._bo_pro.liquidAssets = self._bo.liquidAssets
        self._bo_pro.fixedAssets = self._bo.fixedAssets
        self._bo_pro.reserved = self._bo.reserved
        self._bo_pro.reservedPerShare = self._bo.reservedPerShare
        self._bo_pro.esp = self._bo.esp
        self._bo_pro.bvps = self._bo.bvps
        self._bo_pro.pb = self._bo.pb
        self._bo_pro.list_date = self._bo.list_date
        self._bo_pro.no_dividend = self._bo.no_dividend
        self._bo_pro.perundp = self._bo.perundp
        self._bo_pro.rev_per = self._bo.rev_per
        self._bo_pro.profit = self._bo.profit
        self._bo_pro.gross_profit = self._bo.gross_profit
        self._bo_pro.net_profit = self._bo.net_profit
        self._bo_pro.holder = self._bo.holder
        self._bo_pro.uuid = self._bo.uuid
        self._bo_pro.boid = self._bo.boid
        self._bo_pro.destination = self._bo.destination

    def __setDataFromPB(self):
        self._bo.stockcode = self._bo_pro.stockcode
        self._bo.stockname = self._bo_pro.stockname
        self._bo.industry = self._bo_pro.industry
        self._bo.area = self._bo_pro.area
        self._bo.pe = self._bo_pro.pe
        self._bo.offer_shares = self._bo_pro.offer_shares
        self._bo.total_shares = self._bo_pro.total_shares
        self._bo.totalAssets = self._bo_pro.totalAssets
        self._bo.liquidAssets = self._bo_pro.liquidAssets
        self._bo.fixedAssets = self._bo_pro.fixedAssets
        self._bo.reserved = self._bo_pro.reserved
        self._bo.reservedPerShare = self._bo_pro.reservedPerShare
        self._bo.esp = self._bo_pro.esp
        self._bo.bvps = self._bo_pro.bvps
        self._bo.pb = self._bo_pro.pb
        self._bo.list_date = self._bo_pro.list_date
        self._bo.no_dividend = self._bo_pro.no_dividend
        self._bo.perundp = self._bo_pro.perundp
        self._bo.rev_per = self._bo_pro.rev_per
        self._bo.profit = self._bo_pro.profit
        self._bo.gross_profit = self._bo_pro.gross_profit
        self._bo.net_profit = self._bo_pro.net_profit
        self._bo.holder = self._bo_pro.holder
        self._bo.uuid = self._bo_pro.uuid
        self._bo.boid = self._bo_pro.boid
        self._bo.destination = self._bo_pro.destination

    def getBO(self):
        return self._bo

    def getProBO(self):
        return self._bo_pro

    def getStockcode(self):
        return self._bo.stockcode

    def setStockcode(self, stockcode):
        self._bo.stockcode = stockcode
        self._bo_pro.stockcode = stockcode

    def getStockname(self):
        return self._bo.stockname

    def setStockname(self, stockname):
        self._bo.stockname = stockname
        self._bo_pro.stockname = stockname

    def getIndustry(self):
        return self._bo.industry

    def setIndustry(self, industry):
        self._bo.industry = industry
        self._bo_pro.industry = industry

    def getArea(self):
        return self._bo.area

    def setArea(self, area):
        self._bo.area = area
        self._bo_pro.area = area

    def getPe(self):
        return self._bo.pe

    def setPe(self, pe):
        self._bo.pe = pe
        self._bo_pro.pe = pe

    def getOffer_shares(self):
        return self._bo.offer_shares

    def setOffer_shares(self, offer_shares):
        self._bo.offer_shares = offer_shares
        self._bo_pro.offer_shares = offer_shares

    def getTotal_shares(self):
        return self._bo.total_shares

    def setTotal_shares(self, total_shares):
        self._bo.total_shares = total_shares
        self._bo_pro.total_shares = total_shares

    def getTotalAssets(self):
        return self._bo.totalAssets

    def setTotalAssets(self, totalAssets):
        self._bo.totalAssets = totalAssets
        self._bo_pro.totalAssets = totalAssets

    def getLiquidAssets(self):
        return self._bo.liquidAssets

    def setLiquidAssets(self, liquidAssets):
        self._bo.liquidAssets = liquidAssets
        self._bo_pro.liquidAssets = liquidAssets

    def getFixedAssets(self):
        return self._bo.fixedAssets

    def setFixedAssets(self, fixedAssets):
        self._bo.fixedAssets = fixedAssets
        self._bo_pro.fixedAssets = fixedAssets

    def getReserved(self):
        return self._bo.reserved

    def setReserved(self, reserved):
        self._bo.reserved = reserved
        self._bo_pro.reserved = reserved

    def getReservedPerShare(self):
        return self._bo.reservedPerShare

    def setReservedPerShare(self, reservedPerShare):
        self._bo.reservedPerShare = reservedPerShare
        self._bo_pro.reservedPerShare = reservedPerShare

    def getEsp(self):
        return self._bo.esp

    def setEsp(self, esp):
        self._bo.esp = esp
        self._bo_pro.esp = esp

    def getBvps(self):
        return self._bo.bvps

    def setBvps(self, bvps):
        self._bo.bvps = bvps
        self._bo_pro.bvps = bvps

    def getPb(self):
        return self._bo.pb

    def setPb(self, pb):
        self._bo.pb = pb
        self._bo_pro.pb = pb

    def getList_date(self):
        return self._bo.list_date

    def setList_date(self, list_date):
        self._bo.list_date = list_date
        self._bo_pro.list_date = list_date

    def getNo_dividend(self):
        return self._bo.no_dividend

    def setNo_dividend(self, no_dividend):
        self._bo.no_dividend = no_dividend
        self._bo_pro.no_dividend = no_dividend

    def getPerundp(self):
        return self._bo.perundp

    def setPerundp(self, perundp):
        self._bo.perundp = perundp
        self._bo_pro.perundp = perundp

    def getRev_per(self):
        return self._bo.rev_per

    def setRev_per(self, rev_per):
        self._bo.rev_per = rev_per
        self._bo_pro.rev_per = rev_per

    def getProfit(self):
        return self._bo.profit

    def setProfit(self, profit):
        self._bo.profit = profit
        self._bo_pro.profit = profit

    def getGross_profit(self):
        return self._bo.gross_profit

    def setGross_profit(self, gross_profit):
        self._bo.gross_profit = gross_profit
        self._bo_pro.gross_profit = gross_profit

    def getNet_profit(self):
        return self._bo.net_profit

    def setNet_profit(self, net_profit):
        self._bo.net_profit = net_profit
        self._bo_pro.net_profit = net_profit

    def getHolder(self):
        return self._bo.holder

    def setHolder(self, holder):
        self._bo.holder = holder
        self._bo_pro.holder = holder

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
        return "StockBasicInfoBO_OTW ["+"stockcode = " + self.getStockcode() +"," +"stockname = " + self.getStockname() +"," +"industry = " + self.getIndustry() +"," +"area = " + self.getArea() +"," +"pe = " + str(self.getPe()) +"," +"offer_shares = " + str(self.getOffer_shares()) +"," +"total_shares = " + str(self.getTotal_shares()) +"," +"totalAssets = " + str(self.getTotalAssets()) +"," +"liquidAssets = " + str(self.getLiquidAssets()) +"," +"fixedAssets = " + str(self.getFixedAssets()) +"," +"reserved = " + str(self.getReserved()) +"," +"reservedPerShare = " + str(self.getReservedPerShare()) +"," +"esp = " + str(self.getEsp()) +"," +"bvps = " + str(self.getBvps()) +"," +"pb = " + str(self.getPb()) +"," +"list_date = " + self.getList_date() +"," +"no_dividend = " + str(self.getNo_dividend()) +"," +"perundp = " + str(self.getPerundp()) +"," +"rev_per = " + str(self.getRev_per()) +"," +"profit = " + str(self.getProfit()) +"," +"gross_profit = " + str(self.getGross_profit()) +"," +"net_profit = " + str(self.getNet_profit()) +"," +"holder = " + str(self.getHolder()) +"," +"uuid = " + self.getUuid() +"," +"boid = " + str(self.getBoid()) +"," +"destination = " + self.getDestination() +"," +"]"
