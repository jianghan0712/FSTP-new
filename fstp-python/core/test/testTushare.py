#encoding=UTF-8
import tushare as ts
import pandas as ps

data = ts.get_k_data('603192',start='', end='') #一次性获取全部日k线数据
# print data
# data.to_csv('D:/1/600848.csv')

# data = ps.read_csv('D:/1/600848.csv')
print data