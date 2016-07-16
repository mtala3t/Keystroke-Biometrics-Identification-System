-- phpMyAdmin SQL Dump
-- version 2.9.1.1
-- http://www.phpmyadmin.net
-- 
-- Host: localhost
-- Generation Time: Jun 27, 2009 at 03:53 PM
-- Server version: 5.0.27
-- PHP Version: 5.2.0
-- 
-- Database: `keystroke`
-- 

-- --------------------------------------------------------

-- 
-- Table structure for table `signature_profile`
-- 

CREATE TABLE `signature_profile` (
  `ID` int(11) NOT NULL auto_increment,
  `USER_ID` int(11) NOT NULL,
  `DIGRAPH` varchar(10) NOT NULL,
  `LATENCY_MEAN` double NOT NULL,
  `SUM_OF_X` double NOT NULL,
  `SUM_OF_SQUARED_X` double NOT NULL,
  `TYPE` varchar(20) NOT NULL,
  `TS` datetime NOT NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- 
-- Dumping data for table `signature_profile`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `users`
-- 

CREATE TABLE `users` (
  `ID` int(11) NOT NULL auto_increment,
  `USERNAME` varchar(50) NOT NULL,
  `PASSWORD` varchar(50) NOT NULL,
  `TS` datetime NOT NULL,
  `login_no` int(11) NOT NULL default '10',
  `successful_login_num` int(11) NOT NULL default '10',
  `username_login_no` int(11) NOT NULL default '10',
  `password_login_no` int(11) NOT NULL default '10',
  `phrase_login_no` int(11) NOT NULL default '10',
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- 
-- Dumping data for table `users`
-- 

