import time

class Victim():

    def __init__(self, id, request):
        firstSeen = time.strftime("%H:%M:%S")
        self.id = id
        self.ip = request.remote_addr
        self.userAgent = request.user_agent
        self.referrer = request.referrer
        self.firstSeen = firstSeen
        self.lastSeen = firstSeen
        self.commandQueue = []
        self.currentPage = ''

    def updateLastSeen(self):
        self.lastSeen = time.strftime("%H:%M:%S")

    def queueCommand(self, command):
        self.commandQueue.append(command)

    def hasCommands(self):
        return len(self.commandQueue) > 0

    def pollCommands(self):
        return self.commandQueue.pop()