from flask import Flask

app = Flask(__name__)
app.debug = True

from xss_tunnel_server.victims_store import VictimStore
victimStore = VictimStore()

from xss_tunnel_server.commands_store import CommandStore
commandStore = CommandStore()

import xss_tunnel_server.home
import xss_tunnel_server.shell
import xss_tunnel_server.tunnel
