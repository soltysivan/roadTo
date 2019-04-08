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
		<form name="user" role="form" action="/registration" method="POST" enctype="multipart/form-data">
			<img src="../static/img/Регистрация-чп-бровары.png">
			<h1 class="loginTitle">Реєстрація</h1>
            <div class="message">${message!}</div>
            <div class="dws-input">
                <#if firstNameError??>
                    <div class="invalid-input">
                        ${firstNameError}
                    </div>
                </#if>
                <input class="${(firstNameError??)?string('invalid', '')}" value="<#if user??>${user.firstName}</#if>" type="text" name="firstName" placeholder="Введіть імя">
            </div>
            <div class="dws-input">
                <#if lastNameError??>
                    <div class="invalid-input">
                        ${lastNameError}
                    </div>
                </#if>
                <input class="${(lastNameError??)?string('invalid', '')}" value="<#if user??>${user.lastName}</#if>" type="text" name="lastName" placeholder="Введіть Фамілію">
            </div>
            <div class="dws-input">
                <#if usernameError??>
                <div class="invalid-input">
                    ${usernameError}
                </div>
                </#if>
                <input class="${(usernameError??)?string('invalid', '')}" value="<#if user??>${user.username}</#if>" type="text" name="username" placeholder="Введіть Login">
            </div>
            <div class="dws-input">
			<#if emailError??>
                <div class="invalid-input">
                    ${emailError}
                </div>
            </#if>
                <input class="${(emailError??)?string('invalid', '')}" type="email" name="email" value="<#if user??>${user.email}</#if>" placeholder="email@email.com">
		</div>
            <div class="dws-input">
                <#if telephoneError??>
                <div class="invalid-input">
                    ${telephoneError}
                </div>
                </#if>
                <input class="${(telephoneError??)?string('invalid', '')}" type="tel" id="tel" name="telephone" value="<#if user??>${user.telephone}</#if>" placeholder="Введіть ваш телефон">
            </div>
		<div class="dws-input">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>

			<#if passwordError??>
                <div class="invalid-input">
                    ${passwordError}
                </div>
            </#if>
            <input class="${(passwordError??)?string('invalid', '')}" type="password" name="password" placeholder="Введіть пароль">
		</div>
            <div class="dws-input">
			<#if password2Error??>
                <div class="invalid-input">
                    ${password2Error}
                </div>
            </#if>
                <input class="${(password2Error??)?string('invalid', '')}" type="password" name="password2" placeholder="Підтвердіть пароль">
            </div>
            <div class="kupsh">
                <#if captchaError??>
                    <div class="invalid-input">
                        ${captchaError}
                    </div>
                </#if>
            <div class="g-recaptcha" data-sitekey="6LctgpwUAAAAACwBapdmIuMq4bfXMFURUaOPtblM"></div>
            </div>
			<br/>
			<input class="dws-submitReg" type="submit"  value="Підтвердити">
		</form>
	</div>
</div>
</body>
</html>