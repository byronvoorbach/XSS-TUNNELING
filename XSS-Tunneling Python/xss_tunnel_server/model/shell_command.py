import time


class ShellCommand():

    def __init__(self, victim_id, command, metadata):
        self.victim_id = victim_id
        self.date = time.localtime()
        self.command = command
        self.metadata = metadata

