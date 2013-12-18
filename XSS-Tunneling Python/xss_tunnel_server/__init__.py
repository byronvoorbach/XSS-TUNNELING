from flask import Flask
import time

from xss_tunnel_server.victims_store import VictimStore
from xss_tunnel_server.commands_store import CommandStore

app = Flask(__name__)
app.debug = True

victim_store = VictimStore()
command_store = CommandStore()

import xss_tunnel_server.home
import xss_tunnel_server.shell
import xss_tunnel_server.tunnel

@app.template_filter('datetime')
def format_datetime(value, format='datetime'):
    if format == 'datetime':
        format = "%d-%m-%Y %H:%M:%S"
    elif format == 'date':
        format = "%d-%m-%Y"
    elif format == 'time':
        format = "%H:%M:%S"

    return time.strftime(format, value)
