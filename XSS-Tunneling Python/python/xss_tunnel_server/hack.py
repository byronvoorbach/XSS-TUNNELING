from flask import make_response
from functools import update_wrapper

def hack(f):
    def new_func(*args, **kwargs):
        resp = make_response(f(*args, **kwargs))

        resp.headers['Access-Control-Allow-Origin'] = '*'
        resp.headers['Access-Control-Allow-Methods'] = 'GET, POST, PUT, DELETE, OPTIONS'
        resp.headers['Access-Control-Allow-Headers'] = 'origin, content-type, accept, x-requested-with'
        resp.headers['Access-Control-Max-Age'] = '1800'

        return resp

    return update_wrapper(new_func, f)