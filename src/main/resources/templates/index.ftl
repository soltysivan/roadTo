<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport"  content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" >
	<title>RoadTo</title>
	<link rel="stylesheet" href="/static/css/index.css">
	<link rel="stylesheet" href="/static/css/mediaIndex.css">
    <link rel="shortcut icon" href="/static/img/ic.png" type="image/x-icon">
	<script src="https://www.google.com/recaptcha/api.js"></script>
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

	<header class="headerMain">
		<div class="container">
			<div class="title">
				<div class ="title_first">
					Ласкаво просимо !
				</div>
				<h1 class="indextitle">
					Ми завжди раді вам
				</h1>
			</div>
			<a class="button" href="/trip/list">Знайти поїздку</a>
		</div>
		</header>
		<section id="services">
		<div class="container_service">
			<div class="title_service">
				<h2>
					Послуги
				</h2>
				<p>
					Ознайомтесь з переліком послуг!
				</p>
			</div>
			<div class="services">
				<div class="service">
				<a href="/trip/list" class="services_item">
					<img src="/static/img/icon2.png" alt="Услуга">
					<h3>
						Україна - Польща
					</h3>
					<p>
						Поїздка з України до Польщі.
						Краків,Катовіце,Ополе,<br>Вроцлав.
					</p>
				</a>
			</div>
				<div class="service">
				<a href="/trip/list" class="services_item">
					<img src="/static/img/icon1.png" alt="Услуга">
					<h3>
						Чехія - Польща - Україна
					</h3>
					<p>
						Поїздка з Чехії ч/з Польщу до України.
						Львів,Стрий,Івано-Франківськ.
					</p>
				</a>
				</div>
				<div class="service">
				<a href="/trip/list" class="services_item">
					<img class="flag" src="/static/img/cz.png" alt="Услуга">
					<h3>
						Україна - Чехія
					</h3>
					<p>
						Поїздка з України до Чехії.
						Львів,Стрий,Івано-Франківськ.
					</p>
				</a>
			</div>
			</div>
		</div>
	</section>
	<section>
		<div class="container_2_back">
		<div class="container_2">
			<div class="text_2">
				<h2>
					3 причини, чому вам <br>
					сподобається RoadToEuro.
				</h2>
			</div>
			<div class="container_2_op">
				<a href="/trip/list" class="container_2_item">
					<img class="flag" src="/static/img/1.png" alt="Услуга">
					<h3>
						Спільнота
					</h3>
					<p>
						Ми хочемо дізнатися більше про наших користувачів. Наша команда перевіряє всі профілі та відгуки. Отже, ви точно знаєте, з ким збираєтесь у поїздку.
					</p>
				</a>
			</div>
			<div class="container_2_op">
				<a href="/trip/list" class="container_2_item">
					<img class="flag" src="/static/img/2.png" alt="Услуга">
					<h3>
						Розумно
					</h3>
					<p>
						Забудьте про те, що потрібно поспішати на станцію через все місто.
					</p>
				</a>
			</div>
			<div class="container_2_op">
				<a href="/trip/list" class="container_2_item">
					<img class="flag" src="/static/img/3.png" alt="Услуга">
					<h3>
						Швидкість
					</h3>
					<p>
						60 секунд. Саме стільки часу в середньому потрібно, щоб знайти потрібну поїздку.
					</p>
				</a>
			</div>
			</div>
			</div>
	</section>
	<div class="car_fon">
	<div class="container_car">
			<div class="title_car">
				<h2>
					Транспорт
				</h2>
				<p>
					Комфортне авто для любих поїздок!
				</p>
			</div>
			<div class="car">
					<img src="/static/img/auto3.jpg" alt="Транспорт">
					<img src="/static/img/auto1.jpg" alt="Транспорт">
			</div>
	</div>
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