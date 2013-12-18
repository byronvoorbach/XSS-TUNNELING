import time


class VictimStore():
    def __init__(self):
        self.victims = {}

    def add_victim(self, victim_id, victim):
        if not victim_id in self.victims:
            self.victims[victim_id] = victim

    def remove_victim(self, victim_id):
        if victim_id in self.victims:
            del self.victims[victim_id]

    def get_victim(self, victim_id):
        if victim_id in self.victims:
            return self.victims[victim_id]

        return None

    def has_victim(self, victim_id):
        return victim_id in self.victims

    def get_victims(self):
        return self.victims.values()

    def clear_expired_victims(self):
        current = time.mktime(time.localtime())
        for k, v in self.victims.items():
            if current - time.mktime(v.last_seen) > 5:
                del self.victims[k]
