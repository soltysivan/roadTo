<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"  content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" >
    <title>User List</title>
    <link rel="shortcut icon" href="/static/img/ic.png" type="image/x-icon">
    <link rel="stylesheet" href="../static/css/welcome.css">
    <link rel="stylesheet" href="../static/css/index.css">
    <link rel="stylesheet" href="/static/css/eStyle.css">
    <link rel="stylesheet" href="/static/css/mediaIndex.css">
</head>
<body>
<div class="fix-menu">
    <div class="burger-menu">

        <a href="/"><img src="/static/img/logo.png" alt="Roadtoeuro" class="logo"></a>
        <a class="button_menu" href="/trip/list">Знайти поїздку</a>
        <a href="#" class="burger-menu_button">
            <span class="burger-menu_lines"></span>
        </a>
        <nav class="burger-menu_nav">
	<#include "security.ftl">
            <a href="/" class="burger-menu_link">Головна</a>
					<#if isAdmin>
                        <a href="/list/user" class="burger-menu_link">Користувачі</a>
                        <a href="/trip/adm" class="burger-menu_link" >Пасажири</a>
                    </#if>
            <a href="/info" class="burger-menu_link">Інфо</a>
	<#if !user??>
	<a href="/registration" class="burger-menu_link" >Реєстрація</a>
	<a href="/login" class="burger-menu_link">Вхід</a>
    </#if>
	<#if user??>
        <#if isAdmin>
	<a href="/dialog/list" class="burger-menu_link">Повідомлення</a>
        <#else>
	<a href="/dialog/1" class="burger-menu_link">Повідомлення</a>
        </#if>
	<a href="/profile" class="burger-menu_link">${name}</a>
	<form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <input class="burger-menu_logout" type="submit" value="Вихід">
    </form>
    </#if>
        </nav>
        <div class="burger-menu_overlay"></div>
    </div>
</div>

<script src="/static/js/jquery-3.3.1.min.js"></script>
<script src="/static/js/script.js"></script>

    <div class="button-exc"><a class="createExc" href="/trip/adm">Список Пасажирів</a></div>
    <table class="userList">
        <div class="dostup">Кількість зареєстрованих користувачів - ${size}</div>
        <tr>
            <th>Імя</th>
            <th>Email</th>
            <th>Телефон</th>
            <th>Роль</th>
            <th></th>
        </tr>
<#list users as user>
    <tr>
        <td><a href="/edit/${user.id}">${user.username}</a></td>
        <td>${user.email}</td>
        <td>${user.telephone}</td>
        <td><#list user.roles as roles>${roles}<#sep>, </#list></td>
        <td><a href="/delete/${user.id}">Del</a></td>
    </tr>
</#list>
    </table>

</div>
</body>
</html>