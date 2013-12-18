from flask import render_template
from xss_tunnel_server import app, victim_store

from xss_tunnel_server.nocache import nocache
from xss_tunnel_server.hack import hack


@app.route('/')
@nocache
def index():
    victim_store.clear_expired_victims()
    return render_template('index.html', title='Home',
                           victims=victim_store.get_victims())


@app.route('/victim')
@nocache
@hack
def victim():
    return render_template('victim.html')
