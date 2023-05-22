CREATE DATABASE  IF NOT EXISTS `grocerydb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `grocerydb`;
-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: grocerydb
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `articles`
--

DROP TABLE IF EXISTS `articles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `articles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `article_name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_enabled` tinyint(1) DEFAULT '1',
  `stock` int NOT NULL,
  `unit_price` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `articles`
--

LOCK TABLES `articles` WRITE;
/*!40000 ALTER TABLE `articles` DISABLE KEYS */;
INSERT INTO `articles` VALUES (1,'Takis','Sabor Barbacoa',1,14,13.12),(2,'Galleta Principe','Sabor Limon',1,0,17),(3,'Azucar el cañal','De Caña Negra',1,0,12.99),(4,'Arroz El Progreso','En medias libra',1,0,13.99),(5,'Zambos','Picante Ceviche',1,0,5.99),(6,'Refresco Coca Cola 2l','Retornable',1,8,47.99),(7,'Refresco Pepsi 1.5L','Retornable',1,18,33.99),(8,'Refresco Seven Up 1.5L','Retornable',1,20,32.99),(9,'Refresco Fanta 2L','Retornable',1,2,47.99);
/*!40000 ALTER TABLE `articles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employees` (
  `email` varchar(255) DEFAULT NULL,
  `hire_date` date DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_j9xgmd0ya5jmus09o0b8pqrpb` (`email`),
  UNIQUE KEY `UK_3gqbimdf7fckjbwt1kcud141m` (`username`),
  CONSTRAINT `FKsfxfst8b5qgcjmw6nlktd94jh` FOREIGN KEY (`id`) REFERENCES `persons` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES ('random@gmail.com','2023-05-10','$2a$10$dJ9G2XH0gni0lHQSxr6T2umR3WR1V60lgoqE9daLGRLN4k/gash0e','ADMIN','admin_system',1),
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice`
--

DROP TABLE IF EXISTS `invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoice` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date` datetime(6) NOT NULL,
  `employee_id` bigint NOT NULL,
  `customer_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKd439t4ared4nkgj39kkudleg5` (`employee_id`),
  KEY `FKiv8vmpdnxrhua3dja72y3nsxd` (`customer_id`),
  CONSTRAINT `FKd439t4ared4nkgj39kkudleg5` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`),
  CONSTRAINT `FKiv8vmpdnxrhua3dja72y3nsxd` FOREIGN KEY (`customer_id`) REFERENCES `persons` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `invoice_detail`
--

DROP TABLE IF EXISTS `invoice_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoice_detail` (
  `article_id` bigint NOT NULL,
  `invoice_id` bigint NOT NULL,
  `amount` int NOT NULL,
  `unit_price` double NOT NULL,
  PRIMARY KEY (`article_id`,`invoice_id`),
  KEY `FKit1rbx4thcr6gx6bm3gxub3y4` (`invoice_id`),
  CONSTRAINT `FKit1rbx4thcr6gx6bm3gxub3y4` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id`),
  CONSTRAINT `FKn0lcll3srnhbaloqr5d9f0kfl` FOREIGN KEY (`article_id`) REFERENCES `articles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `persons`
--

DROP TABLE IF EXISTS `persons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `persons` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `dni` varchar(13) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `genre` bit(1) NOT NULL,
  `is_active` tinyint(1) DEFAULT '1',
  `last_name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) NOT NULL,
  `type` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_t0tma5e5ec4leolu2gaqpc9v7` (`dni`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persons`
--

LOCK TABLES `persons` WRITE;
/*!40000 ALTER TABLE `persons` DISABLE KEYS */;
INSERT INTO `persons` VALUES (1,'0109200000098','ADMIN',_binary '',1,'SYSTEM','+50498787378',_binary '');
UNLOCK TABLES;

--
-- Table structure for table `token`
--

DROP TABLE IF EXISTS `token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `token` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `expired` bit(1) NOT NULL,
  `revoked` bit(1) NOT NULL,
  `token` varchar(255) DEFAULT NULL,
  `token_type` varchar(255) DEFAULT NULL,
  `employee_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKryipflmv40b6pndxmsl7mi717` (`employee_id`),
  CONSTRAINT `FKryipflmv40b6pndxmsl7mi717` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `token`
--
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-18 10:05:55
