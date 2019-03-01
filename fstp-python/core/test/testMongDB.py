#encoding=utf-8

import pymongo
import pandas as pd

client = pymongo.MongoClient('localhost', 27017)
# data = pd.read_csv('D:/tushareData/data/白酒/000568.csv',encoding='gbk')
# print data

collection = client[u'银行']['IF0000']
print collection
# dbCursor = collection.find()
# for d in dbCursor:
#     print d
