-- MySQL dump 10.13  Distrib 5.7.22, for Linux (x86_64)
--
-- Host: localhost    Database: car_rent
-- ------------------------------------------------------
-- Server version	5.7.22-0ubuntu0.16.04.1

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
  `model` int(11) NOT NULL,
  `license_plate` varchar(8) NOT NULL,
  `year_of_make` int(4) NOT NULL,
  `price` int(11) NOT NULL,
  `available` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`car_id`),
  KEY `fk_cars_1_idx` (`model`),
  CONSTRAINT `fk_cars_1` FOREIGN KEY (`model`) REFERENCES `models` (`model_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
 1 AS `model`,
 1 AS `name`,
 1 AS `make`,
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
  `type` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  `defect` int(11) DEFAULT NULL,
  PRIMARY KEY (`line_id`),
  KEY `fk_invoce_lines_1_idx` (`type`),
  CONSTRAINT `fk_invoce_lines_1` FOREIGN KEY (`type`) REFERENCES `invoice_types` (`type_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary table structure for view `invoice_lines_full`
--

DROP TABLE IF EXISTS `invoice_lines_full`;
/*!50001 DROP VIEW IF EXISTS `invoice_lines_full`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `invoice_lines_full` AS SELECT 
 1 AS `line_id`,
 1 AS `invoice_id`,
 1 AS `details`,
 1 AS `type`,
 1 AS `name`,
 1 AS `amount`,
 1 AS `defect`*/;
SET character_set_client = @saved_cs_client;

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
  `name` varchar(15) NOT NULL,
  PRIMARY KEY (`make_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `models`
--

DROP TABLE IF EXISTS `models`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `models` (
  `model_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(15) NOT NULL,
  `make` int(11) NOT NULL,
  PRIMARY KEY (`model_id`),
  KEY `fk_models_1_idx` (`make`),
  CONSTRAINT `fk_models_1` FOREIGN KEY (`make`) REFERENCES `makes` (`make_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary table structure for view `models_full`
--

DROP TABLE IF EXISTS `models_full`;
/*!50001 DROP VIEW IF EXISTS `models_full`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `models_full` AS SELECT 
 1 AS `model_id`,
 1 AS `name`,
 1 AS `make`,
 1 AS `make_name`*/;
SET character_set_client = @saved_cs_client;

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
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
 1 AS `car`,
 1 AS `license_plate`,
 1 AS `year_of_make`,
 1 AS `price`,
 1 AS `model`,
 1 AS `model_name`,
 1 AS `make`,
 1 AS `make_name`,
 1 AS `user`,
 1 AS `login`,
 1 AS `approved_by`,
 1 AS `approver_login`*/;
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
 1 AS `role`,
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
 1 AS `role`,
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
/*!50001 VIEW `cars_full` AS select `cars`.`car_id` AS `car_id`,`cars`.`license_plate` AS `license_plate`,`cars`.`year_of_make` AS `year_of_make`,`cars`.`price` AS `price`,`cars`.`model` AS `model`,`models`.`name` AS `name`,`models`.`make` AS `make`,`makes`.`name` AS `make_name`,`cars`.`available` AS `available` from ((`cars` left join `models` on((`cars`.`model` = `models`.`model_id`))) left join `makes` on((`models`.`make` = `makes`.`make_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `invoice_lines_full`
--

/*!50001 DROP VIEW IF EXISTS `invoice_lines_full`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `invoice_lines_full` AS select `invoice_lines`.`line_id` AS `line_id`,`invoice_lines`.`invoice_id` AS `invoice_id`,`invoice_lines`.`details` AS `details`,`invoice_lines`.`type` AS `type`,`invoice_types`.`name` AS `name`,`invoice_lines`.`amount` AS `amount`,`invoice_lines`.`defect` AS `defect` from (`invoice_lines` left join `invoice_types` on((`invoice_lines`.`type` = `invoice_types`.`type_id`))) */;
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
/*!50001 VIEW `models_full` AS select `models`.`model_id` AS `model_id`,`models`.`name` AS `name`,`models`.`make` AS `make`,`makes`.`name` AS `make_name` from (`models` left join `makes` on((`models`.`make` = `makes`.`make_id`))) */;
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
/*!50001 VIEW `rent_orders_full` AS select `rent_orders`.`rent_id` AS `rent_id`,`rent_orders`.`start_date` AS `start_date`,`rent_orders`.`end_date` AS `end_date`,`rent_orders`.`car` AS `car`,`cars`.`license_plate` AS `license_plate`,`cars`.`year_of_make` AS `year_of_make`,`cars`.`price` AS `price`,`cars`.`model` AS `model`,`m`.`name` AS `model_name`,`m`.`make` AS `make`,`k`.`name` AS `make_name`,`rent_orders`.`user` AS `user`,`u`.`login` AS `login`,`rent_orders`.`approved_by` AS `approved_by`,`a`.`login` AS `approver_login` from (((((`rent_orders` left join `cars` on((`rent_orders`.`car` = `cars`.`car_id`))) left join `models` `m` on((`cars`.`model` = `m`.`model_id`))) left join `makes` `k` on((`m`.`make` = `k`.`make_id`))) left join `users` `u` on((`rent_orders`.`user` = `u`.`user_id`))) left join `users` `a` on((`rent_orders`.`approved_by` = `a`.`user_id`))) */;
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
/*!50001 VIEW `users_data_full` AS select `users_full`.`login` AS `login`,`users_full`.`password` AS `password`,`users_data`.`userdata_id` AS `userdata_id`,`users_data`.`name` AS `name`,`users_data`.`address` AS `address`,`users_data`.`phone` AS `phone`,`users_full`.`role` AS `role`,`users_full`.`role_name` AS `role_name`,`users_full`.`user_id` AS `user_id` from (`users_full` left join `users_data` on((`users_full`.`user_id` = `users_data`.`userdata_id`))) */;
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
/*!50001 VIEW `users_full` AS select `users`.`user_id` AS `user_id`,`users`.`login` AS `login`,`users`.`password` AS `password`,`users`.`role` AS `role`,`roles`.`role_name` AS `role_name` from (`users` left join `roles` on((`users`.`role` = `roles`.`role_id`))) */;
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

-- Dump completed on 2018-07-19 14:29:59
