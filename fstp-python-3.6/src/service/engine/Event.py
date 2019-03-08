

"""事件对象"""
class Event:
    def __init__(self, type_=None, key=None, data=None):
        self.type_ = type_      # 事件类型
        self.key = key
        self.data = data         # 字典用于保存具体的事件数据