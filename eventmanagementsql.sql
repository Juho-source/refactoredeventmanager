-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               11.7.2-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             12.10.0.7000
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for eventmanagement
CREATE DATABASE IF NOT EXISTS `eventmanagement` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_uca1400_ai_ci */;
USE `eventmanagement`;

-- Dumping structure for table eventmanagement.attendance
CREATE TABLE IF NOT EXISTS `attendance` (
  `attendanceid` int(11) NOT NULL AUTO_INCREMENT,
  `EventId` int(11) NOT NULL,
  `UserId` int(11) NOT NULL,
  PRIMARY KEY (`attendanceid`),
  KEY `EventId` (`EventId`),
  KEY `UserId` (`UserId`),
  CONSTRAINT `FKc0dhmodtc2c23usnfrrc03hdk` FOREIGN KEY (`EventId`) REFERENCES `event` (`eventid`),
  CONSTRAINT `FKtajpmxntt2g3j6496h4glhg3i` FOREIGN KEY (`UserId`) REFERENCES `user` (`UserID`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table eventmanagement.event
CREATE TABLE IF NOT EXISTS `event` (
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
  `language` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`eventid`),
  KEY `FKicjes6nxn9jsgam061mcstoun` (`creatorid`),
  CONSTRAINT `FKicjes6nxn9jsgam061mcstoun` FOREIGN KEY (`creatorid`) REFERENCES `user` (`UserID`)
) ENGINE=InnoDB AUTO_INCREMENT=116 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table eventmanagement.user
CREATE TABLE IF NOT EXISTS `user` (
  `FirstName` varchar(255) NOT NULL,
  `LastName` varchar(255) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `PhoneNumber` varchar(255) DEFAULT NULL,
  `UserID` int(11) NOT NULL AUTO_INCREMENT,
  `IsTeacher` tinyint(1) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`UserID`)
) ENGINE=InnoDB AUTO_INCREMENT=233 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Data exporting was unselected.

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
