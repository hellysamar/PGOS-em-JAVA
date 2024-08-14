-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: dbpgos
-- ------------------------------------------------------
-- Server version	8.0.36

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
-- Table structure for table `tblos`
--

DROP TABLE IF EXISTS `tblos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblos` (
  `os` int NOT NULL AUTO_INCREMENT,
  `dataOS` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `equipamento` varchar(150) NOT NULL,
  `defeito` varchar(150) NOT NULL,
  `servico` varchar(150) DEFAULT NULL,
  `tecnico` varchar(30) DEFAULT NULL,
  `valor` decimal(10,2) DEFAULT NULL,
  `idCliente` int NOT NULL,
  PRIMARY KEY (`os`),
  KEY `idCliente` (`idCliente`),
  CONSTRAINT `tblos_ibfk_1` FOREIGN KEY (`idCliente`) REFERENCES `tblclientes` (`idCliente`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblos`
--

LOCK TABLES `tblos` WRITE;
/*!40000 ALTER TABLE `tblos` DISABLE KEYS */;
INSERT INTO `tblos` VALUES (2,'2024-03-05 17:19:04','notebook dell','apagou, não liga','troca de cabo','helly',150.25,1),(3,'2024-08-12 20:58:50','macbook pro','não liga o brilho no inicio de seção','atualização de Kext','helly',50.25,1),(6,'2024-08-12 21:02:43','macbook air','trackpad não funciona Trackpad','substituição de trackpad','no lugar de helly',280.00,2),(7,'2024-08-13 19:29:02','Pixelbook','minutenção','substituição de pelicula','tecnico',75.00,3);
/*!40000 ALTER TABLE `tblos` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-08-13 16:30:18
