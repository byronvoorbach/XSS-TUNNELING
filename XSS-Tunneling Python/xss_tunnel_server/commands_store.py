class CommandStore():
    def __init__(self):
        self.victim_commands = {}

    def add_command(self, victim_id, command):
        if not self.victim_commands.has_key(victim_id):
            self.victim_commands[victim_id] = []
        self.victim_commands[victim_id].append(command)

    def get_commands(self, victim_id):
        if self.victim_commands.has_key(victim_id):
            return self.victim_commands[victim_id]

        return []

    def clear(self, victim_id):
        if self.victim_commands.has_key(victim_id):
            self.victim_commands.clear()
