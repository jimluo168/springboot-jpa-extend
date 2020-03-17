/*
Navicat MySQL Data Transfer

Source Server         : 98
Source Server Version : 50727
Source Host           : 192.168.0.98:3306
Source Database       : bus_guiyang

Target Server Type    : MYSQL
Target Server Version : 50727
File Encoding         : 65001

Date: 2020-03-16 16:00:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for core_company
-- ----------------------------
DROP TABLE IF EXISTS `core_company`;
CREATE TABLE `core_company` (
  `id` char(4) NOT NULL COMMENT 'ID',
  `c_city` int(11) DEFAULT NULL COMMENT '公司所在地',
  `c_name` varchar(50) DEFAULT NULL COMMENT '公司名称',
  `c_level` int(11) DEFAULT NULL COMMENT '公司级别',
  `up_cid` char(4) DEFAULT '0' COMMENT '上级公司ID',
  `c_addr` varchar(100) DEFAULT NULL COMMENT '公司地址',
  `comment` varchar(100) DEFAULT NULL COMMENT '备注',
  `insert_date` timestamp NULL DEFAULT '1999-01-01 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '录入时间',
  `insert_uid` int(11) DEFAULT NULL COMMENT '录入人ID',
  `order_num` int(11) DEFAULT NULL COMMENT '排序索引',
  `num` varchar(25) DEFAULT NULL COMMENT '公司编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='公司信息表';
