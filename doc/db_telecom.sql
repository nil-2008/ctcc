/*
Navicat MySQL Data Transfer

Source Server         : 192.168.216.20
Source Server Version : 50173
Source Host           : 192.168.216.20:3306
Source Database       : db_telecom

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2017-10-31 16:11:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_call
-- ----------------------------
DROP TABLE IF EXISTS `tb_call`;
CREATE TABLE `tb_call` (
  `id_date_contact` varchar(255) NOT NULL,
  `id_date_dimension` int(11) NOT NULL,
  `id_contact` int(11) NOT NULL,
  `call_sum` int(11) NOT NULL,
  `call_duration_sum` int(11) NOT NULL,
  PRIMARY KEY (`id_date_contact`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_contacts
-- ----------------------------
DROP TABLE IF EXISTS `tb_contacts`;
CREATE TABLE `tb_contacts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `telephone` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_dimension_date
-- ----------------------------
DROP TABLE IF EXISTS `tb_dimension_date`;
CREATE TABLE `tb_dimension_date` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `year` int(11) NOT NULL,
  `month` int(11) NOT NULL,
  `day` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=263 DEFAULT CHARSET=utf8;