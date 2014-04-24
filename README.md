XSS-TUNNELING
=============
*Still under development*


### Mission
<BR>
My mission is to create an easy to use reverse shell as a PoC to show off the dangers of XSS

### Current features

*  Retrieve cookies (non HttpOnly cookies)
*  Retrieve current URL
*  Retrieve current page and show in iFrame
*  Retrieve localeStorage content
*  Visit page
*  Send message box
*  Start/Stop keylogger

### Planned features

*  Network scanning
*  Improve survivability of script
*  Send custom javascript
*  Increase stability of project

### Documentation
<BR>
Java based 'reverse shell' for XSS<BR>
Start a local server and deploy the war either exploded or packaged.<BR>
Open one page with localhost:8080 and one with localhost:8080/victim.

localhost:8080 will be notified with a new connection.

This shell can also be used to in combination with sites that have XSS.<BR>
Find your own way of hosting this server online and get the hack.js script to be loaded inside a vulnerable page. 

### Disclaimer
<BR>
I created this to be used as a PoC to show off the dangers of XSS.<BR>
This has not been created for any evil purposes...