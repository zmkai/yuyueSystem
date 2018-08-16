/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : yuyuesystem

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2018-07-13 11:06:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for equresource_table
-- ----------------------------
DROP TABLE IF EXISTS `equresource_table`;
CREATE TABLE `equresource_table` (
  `equ_code` varchar(10) NOT NULL,
  `resource_id` int(11) DEFAULT NULL,
  `labcode` varchar(5) DEFAULT NULL COMMENT '实验设备的存在该属性值，否则为空',
  `equ_name` varchar(20) NOT NULL,
  `user_count` int(11) NOT NULL COMMENT '该台设备可服务人数',
  `open_time` char(1) NOT NULL COMMENT '开放使用时间',
  PRIMARY KEY (`equ_code`),
  KEY `FK_Reference_4` (`resource_id`),
  KEY `FK_Reference_5` (`labcode`),
  CONSTRAINT `FK_Reference_4` FOREIGN KEY (`resource_id`) REFERENCES `types_table` (`resource_id`),
  CONSTRAINT `FK_Reference_5` FOREIGN KEY (`labcode`) REFERENCES `lab_table` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of equresource_table
-- ----------------------------
INSERT INTO `equresource_table` VALUES ('1', '2', null, '服务器', '100', '0');
INSERT INTO `equresource_table` VALUES ('2', '2', null, '健身器材', '10', '0');

-- ----------------------------
-- Table structure for lab_table
-- ----------------------------
DROP TABLE IF EXISTS `lab_table`;
CREATE TABLE `lab_table` (
  `code` varchar(5) NOT NULL,
  `labname` varchar(20) NOT NULL,
  `lablocation` varchar(50) NOT NULL,
  `labfunction` varchar(50) NOT NULL,
  `opentime` char(1) NOT NULL COMMENT '0代表全天开放，1代表不为全天开放,1为封闭阶段',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of lab_table
-- ----------------------------
INSERT INTO `lab_table` VALUES ('210', '计算机实验室210', '信电学院210', '计算机学生做实验', '0');
INSERT INTO `lab_table` VALUES ('213', '计算机实验室213', '信电学院213', '计算机系学生做实验', '0');

-- ----------------------------
-- Table structure for records_table
-- ----------------------------
DROP TABLE IF EXISTS `records_table`;
CREATE TABLE `records_table` (
  `id` int(11) NOT NULL,
  `equ_code` varchar(10) DEFAULT NULL,
  `account` varchar(20) DEFAULT NULL,
  `startTime` datetime NOT NULL,
  `endTime` datetime NOT NULL,
  `mark` char(1) NOT NULL COMMENT '0代表为审核通过，1代表审核通过',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_6` (`equ_code`),
  KEY `FK_Reference_7` (`account`),
  CONSTRAINT `FK_Reference_6` FOREIGN KEY (`equ_code`) REFERENCES `equresource_table` (`equ_code`),
  CONSTRAINT `FK_Reference_7` FOREIGN KEY (`account`) REFERENCES `user_table` (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of records_table
-- ----------------------------

-- ----------------------------
-- Table structure for types_table
-- ----------------------------
DROP TABLE IF EXISTS `types_table`;
CREATE TABLE `types_table` (
  `resource_id` int(11) NOT NULL,
  `resource_type` varchar(20) NOT NULL,
  PRIMARY KEY (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of types_table
-- ----------------------------
INSERT INTO `types_table` VALUES ('1', '实验室');
INSERT INTO `types_table` VALUES ('2', '大型设备');

-- ----------------------------
-- Table structure for user_table
-- ----------------------------
DROP TABLE IF EXISTS `user_table`;
CREATE TABLE `user_table` (
  `account` varchar(20) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `email` varchar(20) NOT NULL,
  `photo_path` varchar(200) NOT NULL,
  `mark` char(1) NOT NULL DEFAULT '0' COMMENT '1代表注册成功，0代表未成功',
  `identity` char(1) NOT NULL DEFAULT '1' COMMENT '0代表管理员，1代表普通用户',
  PRIMARY KEY (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表，包括正在注册待审核的用户，其中身份标记mark=0，代表用户已经通过审核注册，mark=1，代表用户尚未通过审核';

-- ----------------------------
-- Records of user_table
-- ----------------------------
INSERT INTO `user_table` VALUES ('111', '1111', '111', '1937915896@qq.com', 'img/shoye.jpg', '1', '1');
INSERT INTO `user_table` VALUES ('12345678', '李明', '1', '1937915896@qq.com', 'img/shoye.jpg', '0', '1');
INSERT INTO `user_table` VALUES ('123456789', '名', '111', '1937915896@qq.com', 'http://localhost:8080/yuyueSystem/upload\\15\\3\\11e6cfb8-b4e7-4972-b583-55cf24f99475_u=791507956,3476896706&fm=23&gp=0.jpg', '1', '1');
INSERT INTO `user_table` VALUES ('195140040', '周梦凯', '111', '1937915896@qq.com', 'img/shoye.jpg', '1', '0');

-- ----------------------------
-- Table structure for yuyue_records_table
-- ----------------------------
DROP TABLE IF EXISTS `yuyue_records_table`;
CREATE TABLE `yuyue_records_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `equ_code` varchar(10) DEFAULT NULL,
  `code` varchar(5) DEFAULT NULL,
  `account` varchar(20) DEFAULT NULL,
  `startTime` datetime NOT NULL,
  `endTime` datetime NOT NULL,
  `mark` char(1) NOT NULL COMMENT '0代表未审核，1代表审核通过，2，代表审核未通过',
  `msg` varchar(200) DEFAULT NULL,
  `resourcemark` char(1) NOT NULL COMMENT '0代表为预约实验室，1为预约大型设备',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_10` (`account`),
  KEY `FK_Reference_8` (`equ_code`),
  KEY `FK_Reference_9` (`code`),
  CONSTRAINT `FK_Reference_10` FOREIGN KEY (`account`) REFERENCES `user_table` (`account`),
  CONSTRAINT `FK_Reference_8` FOREIGN KEY (`equ_code`) REFERENCES `equresource_table` (`equ_code`),
  CONSTRAINT `FK_Reference_9` FOREIGN KEY (`code`) REFERENCES `lab_table` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='记录预约情况表';

-- ----------------------------
-- Records of yuyue_records_table
-- ----------------------------
INSERT INTO `yuyue_records_table` VALUES ('1', null, '213', '195140040', '2018-07-08 15:53:12', '2018-07-08 20:53:12', '1', null, '0');
INSERT INTO `yuyue_records_table` VALUES ('3', '2', null, '195140040', '2018-07-08 15:53:12', '2018-07-08 20:53:12', '0', null, '1');
INSERT INTO `yuyue_records_table` VALUES ('4', null, '210', '195140040', '2018-07-08 15:53:12', '2018-07-08 16:53:12', '1', null, '0');
INSERT INTO `yuyue_records_table` VALUES ('5', '2', null, '195140040', '2018-07-11 15:00:00', '2018-07-11 17:00:00', '0', null, '1');
INSERT INTO `yuyue_records_table` VALUES ('6', null, '213', '195140040', '2018-07-11 20:00:00', '2018-07-11 21:00:00', '1', null, '0');
INSERT INTO `yuyue_records_table` VALUES ('7', '2', null, '111', '2018-07-11 16:00:00', '2018-07-11 18:00:00', '1', null, '1');
INSERT INTO `yuyue_records_table` VALUES ('8', '1', null, '195140040', '2018-07-13 10:00:00', '2018-07-13 13:00:00', '0', null, '1');
INSERT INTO `yuyue_records_table` VALUES ('9', '2', null, '123456789', '2018-07-13 09:00:00', '2018-07-13 12:00:00', '0', null, '1');
