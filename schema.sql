-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: May 18, 2024 at 08:20 PM
-- Server version: 8.0.36-0ubuntu0.22.04.1
-- PHP Version: 8.1.2-1ubuntu2.17

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `test_XML`
--

-- --------------------------------------------------------

--
-- Table structure for table `autres`
--

CREATE TABLE `autres` (
  `id_cv` int NOT NULL,
  `titre` varchar(30) NOT NULL,
  `comment` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `certifs`
--

CREATE TABLE `certifs` (
  `id_cv` int NOT NULL,
  `titre` varchar(32) NOT NULL,
  `datedeb` date NOT NULL,
  `datefin` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `cvs`
--

CREATE TABLE `cvs` (
  `id` int NOT NULL,
  `genre` char(4) NOT NULL,
  `nom` varchar(32) NOT NULL,
  `prenom` varchar(32) NOT NULL,
  `tel` varchar(17) DEFAULT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `statutObjectif` tinyint(1) NOT NULL,
  `contenuObjectif` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `details`
--

CREATE TABLE `details` (
  `id_cv` int NOT NULL,
  `datedeb` date NOT NULL,
  `datefin` date DEFAULT NULL,
  `titre` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `diplomes`
--

CREATE TABLE `diplomes` (
  `id_cv` int NOT NULL,
  `titre` varchar(255) NOT NULL,
  `date` date NOT NULL,
  `institut` varchar(32) DEFAULT NULL,
  `niveau` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `lvs`
--

CREATE TABLE `lvs` (
  `id_cv` int NOT NULL,
  `lang` varchar(30) NOT NULL,
  `cert` varchar(30) NOT NULL,
  `nivs` varchar(10) DEFAULT NULL,
  `nivi` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `autres`
--
ALTER TABLE `autres`
  ADD KEY `autres` (`id_cv`);

--
-- Indexes for table `certifs`
--
ALTER TABLE `certifs`
  ADD KEY `certifs` (`id_cv`);

--
-- Indexes for table `cvs`
--
ALTER TABLE `cvs`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `details`
--
ALTER TABLE `details`
  ADD KEY `details` (`id_cv`);

--
-- Indexes for table `diplomes`
--
ALTER TABLE `diplomes`
  ADD KEY `diplomes_ibfk_1` (`id_cv`);

--
-- Indexes for table `lvs`
--
ALTER TABLE `lvs`
  ADD KEY `lvs` (`id_cv`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cvs`
--
ALTER TABLE `cvs`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `autres`
--
ALTER TABLE `autres`
  ADD CONSTRAINT `autres_ibfk_1` FOREIGN KEY (`id_cv`) REFERENCES `cvs` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `certifs`
--
ALTER TABLE `certifs`
  ADD CONSTRAINT `certifs_ibfk_1` FOREIGN KEY (`id_cv`) REFERENCES `cvs` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `details`
--
ALTER TABLE `details`
  ADD CONSTRAINT `details_ibfk_1` FOREIGN KEY (`id_cv`) REFERENCES `cvs` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `diplomes`
--
ALTER TABLE `diplomes`
  ADD CONSTRAINT `diplomes_ibfk_1` FOREIGN KEY (`id_cv`) REFERENCES `cvs` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `lvs`
--
ALTER TABLE `lvs`
  ADD CONSTRAINT `lvs_ibfk_1` FOREIGN KEY (`id_cv`) REFERENCES `cvs` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
