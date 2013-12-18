class CommandStore():

    def __init__(self):
        self.victimCommands = {}

    def addCommand(self, id, command):
        if not self.victimCommands.has_key(id):
            self.victimCommands[id] = []

        self.victimCommands[id].append(command)

    def getCommands(self, id):
        if self.victimCommands.has_key(id):
            return self.victimCommands[id]

        return []

    def clear(self):
        if self.victimCommands.has_key(id):
            self.victimCommands.clear()
