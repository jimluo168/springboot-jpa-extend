/*
Navicat MySQL Data Transfer

Source Server         : 98
Source Server Version : 50727
Source Host           : 192.168.0.98:3306
Source Database       : bus_guiyang

Target Server Type    : MYSQL
Target Server Version : 50727
File Encoding         : 65001

Date: 2020-03-16 16:02:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for machine_station
-- ----------------------------
DROP TABLE IF EXISTS `machine_station`;
CREATE TABLE `machine_station` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '车载机站点维护表',
  `line_id` int(20) NOT NULL COMMENT '线路',
  `type` int(20) DEFAULT '1' COMMENT '站点类型',
  `site_order` int(3) DEFAULT NULL COMMENT '站点顺序',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '站点名称',
  `up_down_flag` int(20) DEFAULT NULL COMMENT '上下行标志 1上行 0下行',
  `gps_x` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'gps经度',
  `gps_y` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'gps纬度',
  `gps_angle` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT 'gps夹角',
  `report_voice_file` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '报站文件名',
  `insert_date` timestamp NOT NULL DEFAULT '1999-01-01 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '插入时间',
  `insert_id` int(20) DEFAULT NULL COMMENT '插入者ID',
  `station_version` varchar(25) DEFAULT '1' COMMENT '版本号',
  `next_station` int(11) DEFAULT NULL,
  `up_station` int(11) DEFAULT NULL,
  `upStaDistance` varchar(25) DEFAULT NULL COMMENT '上一站距离',
  `yesOrnointerval` varchar(25) DEFAULT NULL COMMENT '是否检测大间隔 1:是2：否',
  `intervalValue` varchar(25) DEFAULT NULL COMMENT '大间隔参数',
  `yesOrnoZhistation` varchar(25) DEFAULT NULL COMMENT '是否滞站',
  `ZhistationValue` varchar(25) DEFAULT NULL COMMENT '滞站参数',
  `radius` varchar(25) DEFAULT NULL COMMENT '半径',
  `reportVoice2` varchar(255) DEFAULT NULL COMMENT '报站文件2',
  `reportVoice3` varchar(255) DEFAULT NULL COMMENT '报站文件3',
  `lReportRadiusLongitude` int(11) DEFAULT '0' COMMENT '报站半径的经度偏移量',
  `lReportRadiusLatitude` int(11) DEFAULT '0' COMMENT '报站半径的纬度偏移量',
  PRIMARY KEY (`id`),
  KEY `line_id` (`line_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=122800 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='车载机站点维护表';
