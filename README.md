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
 + [Downloading the application](#clone)
 + [Database dump deploying](#import)
 + [Database user creating](#setup_db)
 + [Setting up the project](#set_param)
 + [Application building](#compile)
3. The application deploying
 + [Deploying to Tomcat](#tomcat_deploy)
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


## Installing, building, preparing to run
### <a name="clone"></a>	Downdloading the application

To download the application use the following in command line:
	
	git clone https://github.com/MaximChshelokov/CarRentWebApp.git

	
### <a name="import"></a>	Database dump deploying

To deploy the database dump type in command line:
	
	mysql -u root -p < project path/db_dump.sql

Then input the root user's password. Database scheme will be created automatically.

To deploy the test database dump (whether is required to successfully build the project) input into command line:
	
	mysql -u root -p < project path/test_db_dump.sql
	
Then input the root user's password. Database scheme will be created automatically.	

### <a name="setup_db"></a>	Database user creating

The application uses default database username car_rent_app (and password Un3L41NoewVA).
To create the user run the mysql terminal and input:
	
	CREATE USER 'car_rent_app'@'localhost' IDENTIFIED BY 'Un3L41NoewVA';

If you want to use another username and password, you have to specify them to [database properties file](#set_param)

Use the following command to grant to the user access to the database (scheme):
    
    GRANT SELECT, INSERT, UPDATE, DELETE ON * . * TO 'car_rent_app'@'localhost';
    
### Setting up the timezone for the Windows users.

There is an error might occure during connect to the database if you're using the Windows OS because of MySQL couldn't set timezone automatically. To fix it run the mysql terminal and input:
    
    SET GLOBAL time_zone = 'YOUR TIMEZONE';
    
Give your timezone in +7:00 format instead of YOUR TIMEZONE.

### <a name="set_param"></a>	Setting up the project

In order to use an arbitrary username and password, change the following lines in src/main/resources/db_params.properties file:
	
	LOGIN=database user's name
	PASSWORD=database user's password
	
You also could change another database connection parameters, if necessery:
	
	URL=jdbc:mysql://localhost/DATABASE NAME?autoReconnect=true&useSSL=false&characterEncoding=utf-8
	POOL_SIZE=CONNECTION POOS SIZE
	
### <a name="compile"></a>  Application building

Input the following command to build the project:
	
	mvn clean install
	
## The application deploying
### <a name="tomcat_deploy"></a> Deploying to Tomcat

Copy directory project path/target/car_rent-1.0-SNAPSHOT/ content to the tomcat/webapps/ROOT directory.
Note: target directory creation covered in [Application building](#compile)

### <a name="start_tomcat"></a>  Запуск Tomcat

Для запуска контейнера Tomcat перейдите в директорию tomcat/bin и в командной строке выполните:
    
    startup
    
### <a name="browse"></a>   Открытие приложения в браузере

Чтобы открыть страницу приложения, в браузере в адресной строке введите localhost:8080 и нажмите "перейти".
Для того, чтобы войти под администратором, используйте логин admin@mail.com и пароль admin1.
Чтобы войти под пользователем, логин client@mail.com и пароль client.

