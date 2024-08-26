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
  `tipo` varchar(10) NOT NULL,
  `situacaoOS` varchar(30) DEFAULT NULL,
  `equipamento` varchar(150) NOT NULL,
  `defeito` varchar(150) NOT NULL,
  `servico` varchar(150) DEFAULT NULL,
  `tecnico` varchar(30) DEFAULT NULL,
  `valor` decimal(10,2) DEFAULT NULL,
  `idCliente` int NOT NULL,
  PRIMARY KEY (`os`),
  KEY `idCliente` (`idCliente`),
  CONSTRAINT `tblos_ibfk_1` FOREIGN KEY (`idCliente`) REFERENCES `tblclientes` (`idCliente`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblos`
--

LOCK TABLES `tblos` WRITE;
/*!40000 ALTER TABLE `tblos` DISABLE KEYS */;
INSERT INTO `tblos` VALUES (2,'2024-03-05 17:19:04','OS','Entregue na Assistência','notebook dell','apagou, não liga','troca de cabo','helly',150.25,1),(3,'2024-08-12 20:58:50','','','macbook pro','não liga o brilho no inicio de seção','atualização de Kext','helly',50.25,1),(6,'2024-08-12 21:02:43','','','macbook air','trackpad não funciona Trackpad','substituição de trackpad','no lugar de helly',280.00,2),(7,'2024-08-13 19:29:02','OC','Entregue na Assistência','Pixelbook Google','minutenção','substituição de pelicula','tecnico',75.00,3),(8,'2024-08-21 19:51:08','OS','Entregue na Assistência','macbook','tela preta','substituição de tela','Fulano Tecnico',90.00,4),(9,'2024-08-21 20:01:04','OC','Entregue na Assistência','Teclado','travamento nas teclas','limpreza de teclas','fulano tec',91.59,1),(10,'2024-08-21 20:02:42','OS','Entregue na Assistência','Mouse','cursor do mouse tremendo','Substituição de laser','fulano tec',40.90,1),(11,'2024-08-21 20:05:03','OS','Entregue na Assistência','Mouse','cursor do mouse tremendo','Substituição de laser','fulano tec',40.90,1),(12,'2024-08-21 20:15:22','OC','Entregue na Assistência','Orçamento','Orçamento','Orçamento','Orçamento',0.00,1),(13,'2024-08-21 20:15:51','OS','Entregue na Assistência','OS','OS','OS','OS',0.00,1),(17,'2024-08-22 13:11:06','OC','Orçamento REPROVADO','reprovado','reprovado','reprovado','reprovado',0.00,4),(18,'2024-08-22 14:58:54','OC','Entregue na Assistência','macbook air','trackpad não funciona Trackpad','substituição de trackpad','no lugar de helly',280.00,2),(19,'2024-08-23 13:34:20','OC','Aguardando Aprovação','reprovado','reprovado','reprovado','reprovado',0.00,4),(20,'2024-08-23 13:42:45','OC','Orçamento REPROVADO','teste s','test s','teste s','test',0.00,6),(22,'2024-08-23 13:44:46','OS','Pronto para Retirada','ATUALIZADA 22','ATUALIZADA 2','ATUALIZADA 22','Ataulizado Update',0.50,4),(23,'2024-08-23 13:45:12','OS','Aguardando Peças','De OC para OS','DATA 24/08/2024 10:45:12','de Aguardando Aprovação para Aguardando Peças','os 23',0.00,6),(24,'2024-08-23 19:43:40','OC','Entregue na Assistência','test','g','drg','',0.00,6),(25,'2024-08-25 17:39:18','OS','Entregue na Assistência','eq','def','ser','',0.00,7),(26,'2024-08-25 18:03:59','OC','Entregue na Assistência','Notebook Lenovo G4','Não liga','Troca de fonte','Leandro',125.00,4),(27,'2024-08-25 18:13:51','OC','Entregue na Assistência','e','e','','',0.00,2),(28,'2024-08-25 18:14:32','OC','Entregue na Assistência','t','t','','',0.00,3);
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

-- Dump completed on 2024-08-26 17:35:40
