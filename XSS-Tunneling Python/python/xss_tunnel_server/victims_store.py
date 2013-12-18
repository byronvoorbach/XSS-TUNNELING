class VictimStore():

    def __init__(self):
        self.victims = {}

    def addVictim(self, id, victim):
        if not self.victims.has_key(id):
            self.victims[id] = victim

    def removeVictim(self, id):
        if self.victims.has_key(id):
            self.pop(id, None)

    def getVictim(self, id):
        if self.victims.has_key(id):
            return self.victims[id]

        return None

    def hasVictim(self, id):
        return self.victims.has_key(id)

    def getVictims(self):
        return self.victims.values()
