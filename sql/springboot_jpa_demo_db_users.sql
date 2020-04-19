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
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `create_user` bigint DEFAULT NULL,
  `is_delete` int DEFAULT NULL,
  `last_upd_date` datetime(6) DEFAULT NULL,
  `last_upd_user` bigint DEFAULT NULL,
  `account` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `passwd` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `real_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `salt` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `status` int DEFAULT NULL,
  `role_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_r2bdsy4yqtkpssr475ch00bdr` (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`id`, `create_date`, `create_user`, `is_delete`, `last_upd_date`, `last_upd_user`, `account`, `passwd`, `real_name`, `remark`, `salt`, `status`, `role_id`) VALUES (447726797187059712,'2020-04-19 19:46:26.914000',NULL,0,'2020-04-19 19:46:26.941000',NULL,'test01','d0cf3b1832cf50ad02b82719ee571b6310c754444f541019348039758d545a90','test01','test01备注信息','1587296786811',1,1);
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

-- Dump completed on 2020-04-19 21:46:16
