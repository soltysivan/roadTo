<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
	<meta charset="UTF-8">
    <meta name="viewport"  content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" >
	<title>Registration</title>
    <link rel="shortcut icon" href="/static/img/ic.png" type="image/x-icon">
    <link rel="stylesheet" href="/static/css/formStyle.css">
    <link rel="stylesheet" href="/static/css/index.css">
    <link rel="stylesheet" href="/static/css/mediaIndex.css">
    <link rel="stylesheet" href="/static/css/mediaForm.css">
    <script src="https://www.google.com/recaptcha/api.js"></script>
</head>
<body>
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
	<a href="registration/tel" class="burger-menu_link" >Реєстрація</a>
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



<div class="formLogReg">
	<div class="containerForm">
		<form name="user" role="form" action="/registration/tel" method="POST" enctype="multipart/form-data">
			<img src="/static/img/reg.png">
			<h1 class="loginTitle">Реєстрація</h1>
            <div class="message">${message!}</div>
            <div class="textsms">Введіть номер телефону</div>
            <div class="dws-input">
                <#if usernameError??>
                <div class="invalid-input">
                    ${usernameError}
                </div>
                </#if>
                <input id="phone" class="${(usernameError??)?string('invalid', '')}" value="<#if user??>${username?ifExists}</#if>" type="text" name="username" placeholder="+380999999999">
            </div>
		<div class="dws-input">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            </div>
			<br/>
			<input class="dws-submitReg" type="submit"  value="Надіслати СМС">
		</form>
	</div>
</div>
</div>
<script src="/static/js/jquery-3.3.1.min.js"></script>
<script src="/static/js/script.js"></script>
<script src="/static/js/jquery.maskedinput.min.js" type="text/javascript"></script>
<script>
    $(document).ready(function() {
        $("#phone").mask("+38(099)999-99-99");
    });
</script>
</body>
</html>