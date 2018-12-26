# Car rent Java EE web application

## Table of contents

1. Preparing required instruments
 + [Installing MySQL](#mysql)
 + [Installing Java Development Kit](#jdk)
 + [Installing Tomcat](#tomcat)
 + [Preparing a deployment directory](#tom_dir)
 + [Installing Git version control system](#git)
 + [Installing maven build automation system](#maven)
2. Installing, building, preparing to run
 + [Getting the application](#clone)
 + [Database dump deploying](#import)
 + [Database user creating](#setup_db)
 + [Setting up the project](#set_param)
 + [Application building](#compile)
3. Application deploying
 + [Installing Tomcat](#tomcat_deploy)
 + [Running Tomcat](#start_tomcat)
 + [Opening application in browser](#browse)



Car rent application studing project.

##	Preparing required instruments
### <a name="mysql"></a>	Installing MySQL

The application requires MySQL version 5.7 and higher.

[MySQL distributive](https://dev.mysql.com/downloads/mysql/)

[MySQL install instruction](https://dev.mysql.com/doc/refman/8.0/en/installing.html)

### <a name="jdk"></a>	Installing Java Development Kit

The application requires Java Development Kit version 8 (1.8)

[JDK 8 download page](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

[JDK install instruction](https://docs.oracle.com/javase/8/docs/technotes/guides/install/index.html)

### <a name="tomcat"></a>	Installing Tomcat
The application has been tested in Tomcat 8.5
Download and install Tomcat 8.5

[Tomcat 8.5 download page](https://tomcat.apache.org/download-70.cgi)

[Tomcat install instruction](https://tomcat.apache.org/tomcat-8.5-doc/setup.html)

### <a name="tom_dir"></a>    Preparing a deployment directory

Remove or move the content of tomcat/webapps/ROOT to another directory.

### <a name="git"></a>	Installing Git version control system

Git installation is optional, in order to simplify downloading application from git-hub.

[Git download page](https://git-scm.com/downloads)

### <a name="maven"></a> Installing maven build automation system

Maven 3.3 build automation system is required to build, test and deploy application.

[Maven download page](https://maven.apache.org/download.cgi)

[Maven install instruction](https://maven.apache.org/install.html)


## Установка, сборка, подготовка к запуску
### <a name="clone"></a>	Скачивание приложения

Для скачивания приложения из репозитория Git-hub введите в командной строке:
	
	git clone https://github.com/MaximChshelokov/CarRentWebApp.git

	
### <a name="import"></a>	Развертывание дампа базы данных

Для развертывания БД введите в командной строке:
	
	mysql -u root -p < путь к проекту/db_dump.sql
	
Затем введите пароль root пользователя MySQL. База данных (схема) будет создана автоматически.

Для развертывания тестовой БД (которая необходима для успешной сборки проекта) выполните в командной строке:
	
	mysql -u root -p < путь к проекту/test_db_dump.sql
	
Затем введите пароль root пользователя MySQL. База данных (схема) будет создана автоматически.

### <a name="setup_db"></a>	Создание пользователя базы данных

Приложение изначально настроено на работу с базой данных под учётной записью car_rent_app (и паролем Un3L41NoewVA).
Для создание необходимого пользователя:
Запустите терминал mysql, и выполните команду:
	
	CREATE USER 'car_rent_app'@'localhost' IDENTIFIED BY 'Un3L41NoewVA';
	
Если вы хотите использовать другие имя пользователя и пароль, необходимо внести их в [файл настроек базы данных](#set_param).

Чтобы предоставить доступ данному пользователю к базе данных (схеме) приложения, выполните команду:
    
    GRANT SELECT, INSERT, UPDATE, DELETE ON * . * TO 'car_rent_app'@'localhost';
    
### Установка часового пояса MySQL для пользователей Windows

При использовании СУБД MySQL под ОС Windows при подключении к БД может возникнуть ошибка, заключающаяся в невозможности автоматической установки часового пояса. Чтобы избежать данной ошибки запустите терминал mysql, и выполните команду:
    
    SET GLOBAL time_zone = 'ВАШ ЧАСОВОЙ ПОЯС';
    
Где вместо слов ВАШ ЧАСОВОЙ ПОЯС укажите свой часовой пояс в формате +7:00

### <a name="set_param"></a>	Настройка проекта

В случае, если вы хотите использовать произвольные имя и пароль пользователя базы данных, измените строки в файле src/main/resources/db_params.properties
	
	LOGIN=имя пользователя БД
	PASSWORD=пароль пользователя БД
	
Так же при необходимости, можно настроить другие параметры соединения с БД:
	
	URL=jdbc:mysql://localhost/имя базы?autoReconnect=true&useSSL=false&characterEncoding=utf-8
	POOL_SIZE=размер пула подключений к БД
	
### <a name="compile"></a>  Сборка приложения

Для сборки приложения введите в командной строке:
	
	mvn clean install
	
## Развертывание приложения
### <a name="tomcat_deploy"></a> Установка в контейнер Tomcat

Скопируйте содержимое директории путь к проекту/target/car_rent-1.0-SNAPSHOT/ в директорию tomcat/webapps/ROOT
Примечание: директория target создается в пункте [Сборка приложения](#compile)

### <a name="start_tomcat"></a>  Запуск Tomcat

Для запуска контейнера Tomcat перейдите в директорию tomcat/bin и в командной строке выполните:
    
    startup
    
### <a name="browse"></a>   Открытие приложения в браузере

Чтобы открыть страницу приложения, в браузере в адресной строке введите localhost:8080 и нажмите "перейти".
Для того, чтобы войти под администратором, используйте логин admin@mail.com и пароль admin1.
Чтобы войти под пользователем, логин client@mail.com и пароль client.

