<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"  content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" >
    <title>Profile</title>
    <link rel="shortcut icon" href="/static/img/ic.png" type="image/x-icon">
    <link rel="stylesheet" href="/static/css/index.css">
    <link rel="stylesheet" href="/static/css/profileStyle.css">
    <link rel="stylesheet" href="/static/css/mediaIndex.css">
    <link rel="stylesheet" href="/static/css/mediaProfile.css">
</head>
<body>
<div class="profile-update-all">
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
	<a href="/registration/tel" class="burger-menu_link" >Реєстрація</a>
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
<div class="container_profile">
    <div class="profile_form_update">
        <form name="user" role="form" action="/pass/update" method="post" enctype="multipart/form-data">
            <h1 class="profile_name1">${user.getFirstLastName()}</h1>
            <div class="message">${message!}</div>
            <h1 class="profile_name">Старий пароль</h1>
            <div class="profile_input">
                <#if passwordError??>
                    <div class="invalid-input">
                        ${passwordError}
                    </div>
                </#if>
                <input class="${(passwordError??)?string('invalid', '')}" type="password" name="password" placeholder="Введіть старий пароль">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            </div>
            <h1 class="profile_name">Новий пароль</h1>
            <div class="profile_input">
                <#if passwordError1??>
                    <div class="invalid-input">
                        ${passwordError1}
                    </div>
                </#if>
                <input class="${(passwordError1??)?string('invalid', '')}" type="password" name="password1" placeholder="Введіть новий пароль">
			</div>
            <div class="profile_input">
                <#if passwordError2??>
                    <div class="invalid-input">
                        ${passwordError2}
                    </div>
                </#if>
                <input class="${(passwordError2??)?string('invalid', '')}" type="password" name="password2" placeholder="Повторіть новий пароль">
            </div>
            <br/>
            <input class="profile_button" type="submit"  value="Підтвердити">
        </form>
    </div>
</div>
</div>
</body>
</html>