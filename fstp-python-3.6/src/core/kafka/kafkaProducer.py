#coding=utf-8
import json
from kafka import KafkaProducer

from com.purefun.fstp.core.bo.otw.TestBO_OTW import TestBO_OTW


def produce():
    producer = KafkaProducer(bootstrap_servers='localhost:9092')
    for i in range(2):
#         msg_dict = {
#             "db_config": {
#                 "database": "test_1",
#                 "host": "localhost",
#                 "user": "root",
#                 "password": "password"
#             },
#             "table": "msg",
#             "msg": "测试下的第%s条信息" % i
#         }
#         msg = json.dumps(msg_dict)
        bo_otw = TestBO_OTW()
        bo_otw.setMsg('123')
        producer.send(bo_otw.getDestination(), key=b'test', value=bo_otw.getProBO().SerializeToString())
    producer.close()


if __name__ == '__main__':
    produce()