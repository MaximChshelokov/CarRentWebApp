# Car rent Java EE web app

Учебный проект веб-приложение Аренда машин.

## <a name="inctrument"></a>	Подготовка необходимых инструментов
### <a name="database"></a>	Подготовка базы данных
#### <a name="mysql"></a>	Установка MySQL

Для корректной работы приложения требуется MySQL версии не ниже 5.7

[Дистрибутив СУБД MySQL](https://dev.mysql.com/downloads/mysql/)

[Инструкция по установке СУБД MySQL](https://dev.mysql.com/doc/refman/8.0/en/installing.html)

### Подготовка среды выполнения	
##### <a name="jdk"></a>	Установка среды Java

Для корректной работы приложения требуется установить Java Development Kit версии 8 (1.8)

[Страница для скачивания JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

[Инструкция по установке JDK](https://docs.oracle.com/javase/8/docs/technotes/guides/install/index.html)

##### <a name="tomcat"></a>	Установка контейнера Tomcat

Приложение протестировано на работоспособность с контейнером Tomcat версии 8.5
Скачайте и установите Tomcat 8.5

[Страница для скачивания Tomcat 8.5](https://tomcat.apache.org/download-70.cgi)

[Инструкция по установке Tomcat](https://tomcat.apache.org/tomcat-8.5-doc/setup.html)

### Инструменты установки и сборки приложения
#### <a name="git"></a>	Установка системы контроля версий Git

Установка Git опциональна, и производится для удобства скачивания приложения из репозитория git-hub.

[Страница для скачивания Git](https://git-scm.com/downloads)

#### <a name="maven"></a> Установка системы maven

Установка системы автоматической сборки и управления пакетами maven версии 3.3 или выше необходима для сборки, тестирования и размещения приложения.

[Страница для скачивания maven](https://maven.apache.org/download.cgi)

[Инструкция по установке maven](https://maven.apache.org/install.html)


## <a name="setup"></a> Установка, сборка, подготовка к запуску
### Настройка СУБД
##### <a name="setup_db"><a/>	Создание пользователя базы данных

Приложение изначально настроено на работу с базой данных под учётной записью car_rent_app (и паролем Un3L41NoewVA).
Для создание необходимого пользователя:
Запустите терминал mysql, и выполните команду:
	
	CREATE USER 'car_rent_app'@'localhost' IDENTIFIED BY 'Un3L41NoewVA';
	
Если вы хотите использовать другие имя пользователя и пароль, необходимо внести их в [файл настроек базы данных](#set_param)

##### <a name="import"></a>	Развертывание дампа базы данных

Для развертывания БД введите в командной строке:
	
	mysql -u root -p < путь к проект/db_dump.sql
	
Затем введите пароль root пользователя MySQL. База данных (схема) будет создана автоматически.

Для развертывания тестовой БД (которая необходима для успешной сборки проекта) выполните в командной строке:
	
	mysql -u root -p < путь к проекту_/test_db_dump.sql
	
Затем введите пароль root пользователя MySQL. База данных (схема) будет создана автоматически.

### <a name="clone"></a>	Скачивание приложения

Для скачивания приложения из репозитория Git-hub введите в командной строке:
	
	git clone https://github.com/MaximChshelokov/CarRentWebApp.git

	
### <a name="set_param"></a>	Настройка проекта

В случае, если вы хотите использовать произвольные имя пользователя и пароль пользователя базы данных, измените строки в файле src/main/resources/db_params.properties
	
	LOGIN=имя пользователя БД
	PASSWORD=пароль пользователя БД
	
Так же при необходимости, можно настроить другие параметры соединения с БД:
	
	URL=jdbc:mysql://localhost/car_rent?autoReconnect=true&useSSL=false&characterEncoding=utf-8
	DRIVER=com.mysql.cj.jdbc.Driver
	POOL_SIZE=размер пула подключений к БД
	
### Сборка приложения

Для сборки приложения введите в командной строке:
	
	
	
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

