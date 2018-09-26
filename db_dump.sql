CREATE DATABASE  IF NOT EXISTS `car_rent` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `car_rent`;
-- MySQL dump 10.13  Distrib 5.7.23, for Linux (x86_64)
--
-- Host: localhost    Database: car_rent
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cars`
--

LOCK TABLES `cars` WRITE;
/*!40000 ALTER TABLE `cars` DISABLE KEYS */;
INSERT INTO `cars` VALUES (1,1,'235sum09',2010,5000,_binary ''),(2,2,'734cum09',2012,7000,_binary '');
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice_lines`
--

LOCK TABLES `invoice_lines` WRITE;
/*!40000 ALTER TABLE `invoice_lines` DISABLE KEYS */;
/*!40000 ALTER TABLE `invoice_lines` ENABLE KEYS */;
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
  `paid` int(11) DEFAULT NULL,
  PRIMARY KEY (`invoice_id`),
  CONSTRAINT `fk_invoices_1` FOREIGN KEY (`invoice_id`) REFERENCES `rent_orders` (`rent_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoices`
--

LOCK TABLES `invoices` WRITE;
/*!40000 ALTER TABLE `invoices` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `makes`
--

LOCK TABLES `makes` WRITE;
/*!40000 ALTER TABLE `makes` DISABLE KEYS */;
INSERT INTO `makes` VALUES (1,'Lada');
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `models`
--

LOCK TABLES `models` WRITE;
/*!40000 ALTER TABLE `models` DISABLE KEYS */;
INSERT INTO `models` VALUES (1,'Granta',1),(2,'Kalina',1);
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rent_orders`
--

LOCK TABLES `rent_orders` WRITE;
/*!40000 ALTER TABLE `rent_orders` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'admin'),(2,'user');
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (2,'admin@mail.com','f118d3bf958ddd3eff6d0f91242299e147bd101dc09a943afee1e83776a9cca71bac7d00393d56f4fba2db221eb0a8e74c6fd83b6a53db4c204366887fcbda76',1),(3,'client@mail.com','a81a840b05c3beb88384df56925f654705e025119c9f8adb20a50c4cbc82f4a9cf205e2f75cc5394bbfb432233791cd2f0c58e5e22a4f70cc378999ec5942968',2),(4,'client1@mail.com','d366c2e8507ead11cd5648c7e9c99ae7cfc278591d7d07495d373521db52b01a9adf4024fff3b4fb87fa2e997683dab106115df8bc97aa9048c580d1990d4534',2);
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
INSERT INTO `users_data` VALUES (3,'Иванов Иван Иванович','ул. Бассеитовой 23, кв. 15','77755352354'),(4,'Есенбек Нургали','Бухар Жырау, 20, 85','77055071221');
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

-- Dump completed on 2018-09-26 14:51:08
