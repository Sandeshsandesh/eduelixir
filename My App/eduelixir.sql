-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 15, 2017 at 09:56 AM
-- Server version: 5.7.14
-- PHP Version: 5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `eduelixir`
--

-- --------------------------------------------------------

--
-- Table structure for table `edu_class`
--

CREATE TABLE `edu_class` (
  `class_id` int(10) UNSIGNED NOT NULL,
  `class` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `edu_class`
--

INSERT INTO `edu_class` (`class_id`, `class`) VALUES
(1, '1st'),
(2, '2nd'),
(3, '3rd'),
(4, '4th'),
(5, '5th'),
(6, '6th'),
(7, '7th'),
(8, '8th'),
(9, '9th'),
(10, '10th'),
(16, '11th'),
(17, '12th');

-- --------------------------------------------------------

--
-- Table structure for table `edu_class_subjects`
--

CREATE TABLE `edu_class_subjects` (
  `class_id` int(10) UNSIGNED NOT NULL,
  `subject_id` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `edu_class_subjects`
--

INSERT INTO `edu_class_subjects` (`class_id`, `subject_id`) VALUES
(3, 84),
(2, 85),
(2, 86),
(1, 87),
(1, 88),
(1, 90),
(1, 89);

-- --------------------------------------------------------

--
-- Table structure for table `edu_notification`
--

CREATE TABLE `edu_notification` (
  `not_id` int(6) NOT NULL,
  `usn` varchar(45) CHARACTER SET utf8 NOT NULL DEFAULT '0',
  `title` varchar(100) DEFAULT NULL,
  `message` varchar(400) DEFAULT NULL,
  `date` varchar(35) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `edu_notification`
--

INSERT INTO `edu_notification` (`not_id`, `usn`, `title`, `message`, `date`) VALUES
(26, '0', 'Title', 'Message', '12th February 2017 01:36 AM'),
(27, 'adhasdha', 'Title', 'Message', '12th February 2017 01:37 AM'),
(28, '0', 'Title1', 'Message1', '12th February 2017 01:52 AM'),
(29, 'adhasdha', 'Good Morning', 'Your Ward is absent for the second period', '12th February 2017 02:30 AM'),
(30, '0', 'Siddesh', 'Hi.bro', '2nd July 2017 12:42 AM'),
(31, '0', 'Siddesh', 'Hi.bro', '2nd July 2017 12:43 AM'),
(32, '0', '', '', '16th August 2017 12:35 AM'),
(33, '0', 'Android Application Development', 'robocraze', '22nd August 2017 11:14 AM'),
(34, '0', 'Android Application Development', 'sasas', '22nd August 2017 11:14 AM'),
(35, 'adhasdha', 'Nishanth', 'IES officer', '2nd September 2017 03:02 PM'),
(36, 'adhasdha', 'Pareekshith', 'The ring', '2nd September 2017 03:03 PM'),
(37, 'adhasdha', 'Pareekshith', 'The ring  fk;gmfg gfg gj gg g rg kr grgr grg rgoogr ggnrgnrgmr g nrgnmrmg l grgmrgprg gkmr', '2nd September 2017 03:04 PM'),
(38, '0', 'NAAC', 'Hello', '13th September 2017 11:15 AM'),
(39, '0', 'NAAC', 'Hello', '13th September 2017 11:16 AM'),
(40, '0', 'NAAC', 'Hello', '13th September 2017 11:16 AM'),
(41, '0', 'NAAC', 'Hello', '13th September 2017 11:19 AM'),
(42, '0', 'Siddesh', 'Abhishek', '14th September 2017 10:11 AM'),
(43, '0', 'Siddesh', 'Abhishek', '14th September 2017 10:12 AM'),
(44, '0', 'Siddesh', 'Abhishek', '14th September 2017 10:13 AM'),
(45, '0', 'Siddesh', 'Abhishek', '14th September 2017 10:13 AM'),
(46, '0', 'Siddesh', 'Abhishek', '14th September 2017 10:14 AM'),
(47, '0', 'Siddesh', 'Abhishek', '14th September 2017 10:15 AM'),
(48, '0', 'MEETING', 'hi', '14th September 2017 10:32 AM'),
(49, '0', 'MEETING', 'hi', '14th September 2017 10:33 AM'),
(50, '0', 'MEETING', 'hi', '14th September 2017 10:33 AM'),
(51, '0', 'MEETING', 'hi we have scheduled on 12th', '14th September 2017 11:18 AM'),
(52, '0', 'MEETING', 'hi we have scheduled on 12th', '14th September 2017 12:10 PM'),
(53, '0', 'MEETING', 'hi we have scheduled on 12th', '14th September 2017 12:17 PM'),
(54, '0', 'MEETING', 'hi we have scheduled on 12th', '14th September 2017 12:26 PM'),
(55, '0', 'MEETING', 'hi we have scheduled on 12th', '14th September 2017 12:26 PM'),
(56, '0', 'MEETING', 'hi we have scheduled on 12th', '14th September 2017 12:26 PM'),
(57, '0', 'MEETING', 'hi we have scheduled on 12th', '14th September 2017 12:27 PM'),
(58, '0', 'MEETING', 'hi we have scheduled on 12th', '14th September 2017 01:53 PM'),
(59, '0', 'MEETING', 'IES officer', '14th September 2017 02:40 PM'),
(60, '0', 'MEETING', 'hi we have scheduled on 12th', '14th September 2017 02:47 PM'),
(61, '0', 'Android Application Development', 'Message', '15th September 2017 09:10 AM'),
(62, '0', 'Android Application Development', 'Message', '15th September 2017 09:12 AM'),
(63, '0', 'NAAC', 'HEARTLY WELCOME!!!', '15th September 2017 09:13 AM'),
(64, '0', 'NAAC', 'HEARTLY WELCOME!!!', '15th September 2017 09:13 AM'),
(65, '0', 'NAAC', 'HEARTLY WELCOME!!!', '15th September 2017 09:13 AM');

-- --------------------------------------------------------

--
-- Table structure for table `edu_notification_token`
--

CREATE TABLE `edu_notification_token` (
  `stu_id` int(10) UNSIGNED NOT NULL,
  `token` varchar(400) CHARACTER SET latin1 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `edu_organizations`
--

CREATE TABLE `edu_organizations` (
  `ID` int(10) UNSIGNED NOT NULL,
  `ORG_TYPE` varchar(100) NOT NULL,
  `ORG_NAME` varchar(300) NOT NULL,
  `ORG_EMAIL` varchar(100) NOT NULL,
  `ORG_USERNAME` varchar(100) NOT NULL,
  `ORG_PASSWORD` varchar(100) NOT NULL,
  `ORG_LOGO_PATH` varchar(200) DEFAULT NULL,
  `ORG_STATUS` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `edu_organizations`
--

INSERT INTO `edu_organizations` (`ID`, `ORG_TYPE`, `ORG_NAME`, `ORG_EMAIL`, `ORG_USERNAME`, `ORG_PASSWORD`, `ORG_LOGO_PATH`, `ORG_STATUS`) VALUES
(1, 'School', 'abc', 'abc@gmail.com', 'superuser', 'superuser', NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `edu_organization_users`
--

CREATE TABLE `edu_organization_users` (
  `org_user_id` int(10) UNSIGNED NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `created_date` datetime NOT NULL,
  `active` tinyint(1) NOT NULL,
  `org_id` int(10) UNSIGNED NOT NULL,
  `email_id` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `edu_organization_users`
--

INSERT INTO `edu_organization_users` (`org_user_id`, `username`, `password`, `created_date`, `active`, `org_id`, `email_id`) VALUES
(1, 'amit', '$2a$04$CO93CT2ObgMiSnMAWwoBkeFObJlMYi/wzzOnPlsTP44r7qVq0Jln2', '2015-11-14 09:35:51', 1, 1, 'amithsrikanth@yahoo.com');

-- --------------------------------------------------------

--
-- Table structure for table `edu_organization_user_roles`
--

CREATE TABLE `edu_organization_user_roles` (
  `role_id` int(10) UNSIGNED NOT NULL,
  `role` varchar(45) NOT NULL,
  `org_user_id` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `edu_organization_user_roles`
--

INSERT INTO `edu_organization_user_roles` (`role_id`, `role`, `org_user_id`) VALUES
(1, 'ROLE_MGMT', 1);

-- --------------------------------------------------------

--
-- Table structure for table `edu_section`
--

CREATE TABLE `edu_section` (
  `sec_id` int(10) UNSIGNED NOT NULL,
  `section` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `edu_section`
--

INSERT INTO `edu_section` (`sec_id`, `section`) VALUES
(1, 'A'),
(2, 'B'),
(3, 'C'),
(4, 'D'),
(5, 'E'),
(6, 'F'),
(7, 'G'),
(8, 'H'),
(9, 'I');

-- --------------------------------------------------------

--
-- Table structure for table `edu_staff`
--

CREATE TABLE `edu_staff` (
  `staff_id` int(10) UNSIGNED NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `middle_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `staff_type` varchar(45) NOT NULL,
  `address` varchar(300) NOT NULL,
  `city` varchar(45) NOT NULL,
  `state` varchar(45) NOT NULL,
  `zipcode` int(20) UNSIGNED NOT NULL,
  `mobile` varchar(45) NOT NULL,
  `doj` datetime NOT NULL,
  `country` varchar(45) NOT NULL,
  `emp_id` varchar(45) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `dob` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `edu_staff`
--

INSERT INTO `edu_staff` (`staff_id`, `first_name`, `middle_name`, `last_name`, `staff_type`, `address`, `city`, `state`, `zipcode`, `mobile`, `doj`, `country`, `emp_id`, `username`, `password`, `email`, `dob`) VALUES
(1, 'amit', 'B', 'S', 'teaching staff', 'adsadas', 'Bangalore', 'Karnataka', 560060, '9880514248', '2016-09-15 00:00:00', 'India', '001', 'amithsrikanth92@gmail.com', '9880514248', 'amithsrikanth92@gmail.com', '1992-04-05 00:00:00'),
(2, 'Shashi', 'P', 'Prasad', 'teaching staff', 'abcd', 'bangalore', 'Karnataka', 560050, '9880514248', '2016-10-05 00:00:00', 'India', '123', 'shashi.cse@gmail.com', '9880514248', 'shashi.cse@gmail.com', '1992-02-10 00:00:00'),
(3, 'Sandesh', '', 'S', 'teaching staff', 'abcd', 'bangalore', 'Karnataka', 560050, '7676847838', '2016-12-28 00:00:00', 'India', '1234', 'sandesh.sign@gmail.com', '7676847838', 'sandesh.sign@gmail.com', '1995-10-23 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `edu_students`
--

CREATE TABLE `edu_students` (
  `stud_id` int(10) UNSIGNED NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `dob` datetime NOT NULL,
  `guardian_name` varchar(200) NOT NULL,
  `guardian_occupation` varchar(200) DEFAULT NULL,
  `address` varchar(400) NOT NULL,
  `city` varchar(100) NOT NULL,
  `state` varchar(100) NOT NULL,
  `zipcode` int(10) UNSIGNED NOT NULL,
  `country` varchar(100) NOT NULL,
  `guardian_landline` varchar(100) DEFAULT NULL,
  `guardian_mobile` varchar(100) NOT NULL,
  `doa` datetime NOT NULL,
  `class_id` int(10) UNSIGNED DEFAULT NULL,
  `sec_id` int(10) UNSIGNED DEFAULT NULL,
  `usn` varchar(45) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `edu_students`
--

INSERT INTO `edu_students` (`stud_id`, `first_name`, `last_name`, `dob`, `guardian_name`, `guardian_occupation`, `address`, `city`, `state`, `zipcode`, `country`, `guardian_landline`, `guardian_mobile`, `doa`, `class_id`, `sec_id`, `usn`) VALUES
(7, 'siddesh', 'g', '2008-06-06 00:00:00', 'Ganesh', 'alksakl', '#493/68, 2nd floor,3rd main road', 'Bangalore', 'Karnataka', 560050, 'India', '87328913', '81723894', '2016-06-12 00:00:00', 1, 1, 'abhishek'),
(8, 'Mamu', 'Ajax', '2000-11-10 00:00:00', 'Ganesh', 'Engineer', '#493/68, 2nd floor,3rd main road', 'Bangalore', 'Karnataka', 560050, 'India', '8892996778', '8892996778', '2016-06-19 00:00:00', NULL, 2, '1AH10cs009'),
(9, 'Mohan', 'Ramu', '2002-06-12 00:00:00', 'Ramu', 'Pilot', '#493/68, 2nd floor,3rd main road', 'Bangalore', 'Karnataka', 560050, 'India', '', '9030406012', '2016-06-19 00:00:00', NULL, 3, '1ah10cs001'),
(12, 'Puneeth', 'S', '2015-06-12 00:00:00', 'Sreedhar', 'Architect', '#493/68, 2nd floor,3rd main road', 'Bangalore', 'Karnataka', 560050, 'India', NULL, '9030406012', '2016-06-20 00:00:00', NULL, 1, '1ah10cs09'),
(13, 'adarsh', 'B S', '2006-02-01 00:00:00', 'Shivanand', 'Software', '#493/68, 2nd floor,3rd main road', 'Bangalore', 'Karnataka', 560050, 'India', NULL, '9030406012', '2016-06-23 00:00:00', NULL, 1, '1ah10cs004'),
(14, 'Pundri', 'S', '2003-10-02 00:00:00', 'Shivanand', 'Civil Engineer', '#493/68, 2nd floor,3rd main road', 'Bangalore', 'Karnataka', 560050, 'India', '0802853550', '9030406012', '2016-06-25 00:00:00', 1, 1, '1ah10cs010');

-- --------------------------------------------------------

--
-- Table structure for table `edu_subjects`
--

CREATE TABLE `edu_subjects` (
  `subject_id` int(10) UNSIGNED NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `edu_subjects`
--

INSERT INTO `edu_subjects` (`subject_id`, `name`) VALUES
(86, 'English'),
(89, 'History'),
(84, 'Math'),
(88, 'Maths'),
(85, 'Sanskrit'),
(87, 'Science'),
(90, 'Social'),
(83, 'Social-Science');

-- --------------------------------------------------------

--
-- Table structure for table `edu_superadmin`
--

CREATE TABLE `edu_superadmin` (
  `ID` int(10) UNSIGNED NOT NULL,
  `username` varchar(200) NOT NULL,
  `password` varchar(200) NOT NULL,
  `created_t` datetime NOT NULL,
  `active` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `edu_superadmin`
--

INSERT INTO `edu_superadmin` (`ID`, `username`, `password`, `created_t`, `active`) VALUES
(1, 'amit', '$2a$04$CO93CT2ObgMiSnMAWwoBkeFObJlMYi/wzzOnPlsTP44r7qVq0Jln2', '2015-11-14 09:35:51', 1);

-- --------------------------------------------------------

--
-- Table structure for table `edu_superadmin_roles`
--

CREATE TABLE `edu_superadmin_roles` (
  `roles_id` int(10) UNSIGNED NOT NULL,
  `sa_id` int(10) UNSIGNED NOT NULL,
  `role` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `edu_superadmin_roles`
--

INSERT INTO `edu_superadmin_roles` (`roles_id`, `sa_id`, `role`) VALUES
(1, 1, 'ROLE_ADMIN');

-- --------------------------------------------------------

--
-- Table structure for table `edu_timetable`
--

CREATE TABLE `edu_timetable` (
  `tt_id` int(10) UNSIGNED NOT NULL,
  `class_id` int(10) UNSIGNED DEFAULT NULL,
  `sec_id` int(10) UNSIGNED DEFAULT NULL,
  `staff_id` int(10) UNSIGNED DEFAULT NULL,
  `subject_id` int(10) UNSIGNED DEFAULT NULL,
  `day` varchar(10) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `start_hour` int(10) UNSIGNED DEFAULT NULL,
  `start_minute` int(10) UNSIGNED DEFAULT NULL,
  `start_seconds` int(10) UNSIGNED DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `end_hour` int(10) UNSIGNED DEFAULT NULL,
  `end_minute` int(10) UNSIGNED DEFAULT NULL,
  `end_seconds` int(10) UNSIGNED DEFAULT NULL,
  `is_repetitive` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `edu_timetable`
--

INSERT INTO `edu_timetable` (`tt_id`, `class_id`, `sec_id`, `staff_id`, `subject_id`, `day`, `start_date`, `start_hour`, `start_minute`, `start_seconds`, `end_date`, `end_hour`, `end_minute`, `end_seconds`, `is_repetitive`) VALUES
(1, 1, 1, 1, 83, 'mon', '2017-01-20 00:00:00', 8, 30, 0, '2017-01-17 00:00:00', 9, 30, 0, 1),
(2, 1, 1, 3, 84, 'mon', '2017-01-20 00:00:00', 9, 30, 0, '2017-01-17 00:00:00', 10, 30, 0, 1),
(3, 1, 1, 2, 85, 'tue', '2017-01-20 00:00:00', 8, 30, 0, '2017-01-17 00:00:00', 9, 30, 0, 1),
(4, 1, 1, 1, 86, 'tue', '2017-01-20 00:00:00', 9, 30, 0, '2017-01-17 00:00:00', 10, 30, 0, 1),
(5, 1, 1, 2, 85, 'mon', '2017-01-20 00:00:00', 11, 30, 0, '2017-01-17 00:00:00', 12, 30, 0, 1),
(6, 1, 1, 1, 83, 'mon', '2017-01-20 00:00:00', 10, 30, 0, '2017-01-17 00:00:00', 11, 30, 0, 1),
(7, 1, 1, 1, 90, 'mon', '2017-01-20 00:00:00', 13, 30, 0, '2017-01-17 00:00:00', 14, 30, 0, 1),
(8, 1, 1, 1, 89, 'mon', '2017-01-20 00:00:00', 14, 30, 0, '2017-01-17 00:00:00', 15, 30, 0, 1),
(9, 1, 1, 3, 88, 'mon', '2017-01-20 00:00:00', 15, 30, 0, '2017-01-17 00:00:00', 16, 30, 0, 1),
(10, 1, 1, 2, 85, 'tue', '2017-01-20 00:00:00', 11, 30, 0, '2017-01-17 00:00:00', 12, 30, 0, 1),
(11, 1, 1, 1, 83, 'tue', '2017-01-20 00:00:00', 10, 30, 0, '2017-01-17 00:00:00', 11, 30, 0, 1),
(12, 1, 1, 1, 90, 'tue', '2017-01-20 00:00:00', 13, 30, 0, '2017-01-17 00:00:00', 14, 30, 0, 1),
(13, 1, 1, 1, 89, 'tue', '2017-01-20 00:00:00', 14, 30, 0, '2017-01-17 00:00:00', 15, 30, 0, 1),
(14, 1, 1, 3, 88, 'tue', '2017-01-20 00:00:00', 15, 30, 0, '2017-01-17 00:00:00', 16, 30, 0, 1),
(15, 1, 1, 3, 83, 'wed', '2017-01-20 00:00:00', 8, 30, 0, '2017-01-17 00:00:00', 9, 30, 0, 1),
(16, 1, 1, 3, 84, 'wed', '2017-01-20 00:00:00', 9, 30, 0, '2017-01-17 00:00:00', 10, 30, 0, 1),
(17, 1, 1, 2, 85, 'wed', '2017-01-20 00:00:00', 11, 30, 0, '2017-01-17 00:00:00', 12, 30, 0, 1),
(18, 1, 1, 1, 83, 'wed', '2017-01-20 00:00:00', 10, 30, 0, '2017-01-17 00:00:00', 11, 30, 0, 1),
(19, 1, 1, 1, 90, 'wed', '2017-01-20 00:00:00', 13, 30, 0, '2017-01-17 00:00:00', 14, 30, 0, 1),
(20, 1, 1, 3, 89, 'wed', '2017-01-20 00:00:00', 14, 30, 0, '2017-01-17 00:00:00', 15, 30, 0, 1),
(21, 1, 1, 3, 88, 'wed', '2017-01-20 00:00:00', 15, 30, 0, '2017-01-17 00:00:00', 16, 30, 0, 1),
(22, 1, 1, 3, 83, 'thu', '2017-01-20 00:00:00', 8, 30, 0, '2017-01-17 00:00:00', 9, 30, 0, 1),
(23, 1, 1, 3, 84, 'thu', '2017-01-20 00:00:00', 9, 30, 0, '2017-01-17 00:00:00', 10, 30, 0, 1),
(24, 1, 1, 2, 85, 'thu', '2017-01-20 00:00:00', 11, 30, 0, '2017-01-17 00:00:00', 12, 30, 0, 1),
(25, 1, 1, 1, 83, 'thu', '2017-01-20 00:00:00', 10, 30, 0, '2017-01-17 00:00:00', 11, 30, 0, 1),
(26, 1, 1, 1, 90, 'thu', '2017-01-20 00:00:00', 13, 30, 0, '2017-01-17 00:00:00', 14, 30, 0, 1),
(27, 1, 1, 3, 89, 'thu', '2017-01-20 00:00:00', 14, 30, 0, '2017-01-17 00:00:00', 15, 30, 0, 1),
(28, 1, 1, 3, 88, 'thu', '2017-01-20 00:00:00', 15, 30, 0, '2017-01-17 00:00:00', 16, 30, 0, 1),
(29, 1, 1, 3, 83, 'fri', '2017-01-20 00:00:00', 8, 30, 0, '2017-01-17 00:00:00', 9, 30, 0, 1),
(30, 1, 1, 3, 84, 'fri', '2017-01-20 00:00:00', 9, 30, 0, '2017-01-17 00:00:00', 10, 30, 0, 1),
(31, 1, 1, 2, 85, 'fri', '2017-01-20 00:00:00', 11, 30, 0, '2017-01-17 00:00:00', 12, 30, 0, 1),
(32, 1, 1, 1, 83, 'fri', '2017-01-20 00:00:00', 10, 30, 0, '2017-01-17 00:00:00', 11, 30, 0, 1),
(33, 1, 1, 1, 90, 'fri', '2017-01-20 00:00:00', 13, 30, 0, '2017-01-17 00:00:00', 14, 30, 0, 1),
(34, 1, 1, 3, 89, 'fri', '2017-01-20 00:00:00', 14, 30, 0, '2017-01-17 00:00:00', 15, 30, 0, 1),
(35, 1, 1, 3, 88, 'fri', '2017-01-20 00:00:00', 15, 30, 0, '2017-01-17 00:00:00', 16, 30, 0, 1),
(36, 1, 1, 3, 83, 'sat', '2017-01-20 00:00:00', 8, 30, 0, '2017-01-17 00:00:00', 9, 30, 0, 1),
(37, 1, 1, 3, 84, 'sat', '2017-01-20 00:00:00', 9, 30, 0, '2017-01-17 00:00:00', 10, 30, 0, 1),
(38, 1, 1, 2, 85, 'sat', '2017-01-20 00:00:00', 11, 30, 0, '2017-01-17 00:00:00', 12, 30, 0, 1),
(39, 1, 1, 1, 83, 'sat', '2017-01-20 00:00:00', 10, 30, 0, '2017-01-17 00:00:00', 11, 30, 0, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `edu_class`
--
ALTER TABLE `edu_class`
  ADD PRIMARY KEY (`class_id`);

--
-- Indexes for table `edu_class_subjects`
--
ALTER TABLE `edu_class_subjects`
  ADD KEY `FK_edu_class_subjects_class` (`class_id`),
  ADD KEY `FK_edu_class_subjects_subject` (`subject_id`);

--
-- Indexes for table `edu_notification`
--
ALTER TABLE `edu_notification`
  ADD PRIMARY KEY (`not_id`);

--
-- Indexes for table `edu_notification_token`
--
ALTER TABLE `edu_notification_token`
  ADD KEY `FK_students_usn` (`stu_id`);

--
-- Indexes for table `edu_organizations`
--
ALTER TABLE `edu_organizations`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `edu_organization_users`
--
ALTER TABLE `edu_organization_users`
  ADD PRIMARY KEY (`org_user_id`),
  ADD KEY `FK_edu_organization_users_org` (`org_id`);

--
-- Indexes for table `edu_organization_user_roles`
--
ALTER TABLE `edu_organization_user_roles`
  ADD PRIMARY KEY (`role_id`),
  ADD KEY `FK_edu_organization_roles_orguser` (`org_user_id`);

--
-- Indexes for table `edu_section`
--
ALTER TABLE `edu_section`
  ADD PRIMARY KEY (`sec_id`);

--
-- Indexes for table `edu_staff`
--
ALTER TABLE `edu_staff`
  ADD PRIMARY KEY (`staff_id`),
  ADD UNIQUE KEY `EMP_ID_UNIQ` (`emp_id`);

--
-- Indexes for table `edu_students`
--
ALTER TABLE `edu_students`
  ADD PRIMARY KEY (`stud_id`),
  ADD UNIQUE KEY `usn_UNIQUE` (`usn`),
  ADD KEY `FK_students_class` (`class_id`),
  ADD KEY `FK_students_section` (`sec_id`);

--
-- Indexes for table `edu_subjects`
--
ALTER TABLE `edu_subjects`
  ADD PRIMARY KEY (`subject_id`),
  ADD UNIQUE KEY `name_unique` (`name`);

--
-- Indexes for table `edu_superadmin`
--
ALTER TABLE `edu_superadmin`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `edu_superadmin_roles`
--
ALTER TABLE `edu_superadmin_roles`
  ADD PRIMARY KEY (`roles_id`),
  ADD KEY `FK_edu_superadmin_roles` (`sa_id`);

--
-- Indexes for table `edu_timetable`
--
ALTER TABLE `edu_timetable`
  ADD PRIMARY KEY (`tt_id`),
  ADD KEY `FK_class_timetable` (`class_id`),
  ADD KEY `FK_staff_timetable` (`staff_id`),
  ADD KEY `FK_subject_timetable` (`subject_id`),
  ADD KEY `FK_section_timetable_idx` (`sec_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `edu_class`
--
ALTER TABLE `edu_class`
  MODIFY `class_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
--
-- AUTO_INCREMENT for table `edu_notification`
--
ALTER TABLE `edu_notification`
  MODIFY `not_id` int(6) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=66;
--
-- AUTO_INCREMENT for table `edu_organizations`
--
ALTER TABLE `edu_organizations`
  MODIFY `ID` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `edu_organization_users`
--
ALTER TABLE `edu_organization_users`
  MODIFY `org_user_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `edu_organization_user_roles`
--
ALTER TABLE `edu_organization_user_roles`
  MODIFY `role_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `edu_section`
--
ALTER TABLE `edu_section`
  MODIFY `sec_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT for table `edu_staff`
--
ALTER TABLE `edu_staff`
  MODIFY `staff_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `edu_students`
--
ALTER TABLE `edu_students`
  MODIFY `stud_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
--
-- AUTO_INCREMENT for table `edu_subjects`
--
ALTER TABLE `edu_subjects`
  MODIFY `subject_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=91;
--
-- AUTO_INCREMENT for table `edu_superadmin`
--
ALTER TABLE `edu_superadmin`
  MODIFY `ID` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `edu_superadmin_roles`
--
ALTER TABLE `edu_superadmin_roles`
  MODIFY `roles_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `edu_timetable`
--
ALTER TABLE `edu_timetable`
  MODIFY `tt_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `edu_class_subjects`
--
ALTER TABLE `edu_class_subjects`
  ADD CONSTRAINT `FK_edu_class_subjects_class` FOREIGN KEY (`class_id`) REFERENCES `edu_class` (`class_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_edu_class_subjects_subject` FOREIGN KEY (`subject_id`) REFERENCES `edu_subjects` (`subject_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `edu_notification_token`
--
ALTER TABLE `edu_notification_token`
  ADD CONSTRAINT `FK_students_usn` FOREIGN KEY (`stu_id`) REFERENCES `edu_students` (`stud_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `edu_organization_users`
--
ALTER TABLE `edu_organization_users`
  ADD CONSTRAINT `FK_edu_organization_users_org` FOREIGN KEY (`org_id`) REFERENCES `edu_organizations` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `edu_organization_user_roles`
--
ALTER TABLE `edu_organization_user_roles`
  ADD CONSTRAINT `FK_edu_organization_roles_orguser` FOREIGN KEY (`org_user_id`) REFERENCES `edu_organization_users` (`org_user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `edu_students`
--
ALTER TABLE `edu_students`
  ADD CONSTRAINT `FK_students_class` FOREIGN KEY (`class_id`) REFERENCES `edu_class` (`class_id`) ON DELETE SET NULL ON UPDATE SET NULL,
  ADD CONSTRAINT `FK_students_section` FOREIGN KEY (`sec_id`) REFERENCES `edu_section` (`sec_id`) ON DELETE SET NULL ON UPDATE SET NULL;

--
-- Constraints for table `edu_superadmin_roles`
--
ALTER TABLE `edu_superadmin_roles`
  ADD CONSTRAINT `FK_edu_superadmin_roles` FOREIGN KEY (`sa_id`) REFERENCES `edu_superadmin` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `edu_timetable`
--
ALTER TABLE `edu_timetable`
  ADD CONSTRAINT `FK_class_timetable` FOREIGN KEY (`class_id`) REFERENCES `edu_class` (`class_id`) ON DELETE SET NULL ON UPDATE SET NULL,
  ADD CONSTRAINT `FK_section_timetable` FOREIGN KEY (`sec_id`) REFERENCES `edu_section` (`sec_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_staff_timetable` FOREIGN KEY (`staff_id`) REFERENCES `edu_staff` (`staff_id`) ON DELETE SET NULL ON UPDATE SET NULL,
  ADD CONSTRAINT `FK_subject_timetable` FOREIGN KEY (`subject_id`) REFERENCES `edu_subjects` (`subject_id`) ON DELETE SET NULL ON UPDATE SET NULL;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
