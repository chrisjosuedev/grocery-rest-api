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
-- Dumping data for table `invoice`
--

LOCK TABLES `invoice` WRITE;
/*!40000 ALTER TABLE `invoice` DISABLE KEYS */;
INSERT INTO `invoice` VALUES (5,'2023-05-15 19:35:51.398000',3,4),(12,'2023-05-15 19:58:16.072000',3,6),(15,'2023-05-15 20:51:57.318000',3,6),(16,'2023-05-15 20:54:39.962000',3,6),(27,'2023-05-15 21:08:18.671000',3,6),(30,'2023-05-15 21:16:12.865000',3,6),(31,'2023-05-15 21:25:30.748000',3,6),(35,'2023-05-15 21:36:42.986000',3,6),(37,'2023-05-15 21:49:15.889000',3,6),(38,'2023-05-15 21:49:32.912000',3,6),(39,'2023-05-15 21:53:01.366000',3,6),(40,'2023-05-18 00:29:41.608000',11,6),(41,'2023-05-18 01:51:52.603000',13,6);
/*!40000 ALTER TABLE `invoice` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `invoice_detail`
--

LOCK TABLES `invoice_detail` WRITE;
/*!40000 ALTER TABLE `invoice_detail` DISABLE KEYS */;
INSERT INTO `invoice_detail` VALUES (1,5,2,13.12),(1,35,10,13.12),(1,37,1,13.12),(1,38,2,13.12),(1,39,3,13.12),(2,5,2,17),(2,12,10,17),(3,15,3,12.99),(3,16,3,12.99),(4,27,1,13.99),(4,35,1,13.99),(5,12,1,5.99),(6,15,10,47.99),(6,16,10,47.99),(6,27,10,47.99),(6,30,5,47.99),(6,31,5,47.99),(7,30,3,33.99),(7,31,3,33.99),(8,40,2,32.99),(8,41,2,32.99),(9,40,5,47.99),(9,41,5,47.99);
/*!40000 ALTER TABLE `invoice_detail` ENABLE KEYS */;
UNLOCK TABLES;

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
INSERT INTO `persons` VALUES (1,'0109200000098','ADMIN',_binary '',1,'SYSTEM','+50498787378',_binary ''),(2,'0309200000004','Maria Luisa',_binary '',1,'Mejia Lopez','+50498787378',_binary ''),(3,'0301199900034','Chris',_binary '',1,'Martinez','+50488148614',_binary ''),(4,'0301200600004','Suyapa',_binary '\0',1,'Lara','+50487675689',_binary '\0'),(6,'0301196200004','Jose',_binary '\0',1,'Martinez','+50497828220',_binary '\0'),(7,'0109199800089','Angel Mariano',_binary '',1,'Martinez Lara','+50489838923',_binary ''),(11,'0309201100090','Maria Fernanda',_binary '\0',1,'Martinez Silva','+50488938920',_binary ''),(13,'0309199800098','Morgan',_binary '',1,'Freeman','+50497828293',_binary '');
/*!40000 ALTER TABLE `persons` ENABLE KEYS */;
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

