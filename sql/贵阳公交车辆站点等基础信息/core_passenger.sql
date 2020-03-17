/*
Navicat MySQL Data Transfer

Source Server         : 98
Source Server Version : 50727
Source Host           : 192.168.0.98:3306
Source Database       : bus_guiyang

Target Server Type    : MYSQL
Target Server Version : 50727
File Encoding         : 65001

Date: 2020-03-16 16:07:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for core_passenger
-- ----------------------------
DROP TABLE IF EXISTS `core_passenger`;
CREATE TABLE `core_passenger` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `ic_card` varchar(20) DEFAULT NULL COMMENT 'ic卡ID',
  `id_card` varchar(30) DEFAULT NULL COMMENT '身份证号',
  `c_id` int(11) DEFAULT NULL COMMENT '公司id',
  `t_id` int(11) DEFAULT NULL COMMENT '车队id',
  `l_id` int(11) DEFAULT NULL COMMENT '线路id',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机',
  `jobNum` varchar(20) DEFAULT NULL COMMENT '工号',
  `driverYears` varchar(255) DEFAULT NULL COMMENT '司机驾龄',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱 ',
  `sex` varchar(11) DEFAULT NULL COMMENT '性别 ',
  `insert_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT ' 插入时间',
  `insert_uid` int(11) DEFAULT NULL COMMENT '插入者 ID',
  `status` int(11) DEFAULT NULL COMMENT '状态',
  `mobile` varchar(20) DEFAULT NULL,
  `driverYerars` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_jopnum` (`jobNum`),
  KEY `ic_card` (`ic_card`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=30925 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='司机乘务员';
