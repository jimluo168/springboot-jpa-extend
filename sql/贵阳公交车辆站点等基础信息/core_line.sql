/*
Navicat MySQL Data Transfer

Source Server         : 98
Source Server Version : 50727
Source Host           : 192.168.0.98:3306
Source Database       : bus_guiyang

Target Server Type    : MYSQL
Target Server Version : 50727
File Encoding         : 65001

Date: 2020-03-16 16:00:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for core_line
-- ----------------------------
DROP TABLE IF EXISTS `core_line`;
CREATE TABLE `core_line` (
  `id` char(5) NOT NULL COMMENT '线路ID',
  `version` int(11) DEFAULT NULL COMMENT '线路版本号',
  `l_name` varchar(50) DEFAULT NULL COMMENT '线路名称',
  `l_type` varchar(20) DEFAULT NULL COMMENT '线路类型',
  `t_id` char(4) DEFAULT NULL COMMENT '车队ID',
  `line_file` varchar(100) DEFAULT NULL COMMENT '线路经纬度文件名',
  `cross_line` int(11) DEFAULT NULL COMMENT '越界距离',
  `insert_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '插入时间',
  `insert_uid` int(11) DEFAULT NULL COMMENT '插入者ID',
  `status` int(11) DEFAULT NULL COMMENT '线路状态 0:可用 1禁用',
  `percentage` varchar(255) DEFAULT NULL COMMENT '上行撞点百分比',
  `downlinkSchedulingRadius` varchar(255) DEFAULT NULL COMMENT '下行排班半径',
  `timeIntervalScheduling` varchar(255) DEFAULT NULL COMMENT '下发排班时间间隔',
  `uplinkSchedulingRadius` varchar(255) DEFAULT NULL COMMENT '上行排班半径',
  `whetherLink` int(255) DEFAULT '2' COMMENT '是否环线 0:是 1：否',
  `lineSerial` varchar(25) DEFAULT NULL COMMENT '线路编号',
  `upTime` varchar(25) DEFAULT NULL COMMENT '上行周圈时间',
  `downTime` varchar(25) DEFAULT NULL COMMENT '下行周圈时间',
  `upkilometre` varchar(25) DEFAULT NULL COMMENT '上行公里数',
  `downkilometre` varchar(25) DEFAULT NULL COMMENT '下行公里数',
  `downPercentage` varchar(25) DEFAULT NULL COMMENT '下行撞点百分比',
  `pushInterval` varchar(25) DEFAULT NULL COMMENT '设置发车消息推送间隔',
  `geometry1` longblob,
  `geometry2` mediumblob,
  `geometry3` longblob,
  `geometry4` mediumblob,
  `defInterval` int(11) DEFAULT NULL COMMENT '计划外发车间隔',
  `upkilometreDiaotou` varchar(25) DEFAULT NULL COMMENT '上行公里调头',
  `downkilometreDiaotou` varchar(25) DEFAULT NULL COMMENT '下行公里调头',
  `timeThrough` varchar(25) DEFAULT NULL COMMENT '时间通过率',
  `kmThrough` varchar(25) DEFAULT NULL COMMENT '公里通过率',
  `syncapp` int(11) DEFAULT '1' COMMENT '1同步 0不同步',
  `change_lineid` int(1) DEFAULT '0',
  `bzbb` int(11) DEFAULT '1',
  `xsbb` int(11) DEFAULT '1',
  `timeFrameScheduling` varchar(255) DEFAULT NULL,
  `attendanceRate` double(255,1) DEFAULT '0.0' COMMENT '出勤率',
  PRIMARY KEY (`id`),
  KEY `t_id` (`t_id`) USING BTREE,
  KEY `lineSerial` (`lineSerial`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='线路';
