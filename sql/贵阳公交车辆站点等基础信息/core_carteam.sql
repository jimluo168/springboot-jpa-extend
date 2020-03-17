/*
Navicat MySQL Data Transfer

Source Server         : 98
Source Server Version : 50727
Source Host           : 192.168.0.98:3306
Source Database       : bus_guiyang

Target Server Type    : MYSQL
Target Server Version : 50727
File Encoding         : 65001

Date: 2020-03-16 16:01:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for core_carteam
-- ----------------------------
DROP TABLE IF EXISTS `core_carteam`;
CREATE TABLE `core_carteam` (
  `id` char(4) NOT NULL COMMENT 'ID',
  `t_name` varchar(50) DEFAULT NULL COMMENT '车队名称',
  `cid` char(4) DEFAULT NULL COMMENT '公司id',
  `insert_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '插入时间',
  `insert_uid` int(11) DEFAULT NULL COMMENT '插入者iD',
  `status` int(11) DEFAULT '1' COMMENT '状态',
  `t_addr` varchar(150) DEFAULT NULL COMMENT '车队地址',
  `t_tel` varchar(50) DEFAULT NULL COMMENT '车队联系电话',
  `t_per` varchar(255) DEFAULT NULL COMMENT '车队负责人',
  `num` varchar(24) DEFAULT NULL COMMENT '车队编号',
  PRIMARY KEY (`id`),
  KEY `cid` (`cid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='车队信息表';
