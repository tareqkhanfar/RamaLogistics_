-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: ramaLogistic
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `ramaLogistic`
--

/*!40000 DROP DATABASE IF EXISTS `ramaLogistic`*/;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `ramaLogistic` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `ramaLogistic`;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `client` (
  `id_Client` int NOT NULL,
  `name_Client` varchar(50) NOT NULL,
  `address_Client` varchar(50) DEFAULT NULL,
  `phone` varchar(25) DEFAULT NULL,
  `telefx` varchar(25) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `img` longblob,
  `nameFile` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id_Client`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `creditnote`
--

DROP TABLE IF EXISTS `creditnote`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `creditnote` (
  `CreditNO` int NOT NULL AUTO_INCREMENT,
  `Year_` int DEFAULT NULL,
  `Message` text,
  `Date_` date DEFAULT NULL,
  PRIMARY KEY (`CreditNO`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `creditnote`
--

LOCK TABLES `creditnote` WRITE;
/*!40000 ALTER TABLE `creditnote` DISABLE KEYS */;
INSERT INTO `creditnote` VALUES (1,22,'wefwefewfwefصثبصثبصثب','2022-10-12');
/*!40000 ALTER TABLE `creditnote` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `creditnotedetails`
--

DROP TABLE IF EXISTS `creditnotedetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `creditnotedetails` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `CreditNO` int NOT NULL,
  `Description` varchar(100) NOT NULL,
  `Value_` varchar(50) NOT NULL,
  PRIMARY KEY (`ID`,`Description`,`Value_`,`CreditNO`),
  KEY `CreditNO` (`CreditNO`),
  CONSTRAINT `creditnotedetails_ibfk_1` FOREIGN KEY (`CreditNO`) REFERENCES `creditnote` (`CreditNO`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `creditnotedetails`
--

LOCK TABLES `creditnotedetails` WRITE;
/*!40000 ALTER TABLE `creditnotedetails` DISABLE KEYS */;
INSERT INTO `creditnotedetails` VALUES (60,1,'wefwe','صثبصثبصث');
/*!40000 ALTER TABLE `creditnotedetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `filetracker`
--

DROP TABLE IF EXISTS `filetracker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `filetracker` (
  `FileID` int NOT NULL,
  `id_client` int NOT NULL,
  `openDate` date DEFAULT NULL,
  `ArivalDate` date DEFAULT NULL,
  `FILEAMOUNT` varchar(20) DEFAULT NULL,
  `RELEASEDATE` date DEFAULT NULL,
  `BALANCE` varchar(30) DEFAULT NULL,
  `TRANSFER_DATE` date DEFAULT NULL,
  `TRANSFER_AMOUNT` varchar(30) DEFAULT NULL,
  `sellingValue` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`FileID`),
  KEY `id_client` (`id_client`),
  CONSTRAINT `filetracker_ibfk_1` FOREIGN KEY (`id_client`) REFERENCES `client` (`id_Client`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `filetracker`
--

LOCK TABLES `filetracker` WRITE;
/*!40000 ALTER TABLE `filetracker` DISABLE KEYS */;
/*!40000 ALTER TABLE `filetracker` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice`
--

DROP TABLE IF EXISTS `invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoice` (
  `inID` int NOT NULL AUTO_INCREMENT,
  `FileID` int NOT NULL,
  `fileName` varchar(60) DEFAULT NULL,
  `dataInvoice` longblob,
  PRIMARY KEY (`inID`),
  KEY `invoice_ibfk_1` (`FileID`),
  CONSTRAINT `invoice_ibfk_1` FOREIGN KEY (`FileID`) REFERENCES `filetracker` (`FileID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice`
--

LOCK TABLES `invoice` WRITE;
/*!40000 ALTER TABLE `invoice` DISABLE KEYS */;
/*!40000 ALTER TABLE `invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login`
--

DROP TABLE IF EXISTS `login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `login` (
  `username` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login`
--

LOCK TABLES `login` WRITE;
/*!40000 ALTER TABLE `login` DISABLE KEYS */;
INSERT INTO `login` VALUES ('','');
/*!40000 ALTER TABLE `login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `message` (
  `MessageID` int NOT NULL AUTO_INCREMENT,
  `Message` varchar(300) NOT NULL,
  `Sender` varchar(15) NOT NULL,
  `ReciverPhone` varchar(20) NOT NULL,
  `id_client` int NOT NULL,
  PRIMARY KEY (`MessageID`),
  KEY `id_client` (`id_client`),
  CONSTRAINT `message_ibfk_1` FOREIGN KEY (`id_client`) REFERENCES `client` (`id_Client`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paymentdetails`
--

DROP TABLE IF EXISTS `paymentdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `paymentdetails` (
  `PD` int NOT NULL AUTO_INCREMENT,
  `PaymentID` int NOT NULL,
  `discription` varchar(60) NOT NULL,
  `price` float NOT NULL,
  `amount` float NOT NULL,
  PRIMARY KEY (`PD`,`PaymentID`,`amount`,`price`,`discription`),
  KEY `PaymentID` (`PaymentID`),
  CONSTRAINT `paymentdetails_ibfk_1` FOREIGN KEY (`PaymentID`) REFERENCES `paymentrequest` (`PaymentID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paymentdetails`
--

LOCK TABLES `paymentdetails` WRITE;
/*!40000 ALTER TABLE `paymentdetails` DISABLE KEYS */;
/*!40000 ALTER TABLE `paymentdetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paymentrequest`
--

DROP TABLE IF EXISTS `paymentrequest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `paymentrequest` (
  `PaymentID` int NOT NULL AUTO_INCREMENT,
  `id_Client` int DEFAULT NULL,
  `PaymentDate` date NOT NULL,
  `TransportType` varchar(20) NOT NULL,
  `SupplierName` varchar(30) NOT NULL,
  `Carrier` varchar(20) NOT NULL,
  `MBL` varchar(20) NOT NULL,
  `HBL` varchar(20) NOT NULL,
  `DepDate` date NOT NULL,
  `ArrDate` date NOT NULL,
  `DischPort` varchar(30) NOT NULL,
  `LoadPort` varchar(30) NOT NULL,
  `ManifestNo` varchar(30) NOT NULL,
  `DescOfGoods` varchar(50) NOT NULL,
  `Incoterms` varchar(20) NOT NULL,
  `PackageType` varchar(30) NOT NULL,
  `Pcs` varchar(60) NOT NULL,
  `Weight` varchar(60) NOT NULL,
  `ChWeight` varchar(60) NOT NULL,
  `Volume` varchar(60) NOT NULL,
  `GoodsValue` varchar(50) NOT NULL,
  `Currency` varchar(50) NOT NULL,
  `tax` varchar(50) NOT NULL,
  `FileID` int DEFAULT NULL,
  `CurrencySymbole` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`PaymentID`),
  KEY `id_Client` (`id_Client`),
  KEY `FileID` (`FileID`),
  CONSTRAINT `paymentrequest_ibfk_1` FOREIGN KEY (`id_Client`) REFERENCES `client` (`id_Client`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `paymentrequest_ibfk_2` FOREIGN KEY (`FileID`) REFERENCES `filetracker` (`FileID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paymentrequest`
--

LOCK TABLES `paymentrequest` WRITE;
/*!40000 ALTER TABLE `paymentrequest` DISABLE KEYS */;
/*!40000 ALTER TABLE `paymentrequest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `receiptvoucher`
--

DROP TABLE IF EXISTS `receiptvoucher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receiptvoucher` (
  `RV` int NOT NULL AUTO_INCREMENT,
  `id_client` int NOT NULL,
  `sumOf` varchar(200) DEFAULT NULL,
  `accountOf` varchar(200) DEFAULT NULL,
  `Date_now` date DEFAULT NULL,
  `cash` varchar(5) DEFAULT NULL,
  `cheque` varchar(15) DEFAULT NULL,
  `total1` varchar(45) DEFAULT NULL,
  `total2` varchar(45) DEFAULT NULL,
  `total3` varchar(45) DEFAULT NULL,
  `total4` varchar(45) DEFAULT NULL,
  `date1` varchar(45) DEFAULT NULL,
  `date2` varchar(45) DEFAULT NULL,
  `date3` varchar(45) DEFAULT NULL,
  `date4` varchar(45) DEFAULT NULL,
  `bank1` varchar(45) DEFAULT NULL,
  `bank2` varchar(45) DEFAULT NULL,
  `bank3` varchar(45) DEFAULT NULL,
  `bank4` varchar(45) DEFAULT NULL,
  `chequeNO1` varchar(45) DEFAULT NULL,
  `chequeNO2` varchar(45) DEFAULT NULL,
  `chequeNO3` varchar(45) DEFAULT NULL,
  `chequeNO4` varchar(45) DEFAULT NULL,
  `nis` varchar(5) DEFAULT NULL,
  `dollar` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`RV`,`id_client`),
  KEY `receiptvoucher_ibfk_1` (`id_client`),
  CONSTRAINT `receiptvoucher_ibfk_1` FOREIGN KEY (`id_client`) REFERENCES `client` (`id_Client`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receiptvoucher`
--

LOCK TABLES `receiptvoucher` WRITE;
/*!40000 ALTER TABLE `receiptvoucher` DISABLE KEYS */;
/*!40000 ALTER TABLE `receiptvoucher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shipping`
--

DROP TABLE IF EXISTS `shipping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shipping` (
  `shID` int NOT NULL AUTO_INCREMENT,
  `FileID` int NOT NULL,
  `fileName` varchar(60) DEFAULT NULL,
  `dataShipping` longblob,
  PRIMARY KEY (`shID`),
  KEY `shipping_ibfk_1` (`FileID`),
  CONSTRAINT `shipping_ibfk_1` FOREIGN KEY (`FileID`) REFERENCES `filetracker` (`FileID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shipping`
--

LOCK TABLES `shipping` WRITE;
/*!40000 ALTER TABLE `shipping` DISABLE KEYS */;
/*!40000 ALTER TABLE `shipping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `statusfile`
--

DROP TABLE IF EXISTS `statusfile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `statusfile` (
  `status_` varchar(80) NOT NULL,
  `FileID` int DEFAULT NULL,
  `dateStatus` date NOT NULL,
  PRIMARY KEY (`status_`,`dateStatus`),
  KEY `FileID` (`FileID`),
  CONSTRAINT `statusfile_ibfk_1` FOREIGN KEY (`FileID`) REFERENCES `filetracker` (`FileID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `statusfile`
--

LOCK TABLES `statusfile` WRITE;
/*!40000 ALTER TABLE `statusfile` DISABLE KEYS */;
/*!40000 ALTER TABLE `statusfile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier` (
  `id_Supplier` int NOT NULL AUTO_INCREMENT,
  `name_supplier` varchar(45) DEFAULT NULL,
  `address_supplier` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `telefx` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `img` longblob,
  `nameFile` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_Supplier`)
) ENGINE=InnoDB AUTO_INCREMENT=124 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier`
--

LOCK TABLES `supplier` WRITE;
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` VALUES (123,'haefe','235','234','235','235',_binary 'This file was provided by: https://www.dll-files.com/\n\nIf you downloaded it from somewhere else, please let us know: info@dll-files.com\n\nDLL-Files.com is owned and operated by Tilf AB, Sweden. The collection of DLL files as a whole (falls under the “collection copyright” laws) are © Copyright Tilf AB\n\nThe individual DLL files are provided free of charge with the understanding that the user is familiar with their use.\n\nIf you need help installing the file, please see:\nhttps://www.dll-files.com/support/\nor ask your question in the forum:\nhttps://forum.dll-files.com/\n\nDISCLAIMER AND LIMITATION OF LIABILITY\n\nThe Following Refers to all Files with the Extension of \"dll\" or dlls compressed as \"zip\".\n\nAll files are provided on an as is basis. No guarantees or warranties are given or implied. Downloading files from this site is free of charge and the user assumes all risks of any damages that may occur, including but not limited to loss of data, damages to hardware, or loss of business profits. We do our best to ensure that all files are virus-free using available means. However, all files have not been tested for functionality or contamination. Many have been sent to us by visitors like yourself. Thus, we suggest that you do a virus scan using an up-to-date version of an anti-virus program before use. Please use at your own risk.\n','README.txt');
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `taxinvoice`
--

DROP TABLE IF EXISTS `taxinvoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `taxinvoice` (
  `TaxID` int NOT NULL,
  `FileID` int DEFAULT NULL,
  `declarationNo` varchar(60) DEFAULT NULL,
  `id_Client` int DEFAULT NULL,
  `vat` varchar(30) DEFAULT NULL,
  `Notes` text,
  `dateNow` date DEFAULT NULL,
  `currency` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`TaxID`),
  KEY `taxinvoice_ibfk_1` (`FileID`),
  KEY `taxinvoice_ibfk_2` (`id_Client`),
  CONSTRAINT `taxinvoice_ibfk_1` FOREIGN KEY (`FileID`) REFERENCES `filetracker` (`FileID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `taxinvoice_ibfk_2` FOREIGN KEY (`id_Client`) REFERENCES `client` (`id_Client`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `taxinvoice`
--

LOCK TABLES `taxinvoice` WRITE;
/*!40000 ALTER TABLE `taxinvoice` DISABLE KEYS */;
/*!40000 ALTER TABLE `taxinvoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `taxinvoicedetails`
--

DROP TABLE IF EXISTS `taxinvoicedetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `taxinvoicedetails` (
  `details_ID` int NOT NULL AUTO_INCREMENT,
  `TaxID` int NOT NULL,
  `description` varchar(60) NOT NULL,
  `AmountWithoutVat` float NOT NULL,
  `AmountDueVat` float NOT NULL,
  PRIMARY KEY (`details_ID`,`TaxID`,`description`),
  KEY `taxinvoicedetails_ibfk_1` (`TaxID`),
  CONSTRAINT `taxinvoicedetails_ibfk_1` FOREIGN KEY (`TaxID`) REFERENCES `taxinvoice` (`TaxID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `taxinvoicedetails`
--

LOCK TABLES `taxinvoicedetails` WRITE;
/*!40000 ALTER TABLE `taxinvoicedetails` DISABLE KEYS */;
/*!40000 ALTER TABLE `taxinvoicedetails` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-10-04 12:58:41
