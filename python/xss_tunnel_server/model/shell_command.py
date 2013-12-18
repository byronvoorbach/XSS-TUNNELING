import time

class ShellCommand():

    def __init__(self, id, type, metaData):
        self.id = id
        self.date = time.strftime("%H:%M:%S")
        self.type = type
        self.metaData = metaData

