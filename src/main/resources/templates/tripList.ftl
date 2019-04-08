<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"  content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" >
    <title>Trip</title>
    <link rel="shortcut icon" href="/static/img/ic.png" type="image/x-icon">
    <link rel="stylesheet" href="/static/css/index.css">
    <link rel="stylesheet" href="/static/css/welcome.css">
    <link rel="stylesheet" href="/static/css/mediaIndex.css">
    <link rel="stylesheet" href="/static/css/mediaList.css">
</head>
<body>

<div class="fix-menu">
    <div class="burger-menu">

        <a href="/"><img src="/static/img/logo.png" alt="Roadtoeuro" class="logo"></a>
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
<#if isAdmin>
<div class="button-exc">
    <a class="createExc" href="/trip/save">Створити поїздку</a>
</div>
</#if>
<div class="container_trip_top">
    <div class="dostup">Доступно ${size} поїздка(и)</div>
<#list trips as trip>
<div class="container_trip">

    <a href="/details/${trip.id}" class="container_trip_item">
        <div>
        <p class="price_trip">
            ${trip.price}
        </p>
        </div>
        <img class="flag_trip" src="/static/img/car.png">
        <h3>
            ${trip.beginning} - ${trip.finish}
        </h3>
        <p>
             ${trip.date}
        </p>

    </a>
</div>
</#list>

</div>
<div class="footik">.</div>
<footer>
    <div class="container_footer">
        <div class="phone">
            <p><#list telephone as foun>
                    ${foun.text}<br>
                </#list>
            </p>
        </div>
        <div class="email">
            <p>eroadto@gmail.com</p>
        </div>
    </div>
</footer>
</body>
</html>