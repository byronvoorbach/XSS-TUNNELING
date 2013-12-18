from flask import render_template
from xss_tunnel_server import app, victimStore

from xss_tunnel_server.nocache import nocache
from xss_tunnel_server.hack import hack

@app.route('/')
@nocache
def index():
    return render_template('index.html', title = 'Home',
                           victims = victimStore.getVictims())

@app.route('/victim')
@nocache
@hack
def victim():
    return render_template('victim.html')
