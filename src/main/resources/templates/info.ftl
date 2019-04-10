<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"  content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" >
    <title>Info</title>
    <link rel="shortcut icon" href="/static/img/ic.png" type="image/x-icon">
    <link rel="stylesheet" href="/static/css/index.css">
    <link rel="stylesheet" href="/static/css/infoStyle.css">
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

<script src="/static/js/jquery-3.3.1.min.js"></script>
<script src="/static/js/script.js"></script>
<div class="container-info">
        <#if isAdmin>
    <form action="/info/message" method="post" enctype="multipart/form-data">
        <div>Нове повідомлення</div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <div class="enter-text"><input class="pole" type="text" name="text" placeholder="Введіть текст"></div>
        <div class="ok-message"><input class="delete-message" type="submit" value="OK"></div>
    </form>
            <form action="/telephone/create" method="post" enctype="multipart/form-data">
                <div>Задати телефон</div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <div class="enter-text"><input class="pole" type="text" name="text" placeholder="Введіть телефон"></div>
                <div class="ok-message"><input class="delete-message" type="submit" value="OK"></div>
            </form>
            <form action="/telephone/d" method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <div class="ok-message"><input class="delete-message" type="submit" value="Видалити"></div>
            </form>
        </#if>
    <#if infos??>
        <#list infos as info>
        <div class="textinfo">
    <span>
        ${info.text?ifExists}
        <#if isAdmin>
        <form action="/delete/message/${info.id}" method="post" enctype="multipart/form-data">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input class="delete-message" type="submit" value="Видалити">
        </form>
        </#if>
    </span>
        </div>
        </#list>
    </#if>
    </div>
<div class="textinfotel">
    <span>
        Підтримка в будь-якій країні
        <br>
        <br>
        <div class="phone">
            <p><#list telephone as foun>
                ${foun.text}<br>
                </#list>
            </p>
        </div>
    </span>
</div>
<br>
<br>


<div class="textinfo">
    <span>
        Коротко про RoadToEuro <br>
        RoadToEuro - це мережа перевізників до Європи, також з Європи в Україну.
        Це унікальна платформа для ефективних, надійних, та економічних подорожей по усьому світу.
        Якщо вам потрібно комфортно, а головне безпечно пересуватись на далекі відстані, тоді наш сервіс саме для вас.
        Ми забираєм пасажирів з місця де вам зручно і доставляєм на місце призначення для максимального комфорту.
        <br>
        Для того щоб обрати поїздку перейдіть за посиланням <a href="/trip/list">Поїздки</a>,
        тоді якщо вам усе підходить натисніть Прийняти, через деякий час з вами звяжеться Водій
        уточнити умови, для вашого максимального комфорту.
    </span>
</div>


<footer>
    <div class="container_footer">
        <div class="logo_footer">
            <a href="/trip/list">RoadToEuro</a>
        </div>
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