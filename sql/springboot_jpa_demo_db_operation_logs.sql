-- MySQL dump 10.13  Distrib 8.0.19, for macos10.15 (x86_64)
--
-- Host: 127.0.0.1    Database: springboot_jpa_demo_db
-- ------------------------------------------------------
-- Server version	8.0.19

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
-- Table structure for table `operation_logs`
--

DROP TABLE IF EXISTS `operation_logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `operation_logs` (
  `id` bigint NOT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `create_user` bigint DEFAULT NULL,
  `is_delete` int DEFAULT NULL,
  `last_upd_date` datetime(6) DEFAULT NULL,
  `last_upd_user` bigint DEFAULT NULL,
  `account` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `func_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `ip` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL,
  `module` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `params` varchar(2000) COLLATE utf8mb4_bin DEFAULT NULL,
  `real_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `url` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operation_logs`
--

