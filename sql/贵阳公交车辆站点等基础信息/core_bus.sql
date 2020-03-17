/*
Navicat MySQL Data Transfer

Source Server         : 98
Source Server Version : 50727
Source Host           : 192.168.0.98:3306
Source Database       : bus_guiyang

Target Server Type    : MYSQL
Target Server Version : 50727
File Encoding         : 65001

Date: 2020-03-16 16:01:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for core_bus
-- ----------------------------
DROP TABLE IF EXISTS `core_bus`;
CREATE TABLE `core_bus` (
  `id` char(6) NOT NULL COMMENT '车辆编号',
  `bus_no` char(10) DEFAULT NULL COMMENT '车牌号',
  `line_id` char(10) DEFAULT NULL COMMENT '线路ID',
  `m_id` int(11) DEFAULT NULL COMMENT '车载机ID',
  `bus_type` varchar(25) DEFAULT NULL COMMENT '类型 (汽油、天然气、混合动力)',
  `bus_kind` int(11) DEFAULT NULL COMMENT '种类',
  `seat_num` int(11) DEFAULT NULL COMMENT '坐位数量',
  `load_num` int(11) DEFAULT NULL COMMENT '载客数量',
  `factory_name` varchar(50) DEFAULT NULL COMMENT '制造商',
  `producte_date` date DEFAULT NULL COMMENT '生产日期',
  `register_date` date DEFAULT NULL COMMENT '投产日期',
  `insert_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '录入日期',
  `insert_uid` int(11) DEFAULT NULL COMMENT '录入人ID',
  `sim_id` varchar(20) DEFAULT NULL COMMENT 'sim_id',
  `voideNum` varchar(20) DEFAULT NULL COMMENT '视频数量',
  `current_lineid` char(10) DEFAULT NULL COMMENT '当前车辆ID',
  `frame_number` varchar(255) DEFAULT NULL COMMENT '车架号',
  `carCus` varchar(255) DEFAULT NULL COMMENT '车辆用途',
  `carLength` varchar(25) DEFAULT NULL COMMENT '车辆长度',
  `carHelp` varchar(11) DEFAULT NULL COMMENT '是否助力  1：是  2：否',
  `cheXing` varchar(25) DEFAULT NULL COMMENT '车型（什么车）',
  `engine` varchar(11) DEFAULT NULL COMMENT '前后置发动机  1：前  2：后',
  `kongtiao` varchar(11) DEFAULT NULL COMMENT '是否空调 1：是 2：否',
  `deleteSatus` varchar(25) DEFAULT NULL COMMENT '1：正常 2：删除',
  PRIMARY KEY (`id`),
  KEY `line_id` (`line_id`) USING BTREE,
  KEY `m_id` (`m_id`) USING BTREE,
  KEY `current_lineid` (`current_lineid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='公交车信息';