LOCK TABLES `token` WRITE;
/*!40000 ALTER TABLE `token` DISABLE KEYS */;
INSERT INTO `token` VALUES (1,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbl9zeXN0ZW0iLCJpYXQiOjE2ODM4NDc2MDQsImV4cCI6MTY4Mzg0OTA0NH0.LpZzRgPW0XV8pJPRo9JoqwD5LtRHis3uaP14IJXlK5Y','BEARER',1),(2,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYXJpYS5sb3BleiIsImlhdCI6MTY4Mzg0ODI5MCwiZXhwIjoxNjgzODQ5NzMwfQ.HaOTLSgOUlxe9b2y9fFo-8vyt2Nnux8y7mPCDCKF60U','BEARER',2),(3,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbl9zeXN0ZW0iLCJpYXQiOjE2ODM4NDgzNDQsImV4cCI6MTY4Mzg0OTc4NH0.5QrQHm8HVGLEgMmJpXjv8GnGX1PvBggbF-MBXFggfHo','BEARER',1),(4,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbl9zeXN0ZW0iLCJpYXQiOjE2ODM4NDg1MTQsImV4cCI6MTY4Mzg0OTk1NH0.g35BHd0Ol-FoCC2uF5Zgq_Ah8VL5vv4vi5fQUSNUYqo','BEARER',1),(5,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYXJpYS5sb3BleiIsImlhdCI6MTY4Mzg0ODU2NiwiZXhwIjoxNjgzODUwMDA2fQ.HLtQZzK5zlkihgiv1VWxsE-VB-j3meb0hIn0qKlg3to','BEARER',2),(6,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYXJpYS5sb3BleiIsImlhdCI6MTY4Mzg0ODY5MSwiZXhwIjoxNjgzODUwMTMxfQ.wA9-Fpw7B5gaFHcU8-QC3GGRqHjjHRvNzALtFx7AR7A','BEARER',2),(7,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbl9zeXN0ZW0iLCJpYXQiOjE2ODM4NDg3MzAsImV4cCI6MTY4Mzg1MDE3MH0.TfRrnlpX70eBvn5TRDjqWfCoRgNCPrZWsqThAej7Du8','BEARER',1),(8,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbl9zeXN0ZW0iLCJpYXQiOjE2ODM5MDkyMTMsImV4cCI6MTY4MzkxMDY1M30.U_dCWgZz3y856w7pJg4LhljUxW-Y_DQlIQNXfvo1Wx8','BEARER',1),(9,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbl9zeXN0ZW0iLCJpYXQiOjE2ODM5MDk0MDgsImV4cCI6MTY4MzkxMDg0OH0.HjioPOGVGuvriTzZnGqFzaVQ87HdXUiLlRHkBrfyHNc','BEARER',1),(10,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjcmlzdGhpYW4ubWFydGluZXoiLCJpYXQiOjE2ODM5MDk1NzYsImV4cCI6MTY4MzkxMTAxNn0.dDnrryP47k0KFR_mDZ7Y5MaQ_tRPUsJ_0Dp1eJuFmRY','BEARER',3),(11,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjcmlzdGhpYW4ubWFydGluZXoiLCJpYXQiOjE2ODM5MDk2OTAsImV4cCI6MTY4MzkxMTEzMH0.x_WS5t9ORHJY0tqf3gkNVC2352EUGwpNwD0owS7E3c4','BEARER',3),(12,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbl9zeXN0ZW0iLCJpYXQiOjE2ODM5MTAxMzAsImV4cCI6MTY4MzkxMTU3MH0.kIof1Zi1Dm4pnKRhuGM0FCb8JD5QjbrWs0XI5JeV6XI','BEARER',1),(13,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbl9zeXN0ZW0iLCJpYXQiOjE2ODM5MTAzOTQsImV4cCI6MTY4MzkxMTgzNH0.ofN0Kzh-rWivGSSKS1I3hdaqrwol6MQ-94Ww9-ramoQ','BEARER',1),(14,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbl9zeXN0ZW0iLCJpYXQiOjE2ODM5MTA1MDksImV4cCI6MTY4MzkxMTk0OX0.1vcLuPtpXU_U2RqSl5BA19xKxDo9xXLtaUNxio2x0AE','BEARER',1),(15,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjcmlzdGhpYW4ubWFydGluZXoiLCJpYXQiOjE2ODM5MTQyODYsImV4cCI6MTY4MzkxNTcyNn0.Nj-q23V5m6S8SB2QTrZBaayRGqJgCR5orpOmbX3_Keo','BEARER',3),(16,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjcmlzdGhpYW4ubWFydGluZXoiLCJpYXQiOjE2ODM5MTQ5NjIsImV4cCI6MTY4MzkxNjQwMn0.mHVUpDv9qst3qeB5xjf9ParR8gqC89ZGtwpEEk5fJ2w','BEARER',3),(17,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjcmlzdGhpYW4ubWFydGluZXoiLCJpYXQiOjE2ODQxNzI0MTYsImV4cCI6MTY4NDE3Mzg1Nn0.-WmZRSVU3Rz4qQH2Nd-CV-HvRofj2K4TaG5hT7IQqmo','BEARER',3),(18,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjcmlzdGhpYW4ubWFydGluZXoiLCJpYXQiOjE2ODQxNzI2MDQsImV4cCI6MTY4NDE3NDA0NH0.PkoMfCzFwbR5ks_CLo2U47Jn5Bk8kFaYW906dCbL4LY','BEARER',3),(19,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjcmlzdGhpYW4ubWFydGluZXoiLCJpYXQiOjE2ODQxNzMwMDgsImV4cCI6MTY4NDE3NDQ0OH0.CZnsJKgxffND3SIWp2zhFWkuNk4w9LvFiMV4A5fOUBI','BEARER',3),(20,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjcmlzdGhpYW4ubWFydGluZXoiLCJpYXQiOjE2ODQxNzMxNDMsImV4cCI6MTY4NDE3NDU4M30.bhI_BQ-3Sop7wkuxn-5cDwbgN8R0oLF_hvgMB0GueCY','BEARER',3),(21,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjcmlzdGhpYW4ubWFydGluZXoiLCJpYXQiOjE2ODQxNzMyMjMsImV4cCI6MTY4NDE3NDY2M30.wioie8fUx6rQ6s5zKIuGAK3n3d_epZvrnyNNPHB8Rj8','BEARER',3),(22,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjcmlzdGhpYW4ubWFydGluZXoiLCJpYXQiOjE2ODQxNzMzNTYsImV4cCI6MTY4NDE3NDc5Nn0.0x4L-_kmdYEuym7H56rskNpu8TVGJ2t9Uis1gySvtko','BEARER',3),(23,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjcmlzdGhpYW4ubWFydGluZXoiLCJpYXQiOjE2ODQxNzg4MjMsImV4cCI6MTY4NDE4MDI2M30.f0IKrKJ9S11ANNb8CExbWfusdqhcWJwuAtRchWnUZJw','BEARER',3),(24,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjcmlzdGhpYW4ubWFydGluZXoiLCJpYXQiOjE2ODQxODAxNTEsImV4cCI6MTY4NDE4MTU5MX0.offFqf3qjemVVHY9N3xcqkIyqhvPDvPBgrpv-0eL694','BEARER',3),(25,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjcmlzdGhpYW4ubWFydGluZXoiLCJpYXQiOjE2ODQxODA1NTksImV4cCI6MTY4NDE4MTk5OX0.CaAZOT_X-QPftuHL3CJYDLpxMr7kZRU0wDUvWbJbsBw','BEARER',3),(26,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjcmlzdGhpYW4ubWFydGluZXoiLCJpYXQiOjE2ODQxODM4MzIsImV4cCI6MTY4NDE4NTI3Mn0.3QoMXO_C164Xys4PcmMg8trCjcgxsfscaWELqg3aAaQ','BEARER',3),(27,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjcmlzdGhpYW4ubWFydGluZXoiLCJpYXQiOjE2ODQxODQwNjIsImV4cCI6MTY4NDE4NTUwMn0.0urS9vSxSdxi5WetdX4OlfBysg6l2jTd5dvSXJuJd-Q','BEARER',3),(28,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjcmlzdGhpYW4ubWFydGluZXoiLCJpYXQiOjE2ODQxODQxNzYsImV4cCI6MTY4NDE4NTYxNn0.nw5UDkkCXj3onaiXHwcAFWrwzHAyCIUhMLtH_welpLQ','BEARER',3),(29,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjcmlzdGhpYW4ubWFydGluZXoiLCJpYXQiOjE2ODQxODQ1NDMsImV4cCI6MTY4NDE4NTk4M30.64vWkx8QfP-UOxAesAZjJMpTYmcvykbqUVmwpNwB8n8','BEARER',3),(30,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjcmlzdGhpYW4ubWFydGluZXoiLCJpYXQiOjE2ODQxODQ4MTMsImV4cCI6MTY4NDE4NjI1M30.5DpvUO8xeOJksix0TsB--c39UWdin44am2-mnVQD5io','BEARER',3),(31,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjcmlzdGhpYW4ubWFydGluZXoiLCJpYXQiOjE2ODQxODUxODcsImV4cCI6MTY4NDE4NjYyN30.pS-nEPfMpFbINNWtyjBDIEXcvYKbZPdviqC5EZCje1g','BEARER',3),(32,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjcmlzdGhpYW4ubWFydGluZXoiLCJpYXQiOjE2ODQxODUyMDgsImV4cCI6MTY4NDE4NjY0OH0.x2vg8Cq0I1IpVmwhgPf-OSdLwqa3ldsCbcayA81PvPM','BEARER',3),(33,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjcmlzdGhpYW4ubWFydGluZXoiLCJpYXQiOjE2ODQxODUzMTcsImV4cCI6MTY4NDE4Njc1N30.0Kci1plyi3UI5EN_sDDz38HoIdSHXHgIeUOmg6mw5hg','BEARER',3),(34,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjcmlzdGhpYW4ubWFydGluZXoiLCJpYXQiOjE2ODQxODU5MjAsImV4cCI6MTY4NDE4NzM2MH0.83eXAIUgZ51nr8h4rJO9RmY1WpoPfkTWKV9OcJ2p6pQ','BEARER',3),(35,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjcmlzdGhpYW4ubWFydGluZXoiLCJpYXQiOjE2ODQxODY0MzAsImV4cCI6MTY4NDE4Nzg3MH0.93aSrl4VxtxRddJUzrdVz9enJnnh4OMoCT-7ldHBaTo','BEARER',3),(36,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjcmlzdGhpYW4ubWFydGluZXoiLCJpYXQiOjE2ODQxODczMDUsImV4cCI6MTY4NDE4ODc0NX0.vjaqA16vEedbgH-HtAFG0ObZH0rIYe9q2MyhkxtRXIU','BEARER',3),(37,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjcmlzdGhpYW4ubWFydGluZXoiLCJpYXQiOjE2ODQxODc1NTQsImV4cCI6MTY4NDE4ODk5NH0.zg18ybyrjiA4kHm7tPb-wJbrv0ru0hwUuD7TI-xOdGo','BEARER',3),(38,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjcmlzdGhpYW4ubWFydGluZXoiLCJpYXQiOjE2ODQyNTU4OTYsImV4cCI6MTY4NDI1NzMzNn0.5SiD_aLqWPMeURJOCtG_TxyXLYiCWZ7onhSJ8M-fytU','BEARER',3),(39,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjcmlzdGhpYW4ubWFydGluZXoiLCJpYXQiOjE2ODQyNTgxODMsImV4cCI6MTY4NDI1OTYyM30.bCovLz-IymOtRAefKiSVUmuxudV8D3_7z80mX7QzNqs','BEARER',3),(40,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjcmlzdGhpYW4ubWFydGluZXoiLCJpYXQiOjE2ODQyNTg3ODgsImV4cCI6MTY4NDI2MDIyOH0.lE3-biMRaLApHZZB7b2RHxHQghlkSwAv1PQJ4zi4Los','BEARER',3),(41,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjcmlzdGhpYW4ubWFydGluZXoiLCJpYXQiOjE2ODQyNTg4NDEsImV4cCI6MTY4NDI2MDI4MX0.W1ZPAxZzM6lUV4KUEf1O9MRDEGL0E81JkGVPljSSZy0','BEARER',3),(42,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjcmlzdGhpYW4ubWFydGluZXoiLCJpYXQiOjE2ODQyNzM5MDQsImV4cCI6MTY4NDI3NTM0NH0.6vb2H0233eeC-IEY2D4Rt71WQIaSM2A1PjdYCbbldgE','BEARER',3),(43,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjcmlzdGhpYW4ubWFydGluZXoiLCJpYXQiOjE2ODQyNzQxMjIsImV4cCI6MTY4NDI3NTU2Mn0.oNRI4GtonVN-XDFJXLWdlWlVg_kWIv_hBQ8thTicFQQ','BEARER',3),(44,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjcmlzdGhpYW4ubWFydGluZXoiLCJpYXQiOjE2ODQyNzQ0MTAsImV4cCI6MTY4NDI3NTg1MH0.qk90AZsBkeHBG1H4PgGj8IVxjePfSlqdiN8JtaVyAiQ','BEARER',3),(45,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjcmlzdGhpYW4ubWFydGluZXoiLCJpYXQiOjE2ODQyNzUxMTYsImV4cCI6MTY4NDI3NjU1Nn0.iEkBvQkEguIcH76akRODKAU_kVtPr9sqRzO88FuIlYI','BEARER',3),(46,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjcmlzdGhpYW4ubWFydGluZXoiLCJpYXQiOjE2ODQyNzU3MjcsImV4cCI6MTY4NDI3NzE2N30.-hgZyrft-J9tf7Q2KZN7s9tvx4sv7de-SQozpc8Gu2s','BEARER',3),(47,_binary '\0',_binary '\0','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjcmlzdGhpYW4ubWFydGluZXoiLCJpYXQiOjE2ODQyNzU5OTEsImV4cCI6MTY4NDI3NzQzMX0.mrwVgRxwCQCoNgJHTrLyN9T85KHv82kjhHyOAkBP9yw','BEARER',3),(48,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbl9zeXN0ZW0iLCJpYXQiOjE2ODQzNDgyNDMsImV4cCI6MTY4NDM0OTY4M30.CzhAS_S8q1qVnY5HSkh_vnEjgffW9NHhSwTue-I5cXA','BEARER',1),(49,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbl9zeXN0ZW0iLCJpYXQiOjE2ODQzNDg0MzEsImV4cCI6MTY4NDM0OTg3MX0.o2hcxJ7ZehlnBAL5YUM4d_CwWyvXSb7ersyjesXz30c','BEARER',1),(50,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbl9zeXN0ZW0iLCJpYXQiOjE2ODQzNjQxOTMsImV4cCI6MTY4NDM2NTYzM30.kpn4_D3DzD-q7FAht5tHpMntEqgjzyuTjRI_4MaIsrA','BEARER',1),(51,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbl9zeXN0ZW0iLCJpYXQiOjE2ODQzNjQ0MTIsImV4cCI6MTY4NDM2NTg1Mn0.z0XM2w5vcUGGKfG9uBVUZXxW9BQI_9PRcZ9zhnB6TAg','BEARER',1),(52,_binary '',_binary '','ae209302-49a8-4211-a299-4f4226a1d069','RESET_TOKEN',2),(53,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYXJpYS5sb3BleiIsImlhdCI6MTY4NDM2NDUxOSwiZXhwIjoxNjg0MzY1OTU5fQ.eVjT7MtbNF6XgyCDuHIRUIk5Q91ABuzNAptRghrTL6A','BEARER',2),(54,_binary '\0',_binary '\0','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYXJpYS5sb3BleiIsImlhdCI6MTY4NDM2NDU1MCwiZXhwIjoxNjg0MzY1OTkwfQ.PR1VdC0jnBITYKxyHVOOIOSB0ONAhAQs6NKokWjOoDg','BEARER',2),(55,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbl9zeXN0ZW0iLCJpYXQiOjE2ODQzNjY1ODQsImV4cCI6MTY4NDM2ODAyNH0.z6iS-bB1W4FpPqxpUEVIPWQGASHww-B3Nl9fKbgywnw','BEARER',1),(56,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbl9zeXN0ZW0iLCJpYXQiOjE2ODQzNjcwNDcsImV4cCI6MTY4NDM2ODQ4N30.vAJkaNplTb1Iqa2TehMu6EOpiX3ibE8GGHlcH66-Zts','BEARER',1),(59,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbl9zeXN0ZW0iLCJpYXQiOjE2ODQzNjgyMjYsImV4cCI6MTY4NDM2OTY2Nn0.TtrHixNXBbqcxFjtL6QAyWiOdWXpJBPyEl4n4efn5kQ','BEARER',1),(63,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbl9zeXN0ZW0iLCJpYXQiOjE2ODQzNjg3MzIsImV4cCI6MTY4NDM3MDE3Mn0.bvq9qyyWJJA5WWtSOWwxgQBIZj4h8HjQfOll6mx3MY4','BEARER',1),(69,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbl9zeXN0ZW0iLCJpYXQiOjE2ODQzNjk1MjgsImV4cCI6MTY4NDM3MDk2OH0.GPqWTrgMQvDzIaEtw8PMsAE_slcSOzARcknIx8P5FSU','BEARER',1),(72,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbl9zeXN0ZW0iLCJpYXQiOjE2ODQzNjk2MzYsImV4cCI6MTY4NDM3MTA3Nn0.0srydcyY84RSNtE7LArea1hu8serBgjfHhmk1aGWx2c','BEARER',1),(73,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbl9zeXN0ZW0iLCJpYXQiOjE2ODQzNjk2NDIsImV4cCI6MTY4NDM3MTA4Mn0.p2r1t0p5ZWo05UoRhfGhFo8k1hFFDtxrBa1nSYbFP5Y','BEARER',1),(76,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbl9zeXN0ZW0iLCJpYXQiOjE2ODQzNzMyNTMsImV4cCI6MTY4NDM3NDY5M30.RIXL_S_fPQxtg7J-umRSBIr95LBktlSoNSHjBsPNP8I','BEARER',1),(77,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbl9zeXN0ZW0iLCJpYXQiOjE2ODQzNzMzMzksImV4cCI6MTY4NDM3NDc3OX0.qihBPozTPL3UpOOJ9nIOtAUmPSAaWGJ1mVDHI5cd0rw','BEARER',1),(80,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbl9zeXN0ZW0iLCJpYXQiOjE2ODQzNzM5OTQsImV4cCI6MTY4NDM3NTQzNH0._mwFHLW9lxkDnGp4caRLy9SSOCKMB-AUvNrb0p_tWi8','BEARER',1),(81,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbl9zeXN0ZW0iLCJpYXQiOjE2ODQzNzQ0MjIsImV4cCI6MTY4NDM3NTg2Mn0.efd7OZaMztV3jXPf5D9PPepUE8VytVgzhHQzrlGsYKg','BEARER',1),(82,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtb3JnYW5fZnJlZW1hbiIsImlhdCI6MTY4NDM3NDUxOSwiZXhwIjoxNjg0Mzc1OTU5fQ.yGn-CRqs0sF8qG7CEQQVToHPCwn5L2CbA70lYjngNH8','BEARER',13),(83,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtb3JnYW5fZnJlZW1hbiIsImlhdCI6MTY4NDM3NDU4OSwiZXhwIjoxNjg0Mzc2MDI5fQ.l4pmR3nZAxJ5YxmKSTtMBETWKWcQAy2kY8_ZO_g0qJA','BEARER',13),(84,_binary '\0',_binary '\0','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbl9zeXN0ZW0iLCJpYXQiOjE2ODQzNzQ2MDksImV4cCI6MTY4NDM3NjA0OX0.zTlrhJ7v9Z8OPcVp0lGwpBEVdE6pv3q9lGhXXaHHjz0','BEARER',1),(85,_binary '',_binary '','d1ae4658-4fc9-4ef5-8e42-8d554df7c2d4','RESET_TOKEN',13),(86,_binary '\0',_binary '\0','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtb3JnYW5fZnJlZW1hbiIsImlhdCI6MTY4NDM3NDY2NywiZXhwIjoxNjg0Mzc2MTA3fQ.NhlY9X1YktJpYyYEOp381wR_EZys9FMVfruYnBmzjvY','BEARER',13);
/*!40000 ALTER TABLE `token` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-18 10:05:55
