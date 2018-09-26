CREATE DATABASE  IF NOT EXISTS `car_rent_test` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `car_rent_test`;
-- MySQL dump 10.13  Distrib 5.7.23, for Linux (x86_64)
--
-- Host: localhost    Database: car_rent_test
-- ------------------------------------------------------
-- Server version	5.7.23-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `car_parts`
--

DROP TABLE IF EXISTS `car_parts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `car_parts` (
  `part_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  PRIMARY KEY (`part_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `car_parts`
--

LOCK TABLES `car_parts` WRITE;
/*!40000 ALTER TABLE `car_parts` DISABLE KEYS */;
INSERT INTO `car_parts` VALUES (1,'Заднее левое крылышко'),(2,'Заднее правое крыло');
/*!40000 ALTER TABLE `car_parts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cars`
--

DROP TABLE IF EXISTS `cars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cars` (
  `car_id` int(11) NOT NULL AUTO_INCREMENT,
  `model_id` int(11) NOT NULL,
  `license_plate` varchar(8) NOT NULL,
  `year_of_make` int(4) NOT NULL,
  `price` int(11) NOT NULL,
  `available` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`car_id`),
  KEY `fk_cars_1_idx` (`model_id`),
  CONSTRAINT `fk_cars_1` FOREIGN KEY (`model_id`) REFERENCES `models` (`model_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cars`
--

LOCK TABLES `cars` WRITE;
/*!40000 ALTER TABLE `cars` DISABLE KEYS */;
INSERT INTO `cars` VALUES (1,6,'342sum09',2018,38,_binary ''),(6,5,'327cum09',2013,2000,_binary ''),(35,1,'228cum09',2009,6010,_binary '\0'),(40,20,'583cum09',2008,6000,_binary '\0'),(42,1,'228cum09',2009,6000,_binary '\0'),(43,13,'424sum09',1999,2500,_binary ''),(55,1,'228cum09',2009,6000,_binary '\0'),(85,1,'228cum09',2009,6000,_binary '\0'),(87,1,'228cum09',2009,6000,_binary '\0'),(88,1,'228cum09',2009,6000,_binary '\0'),(89,1,'228cum09',2009,6000,_binary '\0'),(90,1,'228cum09',2009,6000,_binary '\0'),(91,1,'228cum09',2009,6000,_binary '\0'),(92,1,'228cum09',2009,6000,_binary '\0'),(93,1,'228cum09',2009,6000,_binary '\0');
/*!40000 ALTER TABLE `cars` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `cars_full`
--

DROP TABLE IF EXISTS `cars_full`;
/*!50001 DROP VIEW IF EXISTS `cars_full`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `cars_full` AS SELECT 
 1 AS `car_id`,
 1 AS `license_plate`,
 1 AS `year_of_make`,
 1 AS `price`,
 1 AS `model_id`,
 1 AS `model_name`,
 1 AS `make_id`,
 1 AS `make_name`,
 1 AS `available`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `defects`
--

DROP TABLE IF EXISTS `defects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `defects` (
  `defect_id` int(11) NOT NULL AUTO_INCREMENT,
  `car` int(11) NOT NULL,
  `car_part` int(11) NOT NULL,
  `description` varchar(500) NOT NULL,
  `fixed` bit(1) NOT NULL,
  PRIMARY KEY (`defect_id`),
  KEY `fk_defects_1_idx` (`car`),
  KEY `fk_defects_2_idx` (`car_part`),
  CONSTRAINT `fk_defects_1` FOREIGN KEY (`car`) REFERENCES `cars` (`car_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_defects_2` FOREIGN KEY (`car_part`) REFERENCES `car_parts` (`part_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `defects`
--

LOCK TABLES `defects` WRITE;
/*!40000 ALTER TABLE `defects` DISABLE KEYS */;
INSERT INTO `defects` VALUES (2,6,1,'Царапина 11 см',_binary '\0'),(50,6,1,'Скол лакокрасочного покрытия 5 см^2',_binary '\0');
/*!40000 ALTER TABLE `defects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `defects_full`
--

DROP TABLE IF EXISTS `defects_full`;
/*!50001 DROP VIEW IF EXISTS `defects_full`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `defects_full` AS SELECT 
 1 AS `defect_id`,
 1 AS `car`,
 1 AS `description`,
 1 AS `fixed`,
 1 AS `car_part`,
 1 AS `name`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `invoice_lines`
--

DROP TABLE IF EXISTS `invoice_lines`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoice_lines` (
  `line_id` int(11) NOT NULL AUTO_INCREMENT,
  `invoice_id` int(11) NOT NULL,
  `details` varchar(100) NOT NULL,
  `amount` int(11) NOT NULL,
  PRIMARY KEY (`line_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice_lines`
--

LOCK TABLES `invoice_lines` WRITE;
/*!40000 ALTER TABLE `invoice_lines` DISABLE KEYS */;
INSERT INTO `invoice_lines` VALUES (1,1,'Штраф за задержку на 5 дней',2075),(4,58,'Оплата за аренду машины на 15 дней',30000),(6,58,'Возврат за аренду машины за 7 дней',-14000),(8,59,'Предоплата за аренду машины на 6 дней',22000);
/*!40000 ALTER TABLE `invoice_lines` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice_types`
--

DROP TABLE IF EXISTS `invoice_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoice_types` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice_types`
--

LOCK TABLES `invoice_types` WRITE;
/*!40000 ALTER TABLE `invoice_types` DISABLE KEYS */;
INSERT INTO `invoice_types` VALUES (1,'overtime penalty'),(2,'penalty'),(3,'rent total');
/*!40000 ALTER TABLE `invoice_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoices`
--

DROP TABLE IF EXISTS `invoices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoices` (
  `invoice_id` int(11) NOT NULL,
  `date` date NOT NULL,
  `paid` int(11) DEFAULT '0',
  PRIMARY KEY (`invoice_id`),
  CONSTRAINT `fk_invoices_1` FOREIGN KEY (`invoice_id`) REFERENCES `rent_orders` (`rent_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoices`
--

LOCK TABLES `invoices` WRITE;
/*!40000 ALTER TABLE `invoices` DISABLE KEYS */;
INSERT INTO `invoices` VALUES (1,'2018-06-19',1500);
/*!40000 ALTER TABLE `invoices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `invoices_full`
--

DROP TABLE IF EXISTS `invoices_full`;
/*!50001 DROP VIEW IF EXISTS `invoices_full`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `invoices_full` AS SELECT 
 1 AS `invoice_id`,
 1 AS `date`,
 1 AS `paid`,
 1 AS `total`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `makes`
--

DROP TABLE IF EXISTS `makes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `makes` (
  `make_id` int(11) NOT NULL AUTO_INCREMENT,
  `make_name` varchar(15) NOT NULL,
  PRIMARY KEY (`make_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `makes`
--

LOCK TABLES `makes` WRITE;
/*!40000 ALTER TABLE `makes` DISABLE KEYS */;
INSERT INTO `makes` VALUES (1,'Toyota'),(4,'Lada'),(6,'Mazda'),(7,'Volkswagen');
/*!40000 ALTER TABLE `makes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `models`
--

DROP TABLE IF EXISTS `models`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `models` (
  `model_id` int(11) NOT NULL AUTO_INCREMENT,
  `model_name` varchar(15) NOT NULL,
  `make_id` int(11) NOT NULL,
  PRIMARY KEY (`model_id`),
  KEY `fk_models_1_idx` (`make_id`),
  CONSTRAINT `fk_models_1` FOREIGN KEY (`make_id`) REFERENCES `makes` (`make_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `models`
--

LOCK TABLES `models` WRITE;
/*!40000 ALTER TABLE `models` DISABLE KEYS */;
INSERT INTO `models` VALUES (1,'Supra',1),(2,'Corola',1),(5,'Corola',4),(6,'Largus',4),(7,'Corola',1),(8,'Corola',1),(9,'Corola',1),(11,'Niva',4),(13,'RX-7',6),(14,'Golf',7),(20,'Aqua',1),(21,'Granta',4);
/*!40000 ALTER TABLE `models` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `models_full`
--

DROP TABLE IF EXISTS `models_full`;
/*!50001 DROP VIEW IF EXISTS `models_full`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `models_full` AS SELECT 
 1 AS `model_id`,
 1 AS `model_name`,
 1 AS `make_id`,
 1 AS `make_name`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `rejection_reasons`
--

DROP TABLE IF EXISTS `rejection_reasons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rejection_reasons` (
  `reason_id` int(11) NOT NULL,
  `reason` varchar(100) NOT NULL,
  PRIMARY KEY (`reason_id`),
  CONSTRAINT `fk_rejection_reasons_1` FOREIGN KEY (`reason_id`) REFERENCES `rent_orders` (`rent_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rejection_reasons`
--

LOCK TABLES `rejection_reasons` WRITE;
/*!40000 ALTER TABLE `rejection_reasons` DISABLE KEYS */;
/*!40000 ALTER TABLE `rejection_reasons` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rent_orders`
--

DROP TABLE IF EXISTS `rent_orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rent_orders` (
  `rent_id` int(11) NOT NULL AUTO_INCREMENT,
  `car` int(11) NOT NULL,
  `user` int(11) DEFAULT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `approved_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`rent_id`),
  KEY `fk_rent_orders_1_idx` (`car`),
  KEY `fk_rent_orders_2_idx` (`user`),
  KEY `fk_rent_orders_3_idx` (`approved_by`),
  CONSTRAINT `fk_rent_orders_1` FOREIGN KEY (`car`) REFERENCES `cars` (`car_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rent_orders_2` FOREIGN KEY (`user`) REFERENCES `users` (`user_id`) ON DELETE SET NULL ON UPDATE NO ACTION,
  CONSTRAINT `fk_rent_orders_3` FOREIGN KEY (`approved_by`) REFERENCES `users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rent_orders`
--

LOCK TABLES `rent_orders` WRITE;
/*!40000 ALTER TABLE `rent_orders` DISABLE KEYS */;
INSERT INTO `rent_orders` VALUES (1,1,3,'2018-06-11','2018-06-20',1),(2,1,NULL,'2018-06-25','2018-06-30',NULL),(46,6,4,'2018-06-15','2018-06-30',NULL),(52,6,4,'2018-06-15','2018-06-30',NULL),(58,6,4,'2018-07-22','2018-07-30',31),(84,6,4,'2018-06-15','2018-06-30',NULL);
/*!40000 ALTER TABLE `rent_orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `rent_orders_full`
--

DROP TABLE IF EXISTS `rent_orders_full`;
/*!50001 DROP VIEW IF EXISTS `rent_orders_full`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `rent_orders_full` AS SELECT 
 1 AS `rent_id`,
 1 AS `start_date`,
 1 AS `end_date`,
 1 AS `car_id`,
 1 AS `license_plate`,
 1 AS `year_of_make`,
 1 AS `price`,
 1 AS `model_id`,
 1 AS `model_name`,
 1 AS `make_id`,
 1 AS `make_name`,
 1 AS `user_id`,
 1 AS `login`,
 1 AS `approved_by`,
 1 AS `approver_login`,
 1 AS `available`,
 1 AS `reason_id`,
 1 AS `reason`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(10) NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'admin'),(2,'user'),(3,'guest'),(4,'guest');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(20) NOT NULL,
  `password` varchar(128) NOT NULL,
  `role` int(11) NOT NULL,
  PRIMARY KEY (`user_id`),
  KEY `fk_users_1_idx` (`role`),
  CONSTRAINT `fk_users_1` FOREIGN KEY (`role`) REFERENCES `roles` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'boss@mail.com','admin',1),(2,'client','clientpass',2),(3,'vovchik','123',2),(4,'Dronchik','321',2),(19,'sobaka@mail.ru','12345',2),(24,'artem','6f61a84f857751a172e11ecb5c295b6c77a1b76ba16bf870ea50da40512e58a398b6c403ebaee6485195c999f828e53e03e2f06f2ef7340b08b88e6b7eb9f7eb',2),(25,'rorka@gmail.com','ytrewq',2),(31,'admin@mail.com','4166fc9a68f8a018055a724876be7ff65b2d8195e5993d8cea720721486c88fa4158567a6b8e3c8e92be5e58102df73c7910b9a444a2de6a4108b8111959e7ca',1),(32,'abdul@mail.ru','94a42f5403528ebc87179de3b7dd825b988cfd403a34cac93e269d81d81254370df607cf8686c1092456dd6c9aa9c95611675bf4ac7ef45736d9f158517d0005',2);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_data`
--

DROP TABLE IF EXISTS `users_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users_data` (
  `userdata_id` int(11) NOT NULL,
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `address` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `phone` varchar(13) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`userdata_id`),
  CONSTRAINT `fk_users_data_1` FOREIGN KEY (`userdata_id`) REFERENCES `users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_data`
--

LOCK TABLES `users_data` WRITE;
/*!40000 ALTER TABLE `users_data` DISABLE KEYS */;
INSERT INTO `users_data` VALUES (1,'Пётр Семёнович Одинцов','пр. Республики, д. 55/4, кв. 21','88005553535'),(2,'Андрей Иванович Давидович','ул. Пушкина, дом Кукушкина','+71233215467'),(3,'Василий Петрович Адамантьев','ул. Блюхера, д. 33, кв 22','77021233181'),(4,'Андрей Павлович Папанов','ул. Коммунаров, д. 12, кв 21','+77773334422'),(25,'Анатолий Петрович','улица Ленина','77021234567'),(32,'Абдулов Абдула Исламович','г. Ашхабад, ул. Магомеда 12','77093452375');
/*!40000 ALTER TABLE `users_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `users_data_full`
--

DROP TABLE IF EXISTS `users_data_full`;
/*!50001 DROP VIEW IF EXISTS `users_data_full`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `users_data_full` AS SELECT 
 1 AS `login`,
 1 AS `password`,
 1 AS `userdata_id`,
 1 AS `name`,
 1 AS `address`,
 1 AS `phone`,
 1 AS `role_id`,
 1 AS `role_name`,
 1 AS `user_id`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `users_full`
--

DROP TABLE IF EXISTS `users_full`;
/*!50001 DROP VIEW IF EXISTS `users_full`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `users_full` AS SELECT 
 1 AS `user_id`,
 1 AS `login`,
 1 AS `password`,
 1 AS `role_id`,
 1 AS `role_name`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `cars_full`
--

/*!50001 DROP VIEW IF EXISTS `cars_full`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `cars_full` AS select `cars`.`car_id` AS `car_id`,`cars`.`license_plate` AS `license_plate`,`cars`.`year_of_make` AS `year_of_make`,`cars`.`price` AS `price`,`cars`.`model_id` AS `model_id`,`models`.`model_name` AS `model_name`,`models`.`make_id` AS `make_id`,`makes`.`make_name` AS `make_name`,`cars`.`available` AS `available` from ((`cars` left join `models` on((`cars`.`model_id` = `models`.`model_id`))) left join `makes` on((`models`.`make_id` = `makes`.`make_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `defects_full`
--

/*!50001 DROP VIEW IF EXISTS `defects_full`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `defects_full` AS select `defects`.`defect_id` AS `defect_id`,`defects`.`car` AS `car`,`defects`.`description` AS `description`,`defects`.`fixed` AS `fixed`,`defects`.`car_part` AS `car_part`,`car_parts`.`name` AS `name` from (`defects` left join `car_parts` on((`defects`.`car_part` = `car_parts`.`part_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `invoices_full`
--

/*!50001 DROP VIEW IF EXISTS `invoices_full`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `invoices_full` AS select `invoices`.`invoice_id` AS `invoice_id`,`invoices`.`date` AS `date`,`invoices`.`paid` AS `paid`,(select sum(`invoice_lines`.`amount`) from `invoice_lines` where (`invoices`.`invoice_id` = `invoice_lines`.`invoice_id`)) AS `total` from `invoices` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `models_full`
--

/*!50001 DROP VIEW IF EXISTS `models_full`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `models_full` AS select `models`.`model_id` AS `model_id`,`models`.`model_name` AS `model_name`,`models`.`make_id` AS `make_id`,`makes`.`make_name` AS `make_name` from (`models` left join `makes` on((`models`.`make_id` = `makes`.`make_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `rent_orders_full`
--

/*!50001 DROP VIEW IF EXISTS `rent_orders_full`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `rent_orders_full` AS select `rent_orders`.`rent_id` AS `rent_id`,`rent_orders`.`start_date` AS `start_date`,`rent_orders`.`end_date` AS `end_date`,`rent_orders`.`car` AS `car_id`,`cars`.`license_plate` AS `license_plate`,`cars`.`year_of_make` AS `year_of_make`,`cars`.`price` AS `price`,`cars`.`model_id` AS `model_id`,`m`.`model_name` AS `model_name`,`m`.`make_id` AS `make_id`,`k`.`make_name` AS `make_name`,`rent_orders`.`user` AS `user_id`,`u`.`login` AS `login`,`rent_orders`.`approved_by` AS `approved_by`,`a`.`login` AS `approver_login`,`cars`.`available` AS `available`,`rent_orders`.`rent_id` AS `reason_id`,`rejection_reasons`.`reason` AS `reason` from ((((((`rent_orders` left join `cars` on((`rent_orders`.`car` = `cars`.`car_id`))) left join `models` `m` on((`cars`.`model_id` = `m`.`model_id`))) left join `makes` `k` on((`m`.`make_id` = `k`.`make_id`))) left join `users` `u` on((`rent_orders`.`user` = `u`.`user_id`))) left join `users` `a` on((`rent_orders`.`approved_by` = `a`.`user_id`))) left join `rejection_reasons` on((`rejection_reasons`.`reason_id` = `rent_orders`.`rent_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `users_data_full`
--

/*!50001 DROP VIEW IF EXISTS `users_data_full`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `users_data_full` AS select `users_full`.`login` AS `login`,`users_full`.`password` AS `password`,`users_data`.`userdata_id` AS `userdata_id`,`users_data`.`name` AS `name`,`users_data`.`address` AS `address`,`users_data`.`phone` AS `phone`,`users_full`.`role_id` AS `role_id`,`users_full`.`role_name` AS `role_name`,`users_full`.`user_id` AS `user_id` from (`users_full` left join `users_data` on((`users_full`.`user_id` = `users_data`.`userdata_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `users_full`
--

/*!50001 DROP VIEW IF EXISTS `users_full`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `users_full` AS select `users`.`user_id` AS `user_id`,`users`.`login` AS `login`,`users`.`password` AS `password`,`users`.`role` AS `role_id`,`roles`.`role_name` AS `role_name` from (`users` left join `roles` on((`users`.`role` = `roles`.`role_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-09-26 14:51:39
