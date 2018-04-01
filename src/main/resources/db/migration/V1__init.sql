-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.20 - MySQL Community Server (GPL)
-- Server OS:                    Linux
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for basket_io
CREATE DATABASE IF NOT EXISTS `basket_io` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_turkish_ci */;
USE `basket_io`;

-- Dumping structure for table basket_io.campaigns
CREATE TABLE IF NOT EXISTS `campaigns` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8_turkish_ci NOT NULL,
  `target_id` int(11) NOT NULL,
  `target_type` varchar(255) COLLATE utf8_turkish_ci NOT NULL,
  `discount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKr3tqn2klaprx104dr16oi4ygk` (`discount_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

-- Data exporting was unselected.
-- Dumping structure for table basket_io.categories
CREATE TABLE IF NOT EXISTS `categories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_turkish_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

-- Data exporting was unselected.
-- Dumping structure for table basket_io.discounts
CREATE TABLE IF NOT EXISTS `discounts` (
  `discount_type` varchar(31) COLLATE utf8_turkish_ci NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `discount_limit` double DEFAULT NULL,
  `discount_rate` double DEFAULT NULL,
  `type` varchar(255) COLLATE utf8_turkish_ci DEFAULT NULL,
  `discount_price` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

-- Data exporting was unselected.
-- Dumping structure for table basket_io.products
CREATE TABLE IF NOT EXISTS `products` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_turkish_ci NOT NULL,
  `price` double NOT NULL,
  `category_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKog2rp4qthbtt2lfyhfo32lsw9` (`category_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

-- Data exporting was unselected.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
