<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"  content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" >
    <title>Trip</title>
    <link rel="shortcut icon" href="/static/img/ic.png" type="image/x-icon">
    <link rel="stylesheet" href="/static/css/index.css">
    <link rel="stylesheet" href="/static/css/mediaIndex.css">
    <link rel="stylesheet" href="/static/css/detailTrip.css">
    <link rel="stylesheet" href="/static/css/mediaForm.css">
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

<div class="buttonExRed">
<#if isAdmin>
    <a href="/trip/${trip.id}" class="buttonUpdate">Редагувати</a>
    <a href="/delete/trip/${trip.id}" class="buttonDelete">Видалити</a>
</#if>
</div>

    <div class="container-trip_detail">
        <div class="sulka"><a href="/dialog/1">зв'язатись із водієм</a></div>
        <div class="err">${mes?ifExists}</div>
        <form class="detail-trip" action="/trip/accept/${trip.id}" method="post" enctype="multipart/form-data">
         <div class="detail-trip_date">
             <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <h1> ${trip.date}</h1>
         </div>
            <div class="trip-detail_price">${trip.price}</div>
            <div class="trip-detail_img">
            <img  src="/static/img/road.png" >
            </div>
            <div class="trip-detail_tovn">
          <p>${trip.beginning} <br> <br> <br> ${trip.finish}</p>
                <#if citys??>
                <div class="promi">Проміжні пункти</div>
              <#list citys as city>
                  <div class="city">
                      ${city.name} - ${city.sss}
                  </div>
              </#list>
            </div>
            </#if>
            <div class="trip-details_submit">
              <input class="trip-detail_button" type="submit"  value="Прийняти">

            </div>
            <div class="trip-info">
                <p>Для бронювання місця, натисніть кнопку <p class="red">прийняти</p> </p>
            </div>
        </form>
    </div>
</body>
</html>