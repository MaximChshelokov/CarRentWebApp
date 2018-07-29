# Car rent Java EE web app

Учебный проект веб-приложение Аренда машин.

## <a name="deploy"></a>	Установка проекта
##### <a name="database"></a>	Подготовка базы данных
###### <a name="mysql"></a>	Установка MySQL

Для корректной работы приложения требуется MySQL Community Edition версии не ниже 5.7
[Дистрибутив СУБД MySQL](https://dev.mysql.com/downloads/mysql/)
[Инструкция по установке СУБД MySQL](https://dev.mysql.com/doc/refman/8.0/en/installing.html)

###### <a name="setup_db"><a/>	Создание пользователя базы данных

Приложение изначально настроено на работу с базой данных под учётной записью car_rent_app (и паролем Un3L41NoewVA).
Для создание необходимого пользователя:
Запустите терминал mysql, и выполните команду:
	
	CREATE USER 'car_rent_app'@'localhost' IDENTIFIED BY 'Un3L41NoewVA';
	
Вы также можете создать пользователя с произвольными именем и паролем. В этом случае, после создания пользователя, внесите ваши имя пользователя и пароль от MySQL в файл /src/main/resources/db_params.properties
	
	LOGIN=__ваш логин__
	PASSWORD=__ваш пароль__
	

 
###### <a name="import"></a>	Развертывание дампа базы данных

Для развертывания БД введите в командной строке:
	
	mysql -u root -p < __путь к проекту__/db_dump.sql
	
Затем введите пароль root пользователя MySQL.

Для развертывания тестовой БД (которая необходима для успешной сборки проекта) выполните в командной строке:
	
	mysql -u root -p < __путь к проекту__/test_db_dump.sql

##### <a name="jre"></a>	Установка среды Java

Для корректной работы приложения требуется установить Java Development Kit версии 8 (1.8)
[Страница для скачивания JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
[Инструкция по установке JDK](https://docs.oracle.com/javase/8/docs/technotes/guides/install/index.html)

##### <a name="tomcat"></a>	Установка контейнера Tomcat


Затем введите пароль root пользователя MySQL.
1. Скачайте и распакуйте проект.
2. Скачайте и установите MySQL сервер.
3. Восстановите дамп базы данных, находящийся в car_rent/db_dump.sql
4. Создайте пользователя MySQL с именем car_rent_app и паролем Un3L41NoewVA
5. Скачайте и установите tomcat.
6. Удалите содержимое директории tomcat/webapps/ROOT
7. Скопируйте содержимое директории car_rent/target/car_rent-1.0-SNAPSHOT/ в директорию tomcat/webapps/ROOT
8. Запустите tomcat
9. Откройте в браузере адрес localhost:8080/

