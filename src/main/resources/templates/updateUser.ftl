<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"  content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" >
    <title>Edit</title>
    <link rel="shortcut icon" href="/static/img/ic.png" type="image/x-icon">
    <link rel="stylesheet" href="../static/css/formStyle.css">
    <link rel="stylesheet" href="/static/css/index.css">
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

<div class="formLogReg">
    <div class="containerForm">
        <form name="user" role="form" action="/edit/save" method="POST" enctype="multipart/form-data">
            <img class="avatar" <#if avatar??>src="data:image/png;base64,${avatar!}"<#else> src="/static/img/Programmyi-dlya-sozdaniya-avatarok.png" </#if>>
            <h1 class="loginTitle">Редагування</h1>
            <div class="message">${message!}</div>
            <div class="dws-input">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <input type="text" name="username" placeholder="Введіть імя" value="${username}">
            </div>
            <div class="dws-input">
                <input type="email" name="email" placeholder="email@email.com" value="${email}">
                <input type="hidden" name="id" value="${id}">
            </div>
            <div class="dws-input">
                <input type="tel" id="tel" name="telephone" value="${telephone}" placeholder="Введіть ваш телефон">
            </div>
                 <#list roles as roles>
                     <div>
                         <label><input type="checkbox" name="${roles}" ${user.roles?seq_contains(roles)?string("checked", "")}>${roles}</label>
                     </div>
                 </#list>
            <br/>
            <input class="dws-submitReg" type="submit"  value="Підтвердити">
        </form>
    </div>
</div>
</body>
</html>