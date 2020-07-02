-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: warehouse
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
-- Table structure for table `carparts`
--

DROP TABLE IF EXISTS `carparts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carparts` (
  `id_car_part` varchar(255) NOT NULL,
  `description` varchar(150) DEFAULT NULL,
  `is_new` bit(1) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `quantity_available` int DEFAULT NULL,
  `year` int DEFAULT NULL,
  `id_producer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_car_part`),
  KEY `FK2qu6hp6fsc12w7wk4936y0lq7` (`id_producer`),
  CONSTRAINT `FK2qu6hp6fsc12w7wk4936y0lq7` FOREIGN KEY (`id_producer`) REFERENCES `producers` (`id_producer`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carparts`
--

LOCK TABLES `carparts` WRITE;
/*!40000 ALTER TABLE `carparts` DISABLE KEYS */;
INSERT INTO `carparts` VALUES ('261e8789-f501-49a9-ab9f-e2c7ee1aa034','adasdas',_binary '\0','ProdEx',12,15,2020,'4f3b871b-5e4e-426d-aab7-24a36982ab8f'),('4049a1b0-2052-43bd-912a-8b9e9790f696','Anvelope Iarna MICHELIN Alpin 5 225/60 R16 102 H XL',_binary '','Anvelope Alpin',590,5,2020,'6f86d0c3-6f15-485b-b88a-6f07298dbd75'),('4341d477-e779-4e73-8163-4e1a48319370','5L, rezistent pana la -5 grade Celsius',_binary '','Lichid de parbriz',13.99,4,2017,'488f53eb-d494-4b52-b7fe-1d9026007f3f'),('70e7726c-5bec-45da-aefb-0335cfc94d4b','Energy Saver+ 195/65 R15 91H',_binary '\0','Cauciucuri all seasons',310,5,2017,'6f86d0c3-6f15-485b-b88a-6f07298dbd75'),('bc182841-ce36-4653-989e-ab33eb53ef73','some description',_binary '\0','AnotherExample',12,12,2020,'4f3b871b-5e4e-426d-aab7-24a36982ab8f');
/*!40000 ALTER TABLE `carparts` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-05-24 21:52:36
