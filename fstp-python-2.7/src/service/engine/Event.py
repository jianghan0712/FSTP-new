#encoding=utf-8


class Event:
    def __init__(self, type_=None, data=None):
        self.type_ = type_      # 事件类型
        self.data = data         # 字典用于保存具体的事件数据