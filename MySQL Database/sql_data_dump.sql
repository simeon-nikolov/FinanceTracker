-- MySQL dump 10.13  Distrib 5.7.9, for Win32 (AMD64)
--
-- Host: 127.0.0.1    Database: finance_tracker
-- ------------------------------------------------------
-- Server version	5.7.11-log

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
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `accounts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  `balance` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_accounts_users1_idx` (`user_id`),
  CONSTRAINT `fk_accounts_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts`
--

LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` VALUES (7,'Cash',50000,9),(52,'Cash',0,59);
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `budget_types`
--

DROP TABLE IF EXISTS `budget_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `budget_types` (
  `type` varchar(15) NOT NULL,
  PRIMARY KEY (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `budget_types`
--

LOCK TABLES `budget_types` WRITE;
/*!40000 ALTER TABLE `budget_types` DISABLE KEYS */;
INSERT INTO `budget_types` VALUES ('ALL'),('CATEGORY'),('TYPE');
/*!40000 ALTER TABLE `budget_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `budgets`
--

DROP TABLE IF EXISTS `budgets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `budgets` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `begin_date` date NOT NULL,
  `end_date` date NOT NULL,
  `amount` int(11) NOT NULL,
  `currency` varchar(3) NOT NULL,
  `budget_type` varchar(15) NOT NULL,
  `repeat_type` varchar(15) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_budgets_users1_idx` (`user_id`),
  KEY `fk_budgets_currencies_idx` (`currency`),
  KEY `fk_budgets_budget_types_idx` (`budget_type`),
  KEY `fk_budgets_repeat_types_idx` (`repeat_type`),
  CONSTRAINT `fk_budgets_budget_types` FOREIGN KEY (`budget_type`) REFERENCES `budget_types` (`type`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_budgets_currencies` FOREIGN KEY (`currency`) REFERENCES `currencies` (`currency`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_budgets_repeat_types` FOREIGN KEY (`repeat_type`) REFERENCES `repeat_types` (`type`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_budgets_users1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `budgets`
--

LOCK TABLES `budgets` WRITE;
/*!40000 ALTER TABLE `budgets` DISABLE KEYS */;
INSERT INTO `budgets` VALUES (1,'2016-03-25','2016-03-28',10000,'BGN','ALL','NO_REPEAT',2);
/*!40000 ALTER TABLE `budgets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category` varchar(45) NOT NULL,
  `for_type` varchar(15) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`category`),
  KEY `fk_categories_finance_operation_types_idx` (`for_type`),
  CONSTRAINT `fk_categories_finance_operation_types` FOREIGN KEY (`for_type`) REFERENCES `finance_operation_types` (`type`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Salary','INCOME'),(2,'Bills','EXPENSE'),(63,'Food','EXPENSE');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `currencies`
--

DROP TABLE IF EXISTS `currencies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `currencies` (
  `currency` varchar(3) NOT NULL,
  PRIMARY KEY (`currency`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `currencies`
--

LOCK TABLES `currencies` WRITE;
/*!40000 ALTER TABLE `currencies` DISABLE KEYS */;
INSERT INTO `currencies` VALUES ('BGN'),('EUR'),('USD');
/*!40000 ALTER TABLE `currencies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `finance_operation_has_tags`
--

DROP TABLE IF EXISTS `finance_operation_has_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `finance_operation_has_tags` (
  `finance_operation_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL,
  KEY `fk_expenses_has_tags_expenses1_idx` (`finance_operation_id`),
  KEY `fk_expenses_has_tags_expense_tags1_idx` (`tag_id`),
  CONSTRAINT `fk_expenses_has_tags_expense_tags1` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_expenses_has_tags_expenses1` FOREIGN KEY (`finance_operation_id`) REFERENCES `finance_operations` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `finance_operation_has_tags`
--

LOCK TABLES `finance_operation_has_tags` WRITE;
/*!40000 ALTER TABLE `finance_operation_has_tags` DISABLE KEYS */;
INSERT INTO `finance_operation_has_tags` VALUES (34,137),(34,138),(33,135),(33,139);
/*!40000 ALTER TABLE `finance_operation_has_tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `finance_operation_types`
--

DROP TABLE IF EXISTS `finance_operation_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `finance_operation_types` (
  `type` varchar(15) NOT NULL,
  PRIMARY KEY (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `finance_operation_types`
--

LOCK TABLES `finance_operation_types` WRITE;
/*!40000 ALTER TABLE `finance_operation_types` DISABLE KEYS */;
INSERT INTO `finance_operation_types` VALUES ('EXPENSE'),('INCOME');
/*!40000 ALTER TABLE `finance_operation_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `finance_operations`
--

DROP TABLE IF EXISTS `finance_operations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `finance_operations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` int(11) NOT NULL,
  `currency` varchar(3) NOT NULL,
  `account_id` int(11) NOT NULL,
  `date` date NOT NULL,
  `description` varchar(200) NOT NULL,
  `repeat_type` varchar(15) NOT NULL,
  `category_id` int(11) NOT NULL,
  `finance_operation_type` varchar(15) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_expenses_accounts1_idx` (`account_id`),
  KEY `fk_expenses_expense_catgories1_idx` (`category_id`),
  KEY `fk_finance_operations_currencies_idx` (`currency`),
  KEY `fk_finance_operations_repeat_types_idx` (`repeat_type`),
  KEY `fk_finance_operations_finance_operation_types_idx` (`finance_operation_type`),
  CONSTRAINT `fk_expenses_accounts1` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_expenses_expense_catgories1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_finance_operations_currencies` FOREIGN KEY (`currency`) REFERENCES `currencies` (`currency`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_finance_operations_finance_operation_types` FOREIGN KEY (`finance_operation_type`) REFERENCES `finance_operation_types` (`type`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_finance_operations_repeat_types` FOREIGN KEY (`repeat_type`) REFERENCES `repeat_types` (`type`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `finance_operations`
--

LOCK TABLES `finance_operations` WRITE;
/*!40000 ALTER TABLE `finance_operations` DISABLE KEYS */;
INSERT INTO `finance_operations` VALUES (33,500,'BGN',7,'2016-04-05','Got some pizza...','NO_REPEAT',63,'EXPENSE'),(34,15700,'BGN',7,'2016-04-05','Paid the electricity bill.','NO_REPEAT',2,'EXPENSE');
/*!40000 ALTER TABLE `finance_operations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repeat_types`
--

DROP TABLE IF EXISTS `repeat_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `repeat_types` (
  `type` varchar(15) NOT NULL,
  PRIMARY KEY (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repeat_types`
--

LOCK TABLES `repeat_types` WRITE;
/*!40000 ALTER TABLE `repeat_types` DISABLE KEYS */;
INSERT INTO `repeat_types` VALUES ('CUSTOM'),('DAILY'),('MONTHLY'),('NO_REPEAT'),('WEEKLY'),('YAERLY');
/*!40000 ALTER TABLE `repeat_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tags`
--

DROP TABLE IF EXISTS `tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tags` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `for_type` varchar(15) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tags_finance_operation_types_idx` (`for_type`),
  CONSTRAINT `fk_tags_finance_operation_types` FOREIGN KEY (`for_type`) REFERENCES `finance_operation_types` (`type`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=140 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tags`
--

LOCK TABLES `tags` WRITE;
/*!40000 ALTER TABLE `tags` DISABLE KEYS */;
INSERT INTO `tags` VALUES (135,'food','EXPENSE'),(136,'fun','EXPENSE'),(137,'electricity','EXPENSE'),(138,'bills','EXPENSE'),(139,'consumings','EXPENSE');
/*!40000 ALTER TABLE `tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(100) NOT NULL,
  `email` varchar(25) NOT NULL,
  `currency` varchar(3) NOT NULL,
  `isAdmin` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `users_currencies_FK_idx` (`currency`),
  CONSTRAINT `fk_users_currencies` FOREIGN KEY (`currency`) REFERENCES `currencies` (`currency`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (2,'simnik','123123','asd@asd.asd','BGN',1),(9,'user123','$2a$10$lxu1PaEe3.qSDeQYwKs/AuBeVRatUfaieFo7bxWhrv7PwrrVHBYve','qwe@qwe.asd','BGN',0),(59,'newuser','$2a$10$i71HU4CAK23RyvEfv.OAMuFFougcF0leiVk0qnkAuNApmPKYFqc5O','qwea@asd.dsa','BGN',0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-04-05  3:02:18
