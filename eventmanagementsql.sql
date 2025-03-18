-- MariaDB dump 10.19  Distrib 10.11.6-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: eventmanagementtest
-- ------------------------------------------------------
-- Server version	10.11.6-MariaDB-0+deb12u1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `attendance`
--

DROP TABLE IF EXISTS `attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attendance` (
  `attendanceid` int(11) NOT NULL AUTO_INCREMENT,
  `EventId` int(11) NOT NULL,
  `UserId` int(11) NOT NULL,
  PRIMARY KEY (`attendanceid`),
  KEY `EventId` (`EventId`),
  KEY `UserId` (`UserId`),
  CONSTRAINT `FKc0dhmodtc2c23usnfrrc03hdk` FOREIGN KEY (`EventId`) REFERENCES `event` (`eventid`),
  CONSTRAINT `FKtajpmxntt2g3j6496h4glhg3i` FOREIGN KEY (`UserId`) REFERENCES `user` (`UserID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendance`
--

LOCK TABLES `attendance` WRITE;
/*!40000 ALTER TABLE `attendance` DISABLE KEYS */;
/*!40000 ALTER TABLE `attendance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event` (
  `Date` date NOT NULL,
  `startTime` time(6) NOT NULL,
  `endTime` time(6) NOT NULL,
  `name` varchar(255) NOT NULL,
  `eventid` int(11) NOT NULL AUTO_INCREMENT,
  `location` varchar(255) NOT NULL,
  `category` varchar(255) DEFAULT NULL,
  `AttQuantity` int(11) NOT NULL,
  `MaxAttendance` int(11) NOT NULL,
  `creatorid` int(11) NOT NULL,
  `description` varchar(255) NOT NULL,
  PRIMARY KEY (`eventid`),
  KEY `FKicjes6nxn9jsgam061mcstoun` (`creatorid`),
  CONSTRAINT `FKicjes6nxn9jsgam061mcstoun` FOREIGN KEY (`creatorid`) REFERENCES `user` (`UserID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event`
--

LOCK TABLES `event` WRITE;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
INSERT INTO `event` VALUES
('2025-03-12','10:00:00.000000','14:00:00.000000','Spring Festival',1,'Central Park','Festival',100,500,20,'A fun-filled spring celebration with food, music, and games!'),
('2025-03-12','12:00:00.000000','13:00:00.000000','Cool AI',2,'Myllypuro',NULL,0,0,20,'Testing');
/*!40000 ALTER TABLE `event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `FirstName` varchar(255) NOT NULL,
  `LastName` varchar(255) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `PhoneNumber` varchar(255) DEFAULT NULL,
  `UserID` int(11) NOT NULL AUTO_INCREMENT,
  `IsTeacher` tinyint(1) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`UserID`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES
('John','Doe','john.doe@example.com','1234567890',17,0,'johndoe','securePassword'),
('Test','User','testuser@example.com','1234567890',18,0,'testuser','$2a$12$YYZgzboF35ib9Na3uExcJuDSnX/l1yg1sVmvFrlat8SEWPGl7lW1W'),
('Juho','User','testuser@example.com','1234567890',19,0,'JuhoS','$2a$12$wp5xnzXzr4ZpTSz63PhYEefy4XmNfLFedNBisuNZiZQSmaZrX4DIu'),
('Juho','Simpanen','juho.simpanen@metropolia.fi','434563567',20,1,'JuhooS','$2a$12$rKYOGpkAqMeUq/1iTWFViedzWxhi9RR/cWEZrCHGgl3nR70K6r/.S');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-12 13:14:56
