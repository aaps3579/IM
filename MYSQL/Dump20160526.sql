CREATE DATABASE  IF NOT EXISTS `im` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `im`;
-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: im
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
-- Table structure for table `user_messages`
--

DROP TABLE IF EXISTS `user_messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_messages` (
  `message` varchar(1000) DEFAULT NULL,
  `message_from` varchar(45) DEFAULT NULL,
  `message_to` varchar(45) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` varchar(45) DEFAULT 'unread',
  PRIMARY KEY (`ID`),
  KEY `user_msg_fk_idx` (`message_from`,`message_to`),
  KEY `fk2_idx` (`message_to`),
  CONSTRAINT `fk1` FOREIGN KEY (`message_from`) REFERENCES `users` (`phone`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk2` FOREIGN KEY (`message_to`) REFERENCES `users` (`phone`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=471 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_messages`
--

LOCK TABLES `user_messages` WRITE;
/*!40000 ALTER TABLE `user_messages` DISABLE KEYS */;
INSERT INTO `user_messages` VALUES ('hello','9803117003','9888565506','text',451,'2016-05-17 14:03:52','unread'),('hi','9803117003','9888565506','text',452,'2016-05-18 15:35:37','unread'),('hello bro how are you','9417781959','9803117003','text',453,'2016-05-18 15:39:27','read'),('\\uD83D\\uDCAA','9417781959','9803117003','text',454,'2016-05-18 15:39:33','read'),('i m good ! u say','9803117003','9417781959','text',455,'2016-05-18 15:41:23','read'),('u say','9803117003','9417781959','text',457,'2016-05-18 15:42:56','read'),('hello','9855290139','9417781959','text',458,'2016-05-18 16:10:37','read'),(':-)','9855290139','9417781959','text',459,'2016-05-18 16:10:48','read'),('hi','9417781959','9855290139','text',460,'2016-05-18 16:12:21','read'),('\\uD83D\\uDE14','9417781959','9855290139','text',461,'2016-05-18 16:13:09','read'),('audio','9417781959','9855290139','audio',462,'2016-05-18 16:13:28','read'),('Sample second Synopsis (android).doc','9417781959','9855290139','file',463,'2016-05-18 16:14:36','read'),('hi','9855290139','9417781959','text',464,'2016-05-18 16:19:20','read'),(' hello','9417781959','9855290139','text',465,'2016-05-18 16:19:33','read'),('\\uD83D\\uDE43','9803117003','9417781959','text',467,'2016-05-25 16:36:11','unread'),('\\uD83D\\uDE43','9417781959','9803117003','text',468,'2016-05-25 16:37:03','read'),('\\uD83D\\uDE43','9417781959','9803117003','text',469,'2016-05-25 16:46:45','read'),('audio','9803117003','9417781959','audio',470,'2016-05-25 16:47:00','unread');
/*!40000 ALTER TABLE `user_messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `phone` varchar(45) NOT NULL,
  `username` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `gender` varchar(45) DEFAULT NULL,
  `status` varchar(150) DEFAULT NULL,
  `verification_code` varchar(45) DEFAULT NULL,
  `verification_status` varchar(45) DEFAULT 'Pending',
  PRIMARY KEY (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('9417781959','Aaps','amanpreet.3579@gmail.com','Male','Loving IM! ','2275','Approved'),('9463618456','mak','iak@gkaik.com','Male','Hello','7908','Approved'),('9803117003','guneet','guneetsingh.honey@gmail.com','Male','Im loving IM....!','2076','Approved'),('9855290139','Inderjeet Kaur','indergandhi71@gmail.com','Female','Loving IM! ','5397','Approved'),('9888565506','rachit','rachitkhanna1994@gmail.com ','Male','Loving IM! ','3677','Approved'),('9996663322','sammy','sharma.samriti412@gmail.com','Female','Hello','6013','Approved');
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

-- Dump completed on 2016-05-26  7:00:57
