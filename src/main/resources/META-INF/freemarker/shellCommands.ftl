<#compress>
<textarea id="output" disabled>
    <#if commands??>
        <#list commands as command>
            ${command.date} ${command.metaData}
        </#list>
    </#if>
</textarea>
</#compress>