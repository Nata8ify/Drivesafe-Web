-- phpMyAdmin SQL Dump
-- version 4.6.4deb1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Apr 14, 2017 at 06:57 PM
-- Server version: 5.7.17-0ubuntu0.16.10.1
-- PHP Version: 7.0.15-0ubuntu0.16.10.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `drivesafe`
--

-- --------------------------------------------------------

--
-- Table structure for table `accident`
--

CREATE TABLE `accident` (
  `userId` bigint(13) NOT NULL,
  `date` date NOT NULL,
  `time` varchar(8) COLLATE utf8_unicode_ci NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `accCode` char(1) COLLATE utf8_unicode_ci NOT NULL,
  `forceDetect` double NOT NULL,
  `speedDetect` float NOT NULL,
  `accidentId` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `accident`
--

INSERT INTO `accident` (`userId`, `date`, `time`, `latitude`, `longitude`, `accCode`, `forceDetect`, `speedDetect`, `accidentId`) VALUES
(12, '2017-03-31', '16:54', 14.04884147644043, 99.90209197998047, 'A', 3, 0.37, 20),
(12, '2017-04-01', '09:05', 14.034825325012207, 99.8171615600586, 'A', 4, 24.97, 21),
(12, '2017-04-01', '09:09', 14.03840446472168, 99.79105377197266, 'A', 4, 9.31, 23),
(12, '2017-04-01', '09:16', 14.038091659545898, 99.79048156738281, 'A', 3, 10.25, 24),
(12, '2017-04-01', '09:27', 13.973596572875977, 99.7551498413086, 'A', 5, 0, 26),
(12, '2017-04-01', '14:58', 14.02253246307373, 99.90507507324219, 'A', 3, 0.63, 28),
(12, '2017-04-02', '23:07', 14.048803329467773, 99.90245819091797, 'A', 1, 0, 43),
(12, '2017-04-02', '23:10', 14.04879093170166, 99.90247344970703, 'A', 1, 0, 44),
(12, '2017-04-02', '23:14', 14.048791885375977, 99.9024887084961, 'A', 1, 0, 45),
(12, '2017-04-03', '19:15', 13.646944046020508, 100.48747253417969, 'A', 1, 0, 46),
(12, '2017-04-06', '22:57', 13.646977424621582, 100.48725128173828, 'A', 1, 0, 47),
(12, '2017-04-06', '22:58', 13.646977424621582, 100.48725128173828, 'A', 1, 0, 48),
(12, '2017-04-09', '08:51', 13.739468574523926, 100.51647186279297, 'A', 1, 0, 49),
(12, '2017-04-09', '11:02', 13.823482513427734, 100.05948638916016, 'A', 1, 0, 50),
(12, '2017-04-09', '11:13', 13.817828178405762, 100.06121063232422, 'A', 1, 10.54, 51),
(12, '2017-04-09', '11:59', 14.021477699279785, 99.99150085449219, 'A', 1, 1.1, 52),
(12, '2017-04-09', '19:16', 14.052087783813477, 99.9092025756836, 'A', 1, 0, 53),
(12, '2017-04-09', '19:17', 14.052087783813477, 99.9092025756836, 'A', 1, 0, 54),
(12, '2017-04-09', '19:17', 14.052087783813477, 99.9092025756836, 'A', 1, 0, 55),
(12, '2017-04-10', '19:35', 14.048741340637207, 99.90251922607422, 'A', 1, 0, 56),
(12, '2017-04-10', '19:43', 14.04874324798584, 99.90252685546875, 'A', 1, 0.24, 57);

-- --------------------------------------------------------

--
-- Table structure for table `profile`
--

CREATE TABLE `profile` (
  `userId` bigint(13) NOT NULL,
  `firstName` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `lastName` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `personalId` bigint(13) NOT NULL,
  `phone` varchar(12) COLLATE utf8_unicode_ci NOT NULL,
  `address1` text COLLATE utf8_unicode_ci NOT NULL,
  `address2` text COLLATE utf8_unicode_ci NOT NULL,
  `age` int(3) NOT NULL,
  `gender` char(1) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `profile`
--

INSERT INTO `profile` (`userId`, `firstName`, `lastName`, `personalId`, `phone`, `address1`, `address2`, `age`, `gender`) VALUES
(1, 'NATTAWUT', 'PICHAIWATTNPHON', 1730200262876, '+66845708981', '65/4 WARANON DORMITORY, SOI. PHUTTHABUCHA 44\r\nPHUTTHABUCHA ROAD, BANGMOD\r\nTHUNGKRU, Bangkok ,  10140\r\nThailand', '', 21, 'M'),
(10, 'ณัฐวุฒิ', 'พิชัยวัฒนพร', 1, '1', '65/4 หอพักวรานนท์ , ซอย พุทธบูชา 44 ถนนพุทธบูชา, บางมด, ทุ่งครุ, กกรุงเทพมหานคร 10140', '', 1, 'M'),
(11, 'Samuel L.', 'Jackson', 8562411747542, '866658888', 'ไม่เปิดเผย', 'ไม่เปิดเผย', 54, 'M'),
(12, 'Driver', 'Oliver', 54782522691, '66581124741', 'ไม่เปิดเผย', 'ไม่เปิดเผย', 32, 'M');

-- --------------------------------------------------------

--
-- Table structure for table `properties`
--

CREATE TABLE `properties` (
  `opLat` double DEFAULT NULL,
  `opLng` double DEFAULT NULL,
  `opBound` int(11) DEFAULT NULL,
  `userId` bigint(13) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `userId` bigint(13) NOT NULL,
  `username` varchar(13) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `userType` char(1) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`userId`, `username`, `password`, `userType`) VALUES
(1, 'root', '63a9f0ea7bb98050796b649e85481845', 'T'),
(10, 'user', 'ee11cbb19052e40b07aac0ca060c23ee', 'T'),
(11, 'userSamuel', '1a1dc91c907325c69271ddf0c944bc72', 'M'),
(12, 'driverUser', '322452ef0a3f1da917f4caf958cb8528', 'M');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `accident`
--
ALTER TABLE `accident`
  ADD PRIMARY KEY (`accidentId`),
  ADD KEY `userId` (`userId`);

--
-- Indexes for table `profile`
--
ALTER TABLE `profile`
  ADD PRIMARY KEY (`userId`);

--
-- Indexes for table `properties`
--
ALTER TABLE `properties`
  ADD KEY `userId` (`userId`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD KEY `userId` (`userId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `accident`
--
ALTER TABLE `accident`
  MODIFY `accidentId` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=58;
--
-- AUTO_INCREMENT for table `profile`
--
ALTER TABLE `profile`
  MODIFY `userId` bigint(13) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `accident`
--
ALTER TABLE `accident`
  ADD CONSTRAINT `accident_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `profile` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `properties`
--
ALTER TABLE `properties`
  ADD CONSTRAINT `properties_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `profile` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
