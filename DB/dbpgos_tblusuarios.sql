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
-- Table structure for table `tblusuarios`
--

DROP TABLE IF EXISTS `tblusuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblusuarios` (
  `idUser` int NOT NULL,
  `usuario` varchar(50) NOT NULL,
  `fone` varchar(15) DEFAULT NULL,
  `login` varchar(15) NOT NULL,
  `senha` varchar(15) NOT NULL,
  `perfil` varchar(20) NOT NULL,
  PRIMARY KEY (`idUser`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblusuarios`
--

LOCK TABLES `tblusuarios` WRITE;
/*!40000 ALTER TABLE `tblusuarios` DISABLE KEYS */;
INSERT INTO `tblusuarios` VALUES (1,'Hellysamar Araújo Silva','61 98284-4250','helly','12345','admin'),(2,'Ana Paula','61 88888-9999','ana','12345','admin'),(3,'Administrador','61 99999-9999','admin','admin','admin'),(4,'Luan Avelan','88 97777-8899','luangin','123456','user'),(5,'Terezinha Alves','61 96666-2586','tera','123456','user'),(6,'Fernando Vasconcelos','98 98255-5687','fer','vas','user'),(7,'Francine','61 98888-3456','fran','123456','user'),(8,'Juan Andrade','61 98888-3456','juan','juan','user'),(9,'Melissa Sanches','61 99988-2548','mel','issa','admin'),(10,'Miguel Sanches','61999882575','miguel','miguel','user'),(11,'Erick Jaquan','61 65654-4585','erick','erick','admin');
/*!40000 ALTER TABLE `tblusuarios` ENABLE KEYS */;
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
